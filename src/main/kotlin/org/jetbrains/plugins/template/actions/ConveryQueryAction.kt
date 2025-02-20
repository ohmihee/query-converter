package org.jetbrains.plugins.template.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager

class ConveryQueryAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("QueryToolWindow")
        toolWindow?.show(null) // 버튼을 누르면 Tool Window 표시
    }
}