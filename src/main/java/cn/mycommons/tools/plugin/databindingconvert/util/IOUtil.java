package cn.mycommons.tools.plugin.databindingconvert.util;

import java.io.PrintWriter;

/**
 * IOUtil <br/>
 * Created by xiaqiulei on 2016-09-09.
 */
public class IOUtil {

    public static void writeToFile(String path, String content) {
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.print(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}