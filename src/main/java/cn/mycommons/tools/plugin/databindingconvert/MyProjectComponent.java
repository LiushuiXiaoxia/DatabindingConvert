package cn.mycommons.tools.plugin.databindingconvert;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

/**
 * cn.mycommons.tools.plugin.databindingconvert.TestProjectComponent <br/>
 * Created by xiaqiulei on 2016-09-09.
 */
public class MyProjectComponent implements ProjectComponent {

    public MyProjectComponent(Project project) {

        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestProjectComponent.<init> project = " + project);
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here

        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestProjectComponent.initComponent");
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestProjectComponent.disposeComponent");
    }

    @Override
    @NotNull
    public String getComponentName() {
        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestProjectComponent.getComponentName");
        return "cn.mycommons.tools.plugin.databindingconvert.TestProjectComponent";
    }

    @Override
    public void projectOpened() {
        // called when project is opened
        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestProjectComponent.projectOpened");
    }

    @Override
    public void projectClosed() {
        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestProjectComponent.projectClosed");
        // called when project is being closed
    }
}