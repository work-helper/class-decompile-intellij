package cn.mrdear.intellij.decompile.ui;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.sun.tools.javap.Main;

import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

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
     *
     * @param stringWriter 输出位置
     */
    public void decompile(String path, StringWriter stringWriter) {
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            // fill args
            String[] args = StringUtils.split(SETTING.getJavap().concat(" ").concat(path), ' ');
            // javap
            Main.run(args, printWriter);
            setCode(stringWriter.toString());
        } catch (Exception e) {
            setCode("decompile fail " + e.getMessage());
        } finally {
            stringWriter.getBuffer().setLength(0);
        }
    }

}
