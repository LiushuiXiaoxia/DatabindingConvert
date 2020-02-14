//package cn.mycommons.tools.plugin.databindingconvert
//
//import com.intellij.openapi.components.ProjectComponent
//import com.intellij.openapi.project.Project
//
///**
// * MyProjectComponent <br></br>
// * Created by xiaqiulei on 2016-09-09.
// */
//class MyProjectComponent(project: Project) : ProjectComponent {
//
//    init {
//        println("cn.mycommons.tools.plugin.databindingconvert.<init> project = $project")
//    }
//
//    override fun initComponent() {
//        println("cn.mycommons.tools.plugin.databindingconvert.initComponent")
//    }
//
//    override fun disposeComponent() {
//        println("cn.mycommons.tools.plugin.databindingconvert.disposeComponent")
//    }
//
//    override fun getComponentName(): String {
//        println("cn.mycommons.tools.plugin.databindingconvert.getComponentName")
//        return "cn.mycommons.tools.plugin.databindingconvert"
//    }
//
//    override fun projectOpened() {
//        println("cn.mycommons.tools.plugin.databindingconvert.projectOpened")
//    }
//
//    override fun projectClosed() {
//        println("cn.mycommons.tools.plugin.databindingconvert.projectClosed")
//    }
//}