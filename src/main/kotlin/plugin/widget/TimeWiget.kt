package plugin.widget

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Timer
import javax.swing.*

class TimeWidgetProvider: StatusBarWidgetProvider {
    override fun getWidget(project: Project): StatusBarWidget? {
        return TimeWidget()
    }
}

class TimeWidget: CustomStatusBarWidget {
    private val time = JLabel("00:00:00", SwingConstants.CENTER)
    private var timeFormatter = DateTimeFormatter.ofPattern("E, dd MMM, HH:mm:ss")

    override fun ID() = "TimeWidget"

    override fun getComponent(): JComponent {
        start()
        time.componentPopupMenu = createPopupMenu()

        return time
    }

    override fun install(statusBar: StatusBar) {}

    override fun dispose() {}

    private fun start() {
        val timer = Timer()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val localTime = LocalDateTime.now()
                time.text = timeFormatter.format(localTime)
            }
        }, 0, 1000)
    }

    private fun createPopupMenu(): JPopupMenu {
        val popup = JPopupMenu()
        popup.label = "View settings"

        createMenuItems().forEach { item ->
            popup.add(item)
        }

        return popup
    }

    private fun createMenuItems(): List<JMenuItem> {
        val fullDateTime = JMenuItem("Show full date and time")
        fullDateTime.addActionListener {
            timeFormatter = DateTimeFormatter.ofPattern("E, dd MMM, HH:mm:ss")
        }

        val onlyDate = JMenuItem("Show only date")
        onlyDate.addActionListener {
            timeFormatter = DateTimeFormatter.ofPattern("E, dd MMM")
        }

        val onlyTime = JMenuItem("Show only time without sec")
        onlyTime.addActionListener {
            timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        }

        return listOf(fullDateTime, onlyTime, onlyDate)
    }
}
