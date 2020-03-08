package cn.mrdear.intellij.decompile;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorImpl;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassOwner;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.org.objectweb.asm.ClassReader;

import cn.mrdear.intellij.decompile.ui.ASMifiedToolPanel;
import cn.mrdear.intellij.decompile.ui.ByteCodeToolPanel;
import cn.mrdear.intellij.decompile.ui.CFRToolPanel;
import cn.mrdear.intellij.decompile.ui.JavapToolPanel;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

/**
 * 全局入口
 */
public class ShowDecompileAction extends AnAction {

    /**
     * 1. 获取当前指向文件,判断是否可编译为Class文件类型
     *
     * @param event 当前事件
     */
    @Override
    public void actionPerformed(AnActionEvent event) {
        // 获取当前环境信息
        Project project = event.getData(PlatformDataKeys.PROJECT);
        VirtualFile file = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (null == project || null == file) {
            return;
        }

        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        // 校验可编译为class文件
        if (!(psiFile instanceof PsiClassOwner)) {
            return;
        }
        // this file is xxx.class, decompile this
        if ("class".equalsIgnoreCase(file.getExtension())) {
            decompileAndShowWindow(project, file);
            return;
        }

        // TODO 场景是什么?
        if (!file.isInLocalFileSystem() && !file.isWritable()) {
            PsiClass[] classes = ((PsiClassOwner) psiFile).getClasses();
            if (classes.length > 0) {
                VirtualFile temp = classes[0].getOriginalElement().getContainingFile().getVirtualFile();
                decompileAndShowWindow(project, temp);
                return;
            }
        }

        // 针对源文件处理, 先编译为class,再反编译class
        Application application = ApplicationManager.getApplication();
        Module module = ModuleUtil.findModuleForPsiElement(psiFile);
        CompilerModuleExtension cme = null == module ? null : CompilerModuleExtension.getInstance(module);
        if (null == cme) {
            return;
        }
        CompilerManager compilerManager = CompilerManager.getInstance(project);

        // 文件,刷盘
        application.runWriteAction(() -> FileDocumentManager.getInstance().saveAllDocuments());
        application.executeOnPooledThread(() -> {
            VirtualFile[] classFile = {null};
            VirtualFile[] files = {file};

            CompileScope compileScope = compilerManager.createFilesCompileScope(files);
            if (compilerManager.isUpToDate(compileScope)) {
                VirtualFile[] outputDirectories = cme.getOutputRoots(true);
                application.invokeLater(() -> {
                    classFile[0] = findClassFile(project, outputDirectories, psiFile);
                    decompileAndShowWindow(project, classFile[0]);
                });
            } else {
                application.invokeLater(() -> {
                    compilerManager.compile(files, (abort, errors, warnings, compileContext) -> {
                        if (errors == 0) {
                            VirtualFile[] outputRoots = cme.getOutputRoots(true);
                            application.invokeLater(() -> {
                                classFile[0] = findClassFile(project, outputRoots, psiFile);
                                decompileAndShowWindow(project, classFile[0]);
                            });
                        }
                    });
                });
            }
        });
    }

    /**
     * 在输出目录中找到对应的class类
     *
     * @param project           当前工程
     * @param outputDirectories 输出目录
     * @param psiFile           class文件
     * @return 查找结果
     */
    private VirtualFile findClassFile(Project project, VirtualFile[] outputDirectories,
        PsiFile psiFile) {
        return ApplicationManager.getApplication().runReadAction((Computable<VirtualFile>) () -> {
            FileEditor editor = FileEditorManager.getInstance(project).getSelectedEditor(psiFile.getVirtualFile());
            int caretOffset = null == editor ? -1 :
                ((PsiAwareTextEditorImpl) editor).getEditor().getCaretModel().getOffset();
            if (caretOffset >= 0) {
                // 找到对应的类名
                PsiElement elem = psiFile.findElementAt(caretOffset);
                while (elem != null) {
                    if (elem instanceof PsiClass) {
                        break;
                    }
                    elem = elem.getParent();
                }
                if (null != elem) {
                    return getClassFile(outputDirectories, (PsiClass) elem);
                }
            }

            PsiClass[] classes = ((PsiClassOwner) psiFile).getClasses();
            for (PsiClass temp : classes) {
                VirtualFile file = getClassFile(outputDirectories, temp);
                if (null != file) {
                    return file;
                }
            }
            return null;
        });
    }

    private VirtualFile getClassFile(VirtualFile[] outputDirectories, PsiClass psiClass) {
        StringBuilder sb = new StringBuilder(Objects.requireNonNull(psiClass.getQualifiedName()));
        while (psiClass.getContainingClass() != null) {
            sb.setCharAt(sb.lastIndexOf("."), '$');
            psiClass = psiClass.getContainingClass();
        }
        String classFileName = sb.toString().replace('.', '/') + ".class";
        for (VirtualFile outputDirectory : outputDirectories) {
            final VirtualFile file = outputDirectory.findFileByRelativePath(classFileName);
            if (file != null && file.exists()) {
                return file;
            }
        }
        return null;
    }

    /**
     * 反编译,并且展示到界面上
     *
     * @param project   当前工程文件
     * @param classFile 对应class类
     */
    private void decompileAndShowWindow(Project project, @Nullable VirtualFile classFile) {
        if (classFile == null) {
            JavapToolPanel.getInstance(project).setCode("// couldn't generate Javap view, no .class file found");
            ByteCodeToolPanel.getInstance(project).setCode("// couldn't generate ByteCode view, no .class file found");
            ASMifiedToolPanel.getInstance(project).setCode("// couldn't generate ASMified view, no .class file found");
            CFRToolPanel.getInstance(project).setCode("// couldn't generate CFR view, no .class file found");
            ToolWindowManager.getInstance(project).getToolWindow("Decompile").activate(null);
            return;
        }

        ApplicationManager.getApplication().runWriteAction(() -> {
            StringWriter writer = new StringWriter();
            String path = classFile.getPath();
            ClassReader reader = null;
            classFile.refresh(false, false);

            try {
                reader = new ClassReader(classFile.contentsToByteArray());
            } catch (IOException e) {
                return;
            }

            JavapToolPanel.getInstance(project).decompile(path);
            ByteCodeToolPanel.getInstance(project).decompile(writer, reader);
            ASMifiedToolPanel.getInstance(project).decompile(writer, reader);
            CFRToolPanel.getInstance(project).decompile(path, writer, reader);

            ToolWindowManager.getInstance(project).getToolWindow("Decompile").activate(null);
            // help gc
            reader = null;
            writer = null;
        });
    }


}
