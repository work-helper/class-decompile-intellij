package cn.mrdear.intellij.decompile.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;

import cn.mrdear.intellij.decompile.state.Setting;

/**
 * 展示面板
 * @author Quding Ding
 * @since 2020/3/7
 */
public abstract class AbstractToolPanel extends SimpleToolWindowPanel implements Disposable {

    /**
     * 当前所在项目
     */
    protected Project project;
    /**
     * 该面板所管理的文档
     */
    private Document document;
    /**
     * 当前面板对应的编辑器
     */
    private Editor editor;
    /**
     * 配置信息
     */
    protected static final Setting SETTING = Setting.getInstance();


    public AbstractToolPanel(Project project) {
        super(true);
        this.project = project;
        // 初始化面板
        this.setUI();
    }

    /**
     * 设置代码
     * @param code 代码
     */
    public void setCode(String code) {
        this.document.setText(code);
    }

    /**
     * 初始化面板
     */
    private void setUI() {
        EditorFactory editorFactory = EditorFactory.getInstance();
        document = editorFactory.createDocument("");
        editor = editorFactory.createEditor(document, project,
            FileTypeManager.getInstance().getFileTypeByExtension("java"), true);
        // 添加
        add(editor.getComponent());
    }

    @Override
    public void dispose() {
        if (editor != null) {
            EditorFactory editorFactory = EditorFactory.getInstance();
            editorFactory.releaseEditor(editor);
            editor = null;
        }
    }

}
