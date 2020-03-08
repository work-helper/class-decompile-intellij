package cn.mrdear.intellij.decompile.ui;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.tools.Tool;
import com.intellij.tools.ToolManager;

import org.apache.commons.lang3.StringUtils;

import cn.mrdear.intellij.decompile.util.ExternalToolsProcessListener;

import java.io.StringWriter;
import java.util.List;

/**
 * @author Quding Ding
 * @since 2020/3/7
 */
public class JavapToolPanel extends AbstractToolPanel {

    public JavapToolPanel(Project project) {
        super(project);
    }

    /**
     * 得到当前实例
     *
     * @param project 当前工程
     * @return 结果
     */
    public static JavapToolPanel getInstance(Project project) {
        return ServiceManager.getService(project, JavapToolPanel.class);
    }

    /**
     * 反编译为字节码
     */
    public void decompile(String path) {
        StringWriter writer = new StringWriter();

        List<Tool> tools = ToolManager.getInstance().getTools("External Tools");

        Tool javap = tools.stream()
            .filter(x -> x.getName().equalsIgnoreCase("javap"))
            .findFirst().orElse(null);

        if (null == javap) {
            this.setCode("\n no javap command found on External Tools, you can refer to " +
                "https://github.com/mrdear/class-decompile-intellij");
            return;
        }

        this.setCode("please wait");

        GeneralCommandLine commandLine = javap.createCommandLine((dataId) -> {
            if (CommonDataKeys.PROJECT.getName().equals(dataId)) {
                return project;
            }
            return null;
        });

        if (null == commandLine) {
            return;
        }

        commandLine.withParameters(StringUtils.split(SETTING.getJavap()," "));
        commandLine.addParameter(path);

        try {
            ExternalToolsProcessListener listener = new ExternalToolsProcessListener(writer, this);
            OSProcessHandler handler = new OSProcessHandler(commandLine);
            handler.addProcessListener(listener);
            handler.startNotify();
        } catch (Exception e) {
            this.setCode("decompile fail " + e.getMessage());
        }
    }

}
