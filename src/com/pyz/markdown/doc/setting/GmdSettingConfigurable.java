package com.pyz.markdown.doc.setting;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nullable;
import javax.swing.*;

/**
 * idea配置类
 */
public class GmdSettingConfigurable implements Configurable {

    private GmdSetting gmdSetting;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Generate Markdown Document";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        gmdSetting = new GmdSetting();
        return gmdSetting.getGmdSettingPanel();
    }

    @Override
    public boolean isModified() {
        GmdSettingsState settings = GmdSettingsState.getInstance();
        boolean modified = !gmdSetting.getClassTemplate().getText().equals(settings.getClassTemplate());
        modified |= !gmdSetting.getMethodTemplate().getText().equals(settings.getMethodTemplate());
        return modified;
    }

    @Override
    public void apply() {
        GmdSettingsState settings = GmdSettingsState.getInstance();
        settings.setClassTemplate(gmdSetting.getClassTemplate().getText());
        settings.setMethodTemplate(gmdSetting.getMethodTemplate().getText());
    }

    @Override
    public void reset() {
        GmdSettingsState settings = GmdSettingsState.getInstance();
        gmdSetting.getClassTemplate().setText(settings.getClassTemplate());
        gmdSetting.getMethodTemplate().setText(settings.getMethodTemplate());
    }

    @Override
    public void disposeUIResources() {
        gmdSetting = null;
    }

}