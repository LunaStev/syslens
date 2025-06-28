import javax.swing.*
import kotlin.concurrent.fixedRateTimer

object NativeBridge {
    init {
        System.load("${System.getProperty("user.dir")}/libs/syslens.dll")
    }

    @JvmStatic external fun get_sys_info(): String
}

fun main() {
    val frame = JFrame("SysLens")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(400, 200)

    val label = JLabel("Loading...", SwingConstants.CENTER)
    frame.add(label)
    frame.isVisible = true

    fixedRateTimer("sysinfo", initialDelay = 0, period = 2000) {
        val info = NativeBridge.get_sys_info()
        SwingUtilities.invokeLater {
            label.text = "<html><pre>$info</pre></html>"
        }
    }
}
