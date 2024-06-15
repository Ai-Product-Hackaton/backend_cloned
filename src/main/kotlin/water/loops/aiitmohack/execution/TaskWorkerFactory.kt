package water.loops.aiitmohack.execution

import jakarta.transaction.SystemException
import org.apache.commons.exec.CommandLine
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path

@Component
class TaskWorkerFactory(

) {

    companion object {
        val log = LoggerFactory.getLogger(TaskWorkerFactory::class.java)!!
    }

    fun create(task: Task): TaskWorker {
        try {
            val outputFile = Files.createTempFile("ksrc_output", ".json")
            val logFile = Files.createTempFile("ksrc_report", ".txt")
            val inputFile = Files.createTempFile("ksrc_input", ".json")
            log.info(
                "Result of task running will be saved to {}, logs are in the file {}",
                outputFile.toString(),
                logFile.toString()
            )
            val runnable = CommandLineRunner(createCommandLine(inputFile, outputFile, logFile), logFile)
            return TaskWorker(
                task,
                logFile,
                outputFile,
                runnable,
                //taskFileSaver,
                //taskDataHelper
            )
        } catch (e: Exception) {
            throw RuntimeException("Can't create task worker", e)
        }
    }

    private fun createCommandLine(
        inputFile: Path,
        outputFile: Path,
        logFile: Path
    ): CommandLine {
        val commandLine = CommandLine("python3")
        commandLine.addArgument("src/main/python/classify.py")
        commandLine.addArgument("--input_file")
        commandLine.addArgument(inputFile.toString())
        commandLine.addArgument("--output_file")
        commandLine.addArgument(outputFile.toString())
        commandLine.addArgument("--report_file")
        commandLine.addArgument(logFile.toString())
        return commandLine
    }
}