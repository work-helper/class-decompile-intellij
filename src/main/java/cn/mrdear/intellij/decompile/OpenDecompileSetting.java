package cn.mrdear.intellij.decompile;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.util.PlatformIcons;

import cn.mrdear.intellij.decompile.state.PluginSettingUIRegister;

public class OpenDecompileSetting extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ShowSettingsUtil.getInstance()
            .showSettingsDialog(e.getProject(), PluginSettingUIRegister.class);
    }

    public OpenDecompileSetting() {
        super(PlatformIcons.SHOW_SETTINGS_ICON);
    }
}
