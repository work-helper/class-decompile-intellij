package cn.mrdear.intellij.decompile;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.util.PlatformIcons;

import org.jetbrains.annotations.NotNull;

/**
 * @author Quding Ding
 * @since 2020/3/21
 */
public class OpenHelperWebSiteAction extends AnAction {

    private String website;

    public OpenHelperWebSiteAction(String website) {
        super(PlatformIcons.EXPORT_ICON);
        this.website = website;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BrowserUtil.browse(website);
    }


}
