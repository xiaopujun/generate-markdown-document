package com.pyz.markdown.doc.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import com.pyz.markdown.doc.config.AppSettingsState;
import com.pyz.markdown.doc.entity.DataNode;
import com.pyz.markdown.doc.entity.DocItem;
import com.pyz.markdown.doc.util.DocUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.List;

public class ToDocMenu extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        NotificationGroup notify = new NotificationGroup("com.pyz.markdown.doc.notify", NotificationDisplayType.BALLOON, true);
        try {
            //将注释解析为java对象
            DataNode<List<DocItem>> res = DocUtil.getDocItemList();
            if (!res.getStatue().equals(MessageType.ERROR)) {
                List<DocItem> docItemList = res.getData();
                //根据对象生成markdown格式文档
                String doc = DocUtil.generateInterfaceDoc(docItemList);
                Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable tText = new StringSelection(doc);
                clip.setContents(tText, null);

                Notification notification;
                if (MessageType.INFO.equals(res.getStatue()))
                    notification = notify.createNotification(res.getMsg(), MessageType.INFO);
                else {
                    notification = notify.createNotification(res.getMsg(), MessageType.WARNING);
                }
                Notifications.Bus.notify(notification);
            }
        } catch (Exception e1) {
            //出现异常
            Notification notification = notify.createNotification("文档生成错误原因：" + e1.getMessage(), MessageType.ERROR);
            Notifications.Bus.notify(notification);
        }
    }
}
