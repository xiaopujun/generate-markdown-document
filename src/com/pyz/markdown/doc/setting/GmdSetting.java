package com.pyz.markdown.doc.setting;

import javax.swing.*;

/**
 * gmd配置组件类
 */
public class GmdSetting {
    private JPanel gmdSettingPanel;
    private JTextArea classTemplate;
    private JTextArea methodTemplate;
    private JLabel classLabel;
    private JLabel methodLabel;
    private JLabel fieldLabel;

    public JPanel getGmdSettingPanel() {
        return gmdSettingPanel;
    }

    public JTextArea getClassTemplate() {
        return classTemplate;
    }

    public JTextArea getMethodTemplate() {
        return methodTemplate;
    }

    public JLabel getClassLabel() {
        return classLabel;
    }

    public JLabel getMethodLabel() {
        return methodLabel;
    }

    public JLabel getFieldLabel() {
        return fieldLabel;
    }
}
