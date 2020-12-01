package cn.mrdear.intellij.decompile.ui;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;

import org.jetbrains.annotations.NotNull;

/**
 * 创建对应window
 *
 * @author Quding Ding
 * @since 2020/3/7
 */
public class DecompileToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentManager manager = toolWindow.getContentManager();
        ContentFactory instance = ContentFactory.SERVICE.getInstance();

        manager.addContent(instance.createContent(ByteCodeToolPanel.getInstance(project), "Bytecode", false));
        manager.addContent(instance.createContent(ASMifiedToolPanel.getInstance(project), "ASMified", false));
        manager.addContent(instance.createContent(CFRToolPanel.getInstance(project), "CFR", false));
        manager.addContent(instance.createContent(JavapToolPanel.getInstance(project), "Javap", false));
    }

}
