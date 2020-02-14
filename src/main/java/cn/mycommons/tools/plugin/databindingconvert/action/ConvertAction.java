package cn.mycommons.tools.plugin.databindingconvert.action;

import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlElementFactory;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;

import java.util.ArrayList;
import java.util.List;

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
            if (psiFile == null) {
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

            XmlTag rootTag = xmlFile.getRootTag();
            if (rootTag == null) {
                break;
            }
            Project project = event.getData(PlatformDataKeys.PROJECT);
            if (project == null) {
                break;
            }

            modifyXml(xmlFile, rootTag, project);
        } while (false);
        if (msg != null && msg.length() != 0) {
            throw new RuntimeException(msg);
        }
    }

    private void modifyXml(final XmlFile xmlFile, final XmlTag rootTag, final Project project) {
        new WriteCommandAction.Simple<Boolean>(project, xmlFile) {
            @Override
            protected void run() throws Throwable {
                XmlAttribute[] attributes = rootTag.getAttributes();

                List<XmlAttribute> list = new ArrayList<>();
                for (XmlAttribute attribute : attributes) {
                    if (attribute.getName().startsWith("xmlns:")) {
                        list.add(attribute);
                    }
                }

                StringBuffer stringBuffer = new StringBuffer();
                for (XmlAttribute attribute : list) {
                    stringBuffer.append(attribute.getText()).append(" ");
                    attribute.delete();
                }

                XmlElementFactory factory = XmlElementFactory.getInstance(project);
                String format = "<layout %s>\n%s\n%s\n</layout>";
                String sequence = String.format(format, stringBuffer, getData(), rootTag.getText());
                XmlTag newXmlTag = factory.createTagFromText(sequence, XMLLanguage.INSTANCE);
                rootTag.replace(newXmlTag);
            }
        }.execute();
    }


    private String getData() {
        if (hasDataElement()) {
            return "<data>\n</data>";
        }
        return "\n";
    }

    protected boolean hasDataElement() {
        return true;
    }
}