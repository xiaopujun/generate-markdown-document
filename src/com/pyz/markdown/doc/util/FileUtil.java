package com.pyz.markdown.doc.util;

import java.io.*;

/**
 * 文件Util
 */
public class FileUtil {

    public static boolean generateMarkdownDocFile(String doc) {
        boolean res = true;
        Reader reader = null;
        Writer writer = null;
        try {
            String projectPath = System.getProperty("user.dir");
            File markdownFile = new File("/Users/yinzhenpu/IdeaProjects/to-markdown-doc" + "/document" + System.currentTimeMillis() + ".md");
            if (!markdownFile.exists()) {
                boolean newFile = markdownFile.createNewFile();
            }
            System.out.println(markdownFile);
            reader = new StringReader(doc);
            writer = new FileWriter(markdownFile);
            int singleChar = 0;
            while ((singleChar = reader.read()) != -1) {
                writer.write((char) singleChar);
            }
            writer.flush();
        } catch (Exception e) {
            res = false;
            System.out.println("generate document err");
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return res;
    }
}
