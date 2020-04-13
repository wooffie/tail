package ru.spbstu

import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.*

/**
 * Main method
 * @param args - command line's arguments
 */
fun main(args: Array<String>) {
    TailLauncher().launch(args)
}

/**
 * Launcher class
 */
class TailLauncher {
    @Option(name = "-c", metaVar = "LastSymbols", required = false, usage = "Take last *num* symbols")
    private var c :Int? = null

    @Option(name = "-n", metaVar = "LastLine", required = false, usage = "Take last *num* lines")
    private var n : Int? = null

    @Option(name = "-o", metaVar = "OutputName", required = false, usage = "Output file name")
    private var outputFileName = ""

    @Argument(required = false, metaVar = "InputFiles", usage = "Input files names")
    private var inputFilesNames = mutableListOf<String>()

    /**
     * Main function of class. Do tail according with input data.
     * @param args - command line's arguments
     */
    fun launch(args: Array<String>) {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(*args)
        } catch (e: CmdLineException) {
            println(e.message)
            println("java -jar tail.jar [-c num|-n num] [-o ofile] file0 file1 file2")
            parser.printUsage(System.out)
            return
        }

        val tail = when {
            c != null && n != null -> {
                println("Choose only one option")
                println(parser.printUsage(System.out))
                return
            }
            c == null && n == null -> Tail(CutOption.LINES, 10)
            c != null && c!! > 0 -> Tail(CutOption.SYMBOLS, c!!)
            n != null && n!! > 0 -> Tail(CutOption.LINES, n!!)
            else -> {
                println("Illegal option's amount")
                return
            }
        }
        try {
            val writer = if (outputFileName == "") {
                BufferedWriter(OutputStreamWriter(System.out))
            } else {
                BufferedWriter(OutputStreamWriter(FileOutputStream(outputFileName)))
            }

            if (inputFilesNames.isEmpty()) {
                val reader = BufferedReader(InputStreamReader(System.`in`))
                tail.get(reader, writer)
            }
            for (fileName in inputFilesNames) {
                if (inputFilesNames.size > 1) {
                    writer.write("$fileName:")
                    writer.newLine()
                }
                tail.get(BufferedReader(InputStreamReader(FileInputStream(fileName))), writer)
                writer.newLine()
            }
            writer.close()
        } catch (e: IOException) {
            println(e.message)
            return
        }
    }
}