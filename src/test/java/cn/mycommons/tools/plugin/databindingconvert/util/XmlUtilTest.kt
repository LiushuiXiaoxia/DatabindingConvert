package cn.mycommons.tools.plugin.databindingconvert.util

import cn.mycommons.tools.plugin.databindingconvert.util.XmlUtil.documentToXmlString
import cn.mycommons.tools.plugin.databindingconvert.util.XmlUtil.formatXml
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document
import javax.xml.parsers.DocumentBuilderFactory

/**
 * XmlUtilTest <br></br>
 * Created by xiaqiulei on 2016-09-14.
 */
class XmlUtilTest {

    lateinit var document: Document

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        document = builder.newDocument()
        initXmlData()
    }

    private fun initXmlData() {
        val root = document.createElement("layout")
        document.appendChild(root)
        val data = document.createElement("data")
        root.appendChild(data)
        val importElement = document.createElement("import")
        importElement.setAttribute("type", "")
        data.appendChild(importElement)
        val variableElement = document.createElement("variable")
        variableElement.setAttribute("name", "")
        variableElement.setAttribute("type", "")
        data.appendChild(variableElement)
    }

    @Test
    @Throws(Exception::class)
    fun documentToXmlString() {
        val xml = documentToXmlString(document)
        println(xml)
    }

    @Test
    @Throws(Exception::class)
    fun formatXml() {
        val xml = documentToXmlString(document)
        println(xml)
        println(formatXml(xml))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }
}