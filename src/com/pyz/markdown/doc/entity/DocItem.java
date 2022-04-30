package com.pyz.markdown.doc.entity;

import java.io.Serializable;
import java.util.List;

public class DocItem implements Serializable {
    /**
     * 注解主名称，类注解为类名、方法注解为方法名
     */
    private String name;
    private String description;
    private List<ParamItem> params;
    private String res;
    private String author;
    private String date;
    /**
     * 0:类注解，1：方法注解
     */
    private int docType;
    /**
     * 接口总览
     */
    private String overview;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ParamItem> getParams() {
        return params;
    }

    public void setParams(List<ParamItem> params) {
        this.params = params;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
