package com.pyz.markdown.doc.entity;


import java.io.Serializable;

public class ParamItem implements Serializable {
    private String paramName;
    private String paramDetail;
    private String paramType;


    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDetail() {
        return paramDetail;
    }

    public void setParamDetail(String paramDetail) {
        this.paramDetail = paramDetail;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
}
