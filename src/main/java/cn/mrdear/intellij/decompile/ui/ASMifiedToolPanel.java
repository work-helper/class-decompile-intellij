package cn.mrdear.intellij.decompile.ui;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;

import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.util.ASMifier;
import org.jetbrains.org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Quding Ding
 * @since 2020/3/7
 */
public class ASMifiedToolPanel extends AbstractToolPanel {

    public ASMifiedToolPanel(Project project) {
        super(project);
    }

    /**
     * 得到当前实例
     * @param project 当前工程
     * @return 结果
     */
    public static ASMifiedToolPanel getInstance(Project project) {
        return ServiceManager.getService(project, ASMifiedToolPanel.class);
    }


    public void decompile(StringWriter stringWriter, ClassReader reader) {
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            reader.accept(new TraceClassVisitor(null, new ASMifier(), printWriter), SETTING.getAsmFlags());

            PsiFile psiFile = PsiFileFactory.getInstance(project).createFileFromText("temp.java",
                JavaFileType.INSTANCE, stringWriter.toString());
            CodeStyleManager.getInstance(project).reformat(psiFile);
            this.setCode(psiFile.getText());
        } catch (Exception e) {
            this.setCode("decompile fail" + e.getMessage());
        } finally {
            stringWriter.getBuffer().setLength(0);
        }
    }

}
