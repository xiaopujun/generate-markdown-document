package com.pyz.markdown.doc.config;

import javax.swing.*;

public class GmdSetting {
    private JPanel gmdSettingPanel;
    private JTextArea classTemplate;
    private JTextArea methodTemplate;
    private JTextArea fieldTemplate;
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

    public JTextArea getFieldTemplate() {
        return fieldTemplate;
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
