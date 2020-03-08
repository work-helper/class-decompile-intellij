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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            IconLoader.getIcon("/actions/diffWithCurrent.png"));
        this.document = document;
        this.prevCode = "";
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DiffManager diffManager = DiffManager.getInstance();

        diffManager.showDiff(e.getProject(),new ContentDiffRequest(){

            @Nullable
            @Override
            public String getTitle() {
                return "class decompile diff";
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
                return Lists.newArrayList("cur", "pre");
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
