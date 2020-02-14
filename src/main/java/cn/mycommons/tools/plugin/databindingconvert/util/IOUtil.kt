package cn.mycommons.tools.plugin.databindingconvert.util

import java.io.PrintWriter

/**
 * IOUtil <br></br>
 * Created by xiaqiulei on 2016-09-09.
 */
object IOUtil {

    fun writeToFile(path: String, content: String?) {
        try {
            val writer = PrintWriter(path)
            writer.print(content)
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}