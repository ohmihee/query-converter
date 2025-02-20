package org.jetbrains.plugins.template.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.jetbrains.plugins.template.view.QueryConverter

class QueryToolWindowFactory: ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentManager = toolWindow.contentManager
        val content = contentManager.factory.createContent(QueryConverter(project, toolWindow), "", false)
        contentManager.addContent(content)


    }

    override fun shouldBeAvailable(project: Project) = true

}