package cn.mycommons.tools.plugin.databindingconvert.action

import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.XmlElementFactory
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import java.util.*

/**
 * ConvertAction <br></br>
 * Created by xiaqiulei on 2016-09-09.
 */
open class ConvertAction : AnAction() {

    override fun update(e: AnActionEvent) {
        super.update(e)
        val visible = isFileOk(e)
        e.presentation.isVisible = visible
    }

    private fun isFileOk(e: AnActionEvent): Boolean {
        val virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        val psiFile = e.getData(LangDataKeys.PSI_FILE)
        val isVFOk = virtualFile?.name?.toLowerCase()?.endsWith("xml") ?: false
        val isPsiOk = psiFile?.name?.toLowerCase()?.endsWith("xml") ?: false
        return isVFOk && isPsiOk
    }

    override fun actionPerformed(event: AnActionEvent) {
        try {
            doAction(event)
        } catch (e: Exception) {
            val project = event.getData(PlatformDataKeys.PROJECT)
            Messages.showMessageDialog(project, e.message, "Warning", Messages.getWarningIcon())
        }
    }

    @Throws(Exception::class)
    private fun doAction(event: AnActionEvent) {
        var msg: String? = null
        do {
            val psiFile = event.getData(LangDataKeys.PSI_FILE)
            if (psiFile == null) {
                msg = "No file"
                break
            }
            if (psiFile !is XmlFile) {
                msg = "No xml file"
                break
            }
            val text = psiFile.text
            if (text.contains("<layout>") || text.contains("</layout>")) {
                msg = "Current xml file is already converted."
                break
            }
            val rootTag = psiFile.rootTag ?: break
            val project = event.getData(PlatformDataKeys.PROJECT) ?: break
            modifyXml(rootTag, project)
        } while (false)
        if (msg != null && msg.isNotEmpty()) {
            throw RuntimeException(msg)
        }
    }

    private fun modifyXml(rootTag: XmlTag, project: Project) {
        WriteCommandAction.runWriteCommandAction(project) {
            val attributes = rootTag.attributes
            val list: MutableList<XmlAttribute> = ArrayList()
            for (attribute in attributes) {
                if (attribute.name.startsWith("xmlns:")) {
                    list.add(attribute)
                }
            }
            val stringBuffer = StringBuffer()
            for (attribute in list) {
                stringBuffer.append(attribute.text).append(" ")
                attribute.delete()
            }
            val factory = XmlElementFactory.getInstance(project)
            val format = "<layout %s>\n%s\n%s\n</layout>"
            val sequence = String.format(format, stringBuffer, getData(), rootTag.text)
            val newXmlTag = factory.createTagFromText(sequence, XMLLanguage.INSTANCE)
            rootTag.replace(newXmlTag)
        }
    }

    private fun getData(): String {
        if (hasDataElement()) {
            return "<data>\n</data>"
        }
        return "\n"
    }

    protected open fun hasDataElement(): Boolean {
        return true
    }
}