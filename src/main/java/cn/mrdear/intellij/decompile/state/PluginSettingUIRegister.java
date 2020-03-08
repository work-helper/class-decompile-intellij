package cn.mrdear.intellij.decompile.state;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

/**
 * @author Quding Ding
 * @since 2020/3/7
 */
public class PluginSettingUIRegister implements Configurable, Disposable {

    private Setting setting;

    private SettingConfigurationPanel panel;

    public PluginSettingUIRegister() {
        this.setting = Setting.getInstance();
    }

    @Override
    public void dispose() {
        panel = null;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Class Decompile Setting";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == panel) {
            panel = new SettingConfigurationPanel();
        }
        return panel.getContainer();
    }

    @Override
    public void disposeUIResources() {
        this.panel = null;
    }

    @Override
    public boolean isModified() {
        if (panel != null) {
            return panel.isModified(setting);
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (panel != null) {
            panel.getData(setting);
        }
    }

    @Override
    public void reset() {
        if (panel != null) {
            panel.setData(setting);
        }
    }
}
