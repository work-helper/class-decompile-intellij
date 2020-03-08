package cn.mrdear.intellij.decompile.state;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Quding Ding
 * @since 2020/3/7
 */
public class SettingConfigurationPanel {

    private JTextField javapField;
    private JTextField cfrField;

    private JPanel container;

    private JCheckBox skipDebugCheckBox;
    private JCheckBox skipFrameCheckBox;
    private JCheckBox expandFramesCheckBox;
    private JCheckBox skipCodeCheckBox;

    public JPanel getContainer() {
        return container;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void setData(Setting data) {
        javapField.setText(data.getJavap());
        cfrField.setText(data.getCfr());
        skipDebugCheckBox.setSelected(data.isAsmSkipDebug());
        skipFrameCheckBox.setSelected(data.isAsmSkipFrames());
        expandFramesCheckBox.setSelected(data.isAsmExpandFrames());
        skipCodeCheckBox.setSelected(data.isAsmSkipCode());
    }

    public void getData(Setting data) {
        data.setJavap(javapField.getText());
        data.setCfr(cfrField.getText());
        data.setAsmSkipDebug(skipDebugCheckBox.isSelected());
        data.setAsmSkipFrames(skipFrameCheckBox.isSelected());
        data.setAsmExpandFrames(expandFramesCheckBox.isSelected());
        data.setAsmSkipCode(skipCodeCheckBox.isSelected());
    }

    public boolean isModified(Setting data) {
        if (javapField.getText() != null ? !javapField.getText().equals(data.getJavap()) : data.getJavap() != null)
            return true;
        if (cfrField.getText() != null ? !cfrField.getText().equals(data.getCfr()) : data.getCfr() != null)
            return true;
        if (skipDebugCheckBox.isSelected() != data.isAsmSkipDebug()) return true;
        if (skipFrameCheckBox.isSelected() != data.isAsmSkipFrames()) return true;
        if (expandFramesCheckBox.isSelected() != data.isAsmExpandFrames()) return true;
        if (skipCodeCheckBox.isSelected() != data.isAsmSkipCode()) return true;
        return false;
    }
}
