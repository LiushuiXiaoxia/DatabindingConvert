package cn.mycommons.tools.plugin.databindingconvert.util;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * XmlUtil <br/>
 * Created by xiaqiulei on 2016-09-09.
 */
public class XmlUtil {

    @NotNull
    public static String documentToXmlString(Document document) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance(); //此抽象类的实例能够将源树转
        Transformer transformer = tf.newTransformer();
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
        writer.close();
        return writer.toString();
    }

    public static String formatXml(String xml) throws Exception {
        StringWriter out = new StringWriter();
        try {
            OutputFormat formate = OutputFormat.createPrettyPrint();
            formate.setIndentSize(4);
            formate.setNewLineAfterDeclaration(false);
            XMLWriter xmlWriter = new XMLWriter(out, formate);
            xmlWriter.write(DocumentHelper.parseText(xml));
            xmlWriter.flush();
            xmlWriter.close();
        } finally {
            out.close();
        }
        return out.toString();
    }
}