package water.loops.aiitmohack.execution

import org.slf4j.LoggerFactory
import java.nio.file.Path
import java.util.concurrent.CompletionException
import java.util.concurrent.atomic.AtomicBoolean

class TaskWorker(
    val task: Task,
    val logFile: Path? = null,
    val outputFile: Path? = null,
    private val runnable: Runnable? = null,
    private val running: AtomicBoolean = AtomicBoolean(true)
): Runnable {

    companion object {
        val log = LoggerFactory.getLogger(TaskWorker::class.java)!!
    }

    fun complete(r: Void, t: Throwable?): Void {

        return r
    }

    fun stop() {
        running.set(false)
        if (runnable is AutoCloseable) {
            try {
                runnable.close()
            } catch (e: Exception) {
                log.error("Error during attempt to stop execution of task {}", task, e)
            }
        }
    }

    private fun handleException(t: Throwable) {
        if (t is RuntimeException) {
            throw t
        }
        throw CompletionException(t)
    }

    override fun run() {
        if (running.get()) {
            runnable!!.run()
        }
    }
}