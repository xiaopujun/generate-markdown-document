package com.pyz.markdown.doc.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


/**
 * 持久化实体
 */
@State(name = "com.pyz.markdown.doc.config.AppSettingsState", storages = @Storage("generate-markdown-document.xml"))
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    public String classTemplate = "";
    public String methodTemplate = "";
    public String fieldTemplate = "";

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
