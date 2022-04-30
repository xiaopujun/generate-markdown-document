package com.pyz.markdown.doc.entity;

import com.intellij.openapi.ui.MessageType;

import java.util.List;

/**
 * 数据节点，各个方法返回值的结果及状态使用改数据节点进行存放
 */
public class DataNode<T> {

    /**
     * 提示信息
     */
    private String msg;
    /**
     * 状态
     **/
    private MessageType statue = MessageType.INFO;
    /**
     * 数据
     */
    private T data;

    public MessageType getStatue() {
        return statue;
    }

    public void setStatue(MessageType statue) {
        this.statue = statue;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
