package cn.mrdear.intellij.decompile;

import com.google.common.collect.Lists;
import com.intellij.diff.DiffManager;
import com.intellij.diff.contents.DiffContent;
import com.intellij.diff.contents.DocumentContentImpl;
import com.intellij.diff.requests.ContentDiffRequest;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.util.IconLoader;
import com.intellij.util.ReflectionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ShowDiffAction extends AnAction {

    /**
     * 引用面板文档
     */
    private Document document;
    /**
     * 上一个版本代码
     */
    private String prevCode;

    public ShowDiffAction(Document document) {
        super("Show Differences",
            "Shows differences from the previous version of bytecode for this file",
            IconLoader.getIcon("/actions/diff.png", Objects.requireNonNull(ReflectionUtil.getGrandCallerClass())));
        this.document = document;
        this.prevCode = "";
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DiffManager diffManager = DiffManager.getInstance();

        diffManager.showDiff(e.getProject(),new ContentDiffRequest(){

            @Override
            public String getTitle() {
                return "Class Decompile Diff";
            }

            @NotNull
            @Override
            public List<DiffContent> getContents() {
                DiffContent currentContent = new DocumentContentImpl(document);
                DiffContent pre = new DocumentContentImpl(new DocumentImpl(prevCode));
                return Lists.newArrayList(currentContent, pre);
            }

            @NotNull
            @Override
            public List<String> getContentTitles() {
                return Lists.newArrayList("current", "previous");
            }
        });


    }

    /**
     * 存储最新的code
     */
    public void storeLatest() {
        prevCode = document.getText();
    }

}
