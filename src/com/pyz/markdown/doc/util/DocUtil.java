package com.pyz.markdown.doc.util;


import com.intellij.openapi.ui.MessageType;
import com.pyz.markdown.doc.entity.DataNode;
import com.pyz.markdown.doc.entity.DocItem;
import com.pyz.markdown.doc.entity.ParamItem;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author pyz
 * @date 2022/4/17
 * @description 文档工具类
 */
public class DocUtil {

    /**
     * 存放警告信息
     */
    private static List<String> warningInfoList = new ArrayList<>();

    /**
     * 注释匹配
     */
    private static final Pattern noteRegex = Pattern.compile("(\\/\\*\\*(\\n\\s*\\*.*)+)\\n.*");
    /**
     * 判定类注解
     */
    private static final Pattern classRegex = Pattern.compile("public? (class|interface).*\\{");
    /**
     * 判定方法注解
     */
    private static final Pattern methodRegex = Pattern.compile(".*[a-zA-Z]+\\(.*\\)(;|\\{)");
    /**
     * 获取@params参数
     */
    private static final Pattern paramRegex = Pattern.compile("@param[^@/]+");
    /**
     * 获取@description描述
     */
    private static final Pattern descriptionRegex = Pattern.compile("@description[^@/]+");
    /**
     * 获取@return描述
     */
    private static final Pattern returnRegex = Pattern.compile("@return[^@/]+");
    /**
     * 获取@author
     */
    private static final Pattern authorRegex = Pattern.compile("@author\\s+.*");
    /**
     * 获取@date
     */
    private static final Pattern dateRegex = Pattern.compile("@date\\s+.*");
    /**
     * 匹配方法参数
     */
    private static final Pattern inputParamRegex = Pattern.compile("\\(((\\s*[\\w\\<\\>]+\\s+\\w+,?)|(\\s*[\\w\\<\\>,]+\\s,?))+\\)");
    /**
     * 匹配单个参数类型
     */
    private static final Pattern paramItemRegex = Pattern.compile("(\\w+\\s\\w+)|(\\w+\\<.*\\>\\s\\w+)");
    /**
     * 匹配类名或方法名
     */
    private static final Pattern nameRegex = Pattern.compile("((class|interface)\\s+\\w+)|(\\w+\\(.+\\))");


