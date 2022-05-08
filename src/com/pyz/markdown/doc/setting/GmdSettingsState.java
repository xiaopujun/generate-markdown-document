package com.pyz.markdown.doc.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


/**
 * 配置信息持久化类
 */
@State(name = "com.pyz.markdown.doc.config.AppSettingsState", storages = @Storage("generate-markdown-document.xml"))
public class GmdSettingsState implements PersistentStateComponent<GmdSettingsState> {

    private String classTemplate = "";
    private String methodTemplate = "";

    public static GmdSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(GmdSettingsState.class);
    }

    @Nullable
    @Override
    public GmdSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull GmdSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getClassTemplate() {
        if (classTemplate == null || "".equals(classTemplate.trim())) {
            return "# 接口类：@{className}@\n" +
                    "\n" +
                    "> 作者：@{author}@\n" +
                    ">\n" +
                    "> 日期：@{date}@\n" +
                    ">\n" +
                    "> 功能描述：@{description}@\n" +
                    "\n";
        }
        return classTemplate;
    }

    public void setClassTemplate(String classTemplate) {
        this.classTemplate = classTemplate;
    }

    public String getMethodTemplate() {
        if (methodTemplate == null || "".equals(methodTemplate.trim())) {
            return "## 接口@{serialNo}@：@{methodName}@\n" +
                    "\n" +
                    "* 接口概览：\n" +
                    "\n" +
                    "@{codePreview}@\n" +
                    "\n" +
                    "* 功能描述：@{description}@\n" +
                    "\n" +
                    "* 返回值：@{return}@\n" +
                    "\n" +
                    "* 参数列表：\n" +
                    "\n" +
                    "@{paramList}@ ";
        }
        return methodTemplate;
    }

    public void setMethodTemplate(String methodTemplate) {
        this.methodTemplate = methodTemplate;
    }
}
