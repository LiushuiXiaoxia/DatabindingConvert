package cn.mycommons.tools.plugin.databindingconvert;

import com.intellij.openapi.components.ApplicationComponent;

import org.jetbrains.annotations.NotNull;

/**
 * cn.mycommons.tools.plugin.databindingconvert.TestApplicationComponent <br/>
 * Created by xiaqiulei on 2016-09-09.
 */
public class MyApplicationComponent implements ApplicationComponent {
    public MyApplicationComponent() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here

        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestApplicationComponent.initComponent");
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestApplicationComponent.component");

    }

    @Override
    @NotNull
    public String getComponentName() {
        System.out.println("cn.mycommons.tools.plugin.databindingconvert.TestApplicationComponent.getComponentName");
        return "cn.mycommons.tools.plugin.databindingconvert.TestApplicationComponent";
    }
}