    /**
     * @return 剪贴板的数据
     * @description 获取系统剪贴板的数据
     */
    public static String getClipboardData() {
        String res = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                res = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
                if ("".equals(res)) warningInfoList.add("剪贴板中尚未复制字符串，请重新复制代码注释！");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            warningInfoList.add("剪贴板中的内存为非文本类型数据，请复制代码注释以生成文档");
        }
        return res;
    }

    /**
     * @return 注释数据列表
     * @description 获取注释数据项
     */
    public static List<String> getNoteList() {
        List<String> res = new ArrayList<>();
        String input = getClipboardData();
        if (!"".equals(input)) {
            Matcher matcher = noteRegex.matcher(input);
            while (matcher.find()) {
                res.add(matcher.group());
            }
        }
        return res;
    }

    /**
     * @param input 输入
     * @return 参数列表
     * @description 获取参数列表
     */
    public static List<ParamItem> getParams(String input) {
        Matcher matcher = paramRegex.matcher(input);
        List<ParamItem> res = new ArrayList<>();
        while (matcher.find()) {
            ParamItem item = new ParamItem();
            String paramStr = matcher.group();
            //处理匹配到的param数据
            paramStr = paramStr.replaceAll("\\s+\\*\\s+", "")
                    .replaceAll("\\s{2,}", " ");
            String[] split = paramStr.split("\\s+");
            if (split.length == 3) {
                //符合规范规范的注解
                item.setParamName(split[1]);
                item.setParamDetail(split[2]);
                res.add(item);
            } else {
                warningInfoList.add("参数说明注释不符合规范，请检查：" + paramStr + "\n");
            }
        }
        //计算参数名与参数实体的映射关系
        Map<String, ParamItem> paramItemMap = res.stream().collect(Collectors.toMap(ParamItem::getParamName, i -> i));
        //解析参数类型
        matcher = inputParamRegex.matcher(input);
        while (matcher.find()) {
            String group = matcher.group();
            Matcher paramItemMatcher = paramItemRegex.matcher(group);
            while (paramItemMatcher.find()) {
                String param = paramItemMatcher.group();
                if (param.contains("<") || param.contains(".")) {
                    param = param.replace(", ", ",");
                    String[] params = param.split("\\s");
                    String type = params[0];
                    String name = params[1];
                    if (paramItemMap.containsKey(name)) {
                        ParamItem item = paramItemMap.get(name);
                        item.setParamType(type);
                    }
                } else {
                    String[] params = param.split("\\s");
                    String type = params[0];
                    String name = params[1];
                    if (paramItemMap.containsKey(name)) {
                        ParamItem item = paramItemMap.get(name);
                        item.setParamType(type);
                    }
                }
            }

        }
        return res;
    }

    /**
     * @param input 输入
     * @return 返回值说明
     * @description 获取返回值
     */
    public static String getReturn(String input) {
        String res = "no return info";
        Matcher matcher = returnRegex.matcher(input);
        while (matcher.find()) {
            String paramStr = matcher.group();
            paramStr = paramStr.replaceAll("\\s+\\*\\s*", "")
                    .replaceAll("\\s{2,}", " ");
            String[] split = paramStr.split("\\s+");
            if (split.length >= 2) {
                res = split[1];
            } else {
                warningInfoList.add("返回信息注释不符合规范，请检查：" + paramStr + "\n");
            }
        }
        return res;
    }

    /**
     * @param input 输入
     * @return 返回值说明
     * @description 获取日期
     */
    public static String getDate(String input) {
        String res = "no date";
        Matcher matcher = dateRegex.matcher(input);
        while (matcher.find()) {
            String paramStr = matcher.group();
            String[] split = paramStr.split("\\s+");
            if (split.length == 2) {
                res = split[1];
            } else {
                warningInfoList.add("日期注释不符合规范，请检查：" + paramStr + "\n");
            }
        }
        return res;
    }

    /**
     * @param input 输入
     * @return 返回值说明
     * @description 获取日期
     */
    public static String getAuthor(String input) {
        String res = "no author";
        Matcher matcher = authorRegex.matcher(input);
        while (matcher.find()) {
            String paramStr = matcher.group();
            String[] split = paramStr.split("\\s+");
            if (split.length >= 2) {
                res = split[1];
            } else {
                warningInfoList.add("作者注释不符合规范，请检查：" + paramStr + "\n");
            }
        }
        return res;
    }

    /**
     * @param input 输入
     * @return 返回值说明
     * @description 获取日期
     */
    public static String getDescription(String input) {
        String res = "no description";
        Matcher matcher = descriptionRegex.matcher(input);
        while (matcher.find()) {
            String paramStr = matcher.group();
            paramStr = paramStr.replaceAll("\\s+\\*\\s*", "")
                    .replaceAll("\\s{2,}", " ");
            String[] split = paramStr.split("\\s+");
            if (split.length >= 2) {
                res = split[1];
            } else {
                warningInfoList.add("说明文字注释不符合规范，请检查：" + paramStr + "\n");
            }
        }
        return res;
    }

    /**
     * @param input 输入
     * @return 注释类型
     * @description 判断注释类型
     */
    public static int getNoteType(String input) {
        Matcher matcher = classRegex.matcher(input);
        boolean classNote = matcher.find();
        if (classNote)
            return 0;
        else
            return 1;
//        matcher = methodRegex.matcher(input);
//        boolean methodNote = matcher.find();
//        if (methodNote) return 1;
//        return 1;
    }

    /**
     * @param input 输入
     * @return 方法名或类名
     * @description 解析方法名或类名
     */
    public static String getName(String input, int type) {
        String res = "";
        Matcher matcher = nameRegex.matcher(input);
        if (matcher.find()) {
            String matcherStr = matcher.group();
            if (type == 0) {
                //类名
                String[] split = matcherStr.split("\\s+");
                if (split.length == 2)
                    res = split[1];
                else
                    warningInfoList.add("类名解析失败，请检查：" + matcherStr);
            } else {
                //方法名
                res = matcherStr.replaceFirst("\\(.*\\)", "");
            }
        }
        return res;
    }

    /**
     * @return 所有注释项的list集合
     * @description 获取所有注释项的list集合
     */
    public static DataNode<List<DocItem>> getDocItemList() {
        DataNode<List<DocItem>> res = new DataNode<>();
        ArrayList<DocItem> data = new ArrayList<>();
        List<String> noteList = DocUtil.getNoteList();
        if (noteList.size() > 0) {
            for (String noteData : noteList) {
                DocItem docItem = new DocItem();
                docItem.setAuthor(DocUtil.getAuthor(noteData));
                docItem.setDescription(DocUtil.getDescription(noteData));
                docItem.setDocType(DocUtil.getNoteType(noteData));
                docItem.setDate(DocUtil.getDate(noteData));
                docItem.setParams(DocUtil.getParams(noteData));
                docItem.setRes(DocUtil.getReturn(noteData));
                docItem.setName(DocUtil.getName(noteData, docItem.getDocType()));
                docItem.setOverview(noteData);
                data.add(docItem);
            }
            res.setData(data);
            res.setMsg("已生成文档到剪贴板中，请粘贴使用！");
        } else
            warningInfoList.add("未解析到代码注释内容，请检查代码注释是否规范");

        if (warningInfoList.size() > 0) {
            res.setStatue(MessageType.WARNING);
            res.setMsg(generateWarningInfo());
            //清空容器，防止警告信息一直增加
            warningInfoList.clear();
        }
        return res;
    }


    /**
     * @return 接口文档
     * @description 生成最终接口文档
     */
    public static String generateInterfaceDoc(List<DocItem> docItems) {
        String res = "";
        StringBuilder docBuilder = new StringBuilder();
        int total = 1;
        if (docItems != null && docItems.size() > 0) {
            for (DocItem docItem : docItems) {
                if (docItem.getDocType() == 0) {
                    //类文档
                    docBuilder.append("# 接口类：")
                            .append(docItem.getName())
                            .append("\n\n")
                            .append("> - 作者：")
                            .append(docItem.getAuthor())
                            .append("\n").append("> - 日期：")
                            .append(docItem.getDate())
                            .append("\n").append("> - 描述：")
                            .append(docItem.getDescription())
                            .append("\n\n");
                } else {
                    //方法文档
                    docBuilder.append("## 接口")
                            .append(total).append("：")
                            .append(docItem.getName())
                            .append("\n\n")
                            .append("- 接口概览：\n\n```java\n")
                            .append(docItem.getOverview())
                            .append("\n```\n")
                            .append("- 功能描述：")
                            .append(docItem.getDescription())
                            .append("\n- 返回值：")
                            .append(docItem.getRes())
                            .append("\n- 参数列表：\n\n")
                            .append("|序号|参数名|参数说明|参数类型|\n|----|----|----|----|\n");
                    List<ParamItem> params = docItem.getParams();
                    for (int i = 0; i < params.size(); i++) {
                        docBuilder.append("|").
                                append(i + 1)
                                .append("|")
                                .append(params.get(i).getParamName())
                                .append("|")
                                .append(params.get(i).getParamDetail())
                                .append("|")
                                .append(params.get(i).getParamType())
                                .append("|\n");
                    }
                    total++;
                }
            }
            res = docBuilder.toString();
        }
        return res;
    }

    /**
     * @return string
     * @description 生成异常信息
     */
    private static String generateWarningInfo() {
        StringBuilder builder = new StringBuilder();
        for (String warning : warningInfoList) {
            builder.append(warning).append("\n");
        }
        return builder.toString();
    }
}
