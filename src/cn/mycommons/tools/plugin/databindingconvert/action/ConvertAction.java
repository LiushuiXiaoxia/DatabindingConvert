package cn.mycommons.tools.plugin.databindingconvert.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.RefreshQueue;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cn.mycommons.tools.plugin.databindingconvert.model.KeyValue;
import cn.mycommons.tools.plugin.databindingconvert.util.IOUtil;
import cn.mycommons.tools.plugin.databindingconvert.util.XmlUtil;

/**
 * MainAction <br/>
 * Created by xiaqiulei on 2016-09-09.
 */
public class ConvertAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        boolean visible = isFileOk(e);
        e.getPresentation().setVisible(visible);
    }

    private boolean isFileOk(AnActionEvent e) {
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        boolean isVFOk = virtualFile != null && virtualFile.getName().toLowerCase().endsWith("xml");
        boolean isPsiOk = psiFile != null && psiFile.getName().toLowerCase().endsWith("xml");
        return isVFOk && isPsiOk;
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            doAction(event);
        } catch (Exception e) {
            Project project = event.getData(PlatformDataKeys.PROJECT);
            Messages.showMessageDialog(project, e.getMessage(), "Warning", Messages.getWarningIcon());
        }
    }

    private void doAction(AnActionEvent event) throws Exception {
        String msg = null;
        do {
            PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
            VirtualFile virtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
            if (psiFile == null || virtualFile == null) {
                msg = "No file";
                break;
            }

            if (!(psiFile instanceof XmlFile)) {
                msg = "No xml file";
                break;
            }
            XmlFile xmlFile = (XmlFile) psiFile;

            String text = xmlFile.getText();
            if (text.contains("<layout>") || text.contains("</layout>")) {
                msg = "Current xml file is already converted.";
                break;
            }

            XmlDocument xmlDocument = xmlFile.getDocument();
            if (xmlDocument != null) {
                List<KeyValue> xmlns = getXmlns(xmlDocument);
                XmlTag rootTag = xmlDocument.getRootTag();
                Document newDocument = genDatabindingDocument(rootTag, xmlns);
                String xmlStr = XmlUtil.documentToXmlString(newDocument);
                xmlStr = XmlUtil.formatXml(xmlStr);

                // 删除最后一个回车
                if (xmlStr.endsWith("\n")) {
                    xmlStr = new StringBuffer(xmlStr).deleteCharAt(xmlStr.length() - 1).toString();
                }

                // 写入文件
                IOUtil.writeToFile(virtualFile.getPath(), xmlStr);
                virtualFile.refresh(true, true);
                RefreshQueue.getInstance().createSession(true, true, () -> {

                });

                // 格式化,不知道怎么写?
//                new XmlFormattingModelBuilder()
//                        .createModel(psiFile.getOriginalElement(), new CodeStyleSettings())
//                        .commitChanges();
            }
        } while (false);
        if (msg != null && msg.length() != 0) {
            throw new RuntimeException(msg);
        }
    }

    private List<KeyValue> getXmlns(@NotNull XmlDocument document) {
        List<KeyValue> list = new ArrayList<>();
        do {
            XmlTag rootTag = document.getRootTag();
            if (rootTag == null) {
                break;
            }
            XmlAttribute[] attributes = rootTag.getAttributes();
            for (XmlAttribute attribute : attributes) {
                if (attribute.getName().startsWith("xmlns:")) {
                    list.add(new KeyValue(attribute.getName(), attribute.getValue()));
                }
            }
        } while (false);
        return list;
    }

    @NotNull
    private Document genDatabindingDocument(XmlTag rootTag, List<KeyValue> xmlns) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document newDocument = builder.newDocument();
        Element root = newDocument.createElement("layout");
        newDocument.appendChild(root);

        List<String> keys = new ArrayList<>();
        for (KeyValue item : xmlns) {
            Attr attr = newDocument.createAttribute(item.key);
            attr.setValue(item.value);
            root.setAttributeNode(attr);
            keys.add(item.key);
        }

        if (hasDataElement()) {
            Element data = newDocument.createElement("data");
            Element importElement = newDocument.createElement("import");
            importElement.setAttribute("type", "");
            data.appendChild(importElement);
            Element variableElement = newDocument.createElement("variable");
            variableElement.setAttribute("name", "");
            variableElement.setAttribute("type", "");
            data.appendChild(variableElement);
            root.appendChild(data);
        }

        Element newRoot = newDocument.createElement(rootTag.getName());
        copyXml(newDocument, rootTag, newRoot, root, keys);
        return newDocument;
    }

    private void copyXml(Document document, XmlTag sourceTag,
                         Element targetElement, Element parent, List<String> excludes) {
        do {
            if (sourceTag == null) {
                break;
            }
            parent.appendChild(targetElement);
            XmlAttribute[] attributes = sourceTag.getAttributes();
            for (XmlAttribute attribute : attributes) {
                if (excludes != null && excludes.contains(attribute.getName())) {
                    continue;
                }
                targetElement.setAttribute(attribute.getName(), attribute.getValue());
            }
            for (XmlTag xmlTag : sourceTag.getSubTags()) {
                if (xmlTag == null) {
                    continue;
                }
                Element newElement = document.createElement(xmlTag.getName());
                copyXml(document, xmlTag, newElement, targetElement, excludes);
            }
        } while (false);
    }

    protected boolean hasDataElement() {
        return true;
    }
}