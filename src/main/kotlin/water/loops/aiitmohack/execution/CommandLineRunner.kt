package water.loops.aiitmohack.execution

import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.ExecuteWatchdog
import org.apache.commons.exec.PumpStreamHandler
import org.slf4j.LoggerFactory
import java.io.*
import java.nio.file.Path

class CommandLineRunner(
    private val commandLine: CommandLine,
    private val logFile: Path? = null,
    private val watchDog: ExecuteWatchdog = ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT)
): Runnable, AutoCloseable {

    companion object {
        val log = LoggerFactory.getLogger(CommandLineRunner::class.java)!!
    }

    override fun run() {
        try {
            tryToRunCommandLine()
        } catch (e: Exception) {
            throw RuntimeException("Can't run command line $commandLine")
        }
    }

    @Throws(Exception::class)
    override fun close() {
        log.info("Destroying process {}", commandLine)
        watchDog.destroyProcess()
    }

    @Throws(IOException::class)
    private fun tryToRunCommandLine() {
        logStream.use { logStream ->
            val streamHandler =
                PumpStreamHandler(logStream)
            val executor = DefaultExecutor()
            executor.watchdog = watchDog
            executor.streamHandler = streamHandler
            val exitCode = executor.execute(commandLine)
            log.info("Result of command line execution is {} ", exitCode)
        }
    }

    @get:Throws(FileNotFoundException::class)
    private val logStream: OutputStream
        get() {
            logFile ?: return ByteArrayOutputStream()

            return FileOutputStream(logFile.toFile())
        }
}