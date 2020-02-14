package cn.mycommons.tools.plugin.databindingconvert.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * XmlUtilTest <br/>
 * Created by xiaqiulei on 2016-09-14.
 */
public class XmlUtilTest {
    Document document;

    @Before
    public void setUp() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();

        initXmlData();
    }

    private void initXmlData() {
        Element root = document.createElement("layout");
        document.appendChild(root);

        Element data = document.createElement("data");
        root.appendChild(data);

        Element importElement = document.createElement("import");
        importElement.setAttribute("type", "");
        data.appendChild(importElement);

        Element variableElement = document.createElement("variable");
        variableElement.setAttribute("name", "");
        variableElement.setAttribute("type", "");
        data.appendChild(variableElement);
    }

    @Test
    public void documentToXmlString() throws Exception {
        String xml = XmlUtil.documentToXmlString(document);

        System.out.println(xml);
    }

    @Test
    public void formatXml() throws Exception {
        String xml = XmlUtil.documentToXmlString(document);

        System.out.println(xml);
        System.out.println(XmlUtil.formatXml(xml));
    }

    @After
    public void tearDown() throws Exception {

    }
}