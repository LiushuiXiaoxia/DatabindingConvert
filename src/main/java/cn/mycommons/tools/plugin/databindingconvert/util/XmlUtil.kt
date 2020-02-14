package cn.mycommons.tools.plugin.databindingconvert.util

import org.dom4j.DocumentHelper
import org.dom4j.io.OutputFormat
import org.dom4j.io.XMLWriter
import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * XmlUtil <br></br>
 * Created by xiaqiulei on 2016-09-09.
 */
object XmlUtil {

    @JvmStatic
    @Throws(Exception::class)
    fun documentToXmlString(document: Document?): String {
        val tf = TransformerFactory.newInstance() //此抽象类的实例能够将源树转
        val transformer = tf.newTransformer()
        val writer = StringWriter()
        val result = StreamResult(writer)
        val source = DOMSource(document)
        transformer.transform(source, result)
        writer.close()
        return writer.toString()
    }

    @JvmStatic
    @Throws(Exception::class)
    fun formatXml(xml: String?): String {
        val out = StringWriter()
        out.use {
            val format = OutputFormat.createPrettyPrint()
            format.setIndentSize(4)
            format.isNewLineAfterDeclaration = false
            val xmlWriter = XMLWriter(out, format)
            xmlWriter.write(DocumentHelper.parseText(xml))
            xmlWriter.flush()
            xmlWriter.close()
        }
        return out.toString()
    }
}