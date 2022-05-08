package com.pyz.markdown.doc.config;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nullable;
import javax.swing.*;

/**
 * idea配置类
 */
public class AppSettingsConfigurable implements Configurable {

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
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modified = !gmdSetting.getClassTemplate().getText().equals(settings.classTemplate);
        modified |= !gmdSetting.getMethodTemplate().getText().equals(settings.methodTemplate);
        modified |= !gmdSetting.getFieldTemplate().getText().equals(settings.fieldTemplate);
        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.classTemplate = gmdSetting.getClassTemplate().getText();
        settings.methodTemplate = gmdSetting.getMethodTemplate().getText();
        settings.fieldTemplate = gmdSetting.getFieldTemplate().getText();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        gmdSetting.getClassTemplate().setText(settings.classTemplate);
        gmdSetting.getMethodTemplate().setText(settings.methodTemplate);
        gmdSetting.getFieldTemplate().setText(settings.fieldTemplate);
    }

    @Override
    public void disposeUIResources() {
        gmdSetting = null;
    }

}