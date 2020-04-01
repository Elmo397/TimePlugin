package plugin.widget

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.wm.WindowManager
import com.intellij.openapi.wm.impl.status.IdeStatusBarImpl
import plugin.widget.TimeWidgetProvider

class TimeStatusBar: StartupActivity {
    override fun runActivity(project: Project) {
        val statusBar = WindowManager.getInstance().getStatusBar(project)
        val provider = TimeWidgetProvider()

        (statusBar!! as IdeStatusBarImpl).doAddWidget(provider.getWidget(project)!!, provider.anchor)
    }
}