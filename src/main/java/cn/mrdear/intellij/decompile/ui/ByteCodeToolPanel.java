package cn.mrdear.intellij.decompile.ui;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Quding Ding
 * @since 2020/3/7
 */
public class ByteCodeToolPanel extends AbstractToolPanel {

    public ByteCodeToolPanel(Project project) {
        super(project);
    }

    /**
     * 得到当前实例
     * @param project 当前工程
     * @return 结果
     */
    public static ByteCodeToolPanel getInstance(Project project) {
        return ServiceManager.getService(project, ByteCodeToolPanel.class);
    }

    /**
     * 反编译为字节码
     * @param stringWriter 输出位置
     * @param reader class内容
     */
    public void decompile(StringWriter stringWriter, ClassReader reader) {
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            ClassVisitor visitor = new TraceClassVisitor(printWriter);
            reader.accept(visitor, 0);
            setCode(stringWriter.toString());
        } catch (Exception e) {
            setCode("decompile fail " + e.getMessage());
        } finally {
            stringWriter.getBuffer().setLength(0);
        }
    }

}
