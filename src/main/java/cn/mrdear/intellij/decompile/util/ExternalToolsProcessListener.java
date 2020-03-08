package cn.mrdear.intellij.decompile.util;

import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Key;

import org.jetbrains.annotations.NotNull;

import cn.mrdear.intellij.decompile.ui.JavapToolPanel;

import java.io.StringWriter;

/**
 * @author Quding Ding
 * @since 2020/3/8
 */
public class ExternalToolsProcessListener extends ProcessAdapter {

    private StringWriter writer;

    private JavapToolPanel panel;

    public ExternalToolsProcessListener(StringWriter writer, JavapToolPanel panel) {
        this.writer = writer;
        this.panel = panel;
    }

    @Override
    public void processTerminated(@NotNull ProcessEvent event) {
        ApplicationManager.getApplication()
            .invokeLater(() -> ApplicationManager.getApplication()
                .runWriteAction(() -> panel.setCode(writer.toString())));
    }

    @Override
    public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
        if (outputType.equals(ProcessOutputType.STDOUT) || outputType.equals(ProcessOutputType.STDERR)) {
            writer.append(event.getText());
        }
    }

}
