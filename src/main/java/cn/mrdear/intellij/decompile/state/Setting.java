package cn.mrdear.intellij.decompile.state;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.org.objectweb.asm.ClassReader;

/**
 * 持久化配置
 * @author Quding Ding
 * @since 2020/2/23
 */
@State(name = "ClassDecompileConfig", storages = {@Storage("ClassDecompilePlugin.xml")})
public final class Setting implements PersistentStateComponent<Setting> {
    /**
     * default -c
     */
    private String javap = "-c";
    /**
     * default
     */
    private String cfr = "--stringbuilder false --arrayiter fase --collectioniter false --decodelambdas false --sugarboxing false";

    private boolean asmSkipDebug;
    private boolean asmSkipFrames;
    private boolean asmExpandFrames;
    private boolean asmSkipCode;

    /**
     * 全局单例
     */
    public static Setting getInstance() {
        return ApplicationManager.getApplication().getService(Setting.class);
    }

    /**
     * 得到ASM flags 标识
     * @return 标识
     */
    public int getAsmFlags() {
        int flags = 0;
        if (this.asmSkipDebug) flags = flags | ClassReader.SKIP_DEBUG;
        if (this.asmSkipFrames) flags = flags | ClassReader.SKIP_FRAMES;
        if (this.asmExpandFrames) flags = flags | ClassReader.EXPAND_FRAMES;
        if (this.asmSkipCode) flags = flags | ClassReader.SKIP_CODE;
        return flags;
    }

    @Nullable
    @Override
    public Setting getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull Setting state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getJavap() {
        return javap;
    }

    public void setJavap(final String javap) {
        this.javap = javap;
    }

    public String getCfr() {
        return cfr;
    }

    public void setCfr(final String cfr) {
        this.cfr = cfr;
    }

    public boolean isAsmSkipDebug() {
        return asmSkipDebug;
    }

    public void setAsmSkipDebug(final boolean asmSkipDebug) {
        this.asmSkipDebug = asmSkipDebug;
    }

    public boolean isAsmSkipFrames() {
        return asmSkipFrames;
    }

    public void setAsmSkipFrames(final boolean asmSkipFrames) {
        this.asmSkipFrames = asmSkipFrames;
    }

    public boolean isAsmExpandFrames() {
        return asmExpandFrames;
    }

    public void setAsmExpandFrames(final boolean asmExpandFrames) {
        this.asmExpandFrames = asmExpandFrames;
    }

    public boolean isAsmSkipCode() {
        return asmSkipCode;
    }

    public void setAsmSkipCode(final boolean asmSkipCode) {
        this.asmSkipCode = asmSkipCode;
    }
}
