package cn.mycommons.tools.plugin.databindingconvert.util;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * XmlUtil <br/>
 * Created by xiaqiulei on 2016-09-09.
 */
public class XmlUtil {

    @NotNull
    public static String documentToXmlString(Document newDocument) throws TransformerException, IOException {
        TransformerFactory tf = TransformerFactory.newInstance(); //此抽象类的实例能够将源树转
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//设置转换中世纪的输出属性
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");//
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);//充当转换结果的持有者，可以为xml、纯文本、HTML或某些其他格式的标记
        DOMSource source = new DOMSource(newDocument); //创建带有DOM节点的新输入源
        transformer.transform(source, result);//将XML Source转换为Result
        writer.close();
        return writer.toString();
    }
}