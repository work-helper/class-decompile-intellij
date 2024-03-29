package cn.mrdear.intellij.decompile.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;

import cn.mrdear.intellij.decompile.OpenDecompileSetting;
import cn.mrdear.intellij.decompile.ShowDiffAction;
import cn.mrdear.intellij.decompile.state.Setting;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * 展示面板
 *
 * @author Quding Ding
 * @since 2020/3/7
 */
public abstract class AbstractToolPanel extends SimpleToolWindowPanel implements Disposable {
    /**
     * 无状态,因此共享
     */
    private static final OpenDecompileSetting OPEN_DECOMPILE_SETTING = new OpenDecompileSetting();

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

    private ShowDiffAction diffAction;
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
     *
     * @param code 代码
     */
    public void setCode(String code) {
        // 存储上个版本
        diffAction.storeLatest();
        // 最新版本
        this.document.setText(code);
    }

    /**
     * 初始化面板
     */
    private void setUI() {
        EditorFactory editorFactory = EditorFactory.getInstance();
        document = editorFactory.createDocument("");
        diffAction = new ShowDiffAction(document);
        editor = editorFactory.createEditor(document, project,
            FileTypeManager.getInstance().getFileTypeByExtension("java"), true);
        // 主面板
        add(editor.getComponent());

        // 添加工具栏
        DefaultActionGroup group = new DefaultActionGroup();

        group.add(OPEN_DECOMPILE_SETTING);
        group.add(diffAction);
        AnAction action = internalToolAction();
        if (null != action) {
            group.add(action);
        }

        ActionManager actionManager = ActionManager.getInstance();

        ActionToolbar actionToolBar = actionManager.createActionToolbar("Decompile", group, true);
        actionToolBar.setTargetComponent(this);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(actionToolBar.getComponent(), BorderLayout.CENTER);

        setToolbar(buttonsPanel);
    }

    /**
     * 子类的action
     *
     * @return action
     */
    protected AnAction internalToolAction() {
        return null;
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
