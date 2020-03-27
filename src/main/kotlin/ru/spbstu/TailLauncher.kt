package ru.spbstu

import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.*


fun main(args: Array<String>) {
    TailLauncher().launch(args)
}

/**
 * Класс который обрабатывает аргументы и запускает утилиту
 */
class TailLauncher {
    @Option(name = "-c", metaVar = "LastSymbols", required = false, usage = "Take last *num* symbols")
    private var c: Int? = null

    @Option(name = "-n", metaVar = "LastLine", required = false, usage = "Take last *num* lines")
    private var n: Int? = null

    @Option(name = "-o", metaVar = "OutputName", required = false, usage = "Output file name")
    private var outputFileName = ""

    @Argument(required = false, metaVar = "InputFiles", usage = "Input files names")
    private var inputFilesNames = mutableListOf<String>()


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


        // проверка входных данных и создание экземпляра класса Tail
        val tail = when {
            c != null && n != null -> {
                println("Illegal argument")
                println("java -jar tail.jar [-c num|-n num] [-o ofile] file0 file1 file2")
                return
            }
            c == null && n == null -> Tail(InputOption.LINES, 10)
            c != null && c!! > 0 -> Tail(InputOption.SYMBOLS, c!!)
            n != null && n!! > 0 -> Tail(InputOption.LINES, n!!)
            else -> {
                println("Illegal argument")
                println("java -jar tail.jar [-c num|-n num] [-o ofile] file0 file1 file2")
                return
            }
        }


        try {
            // определение выходного потока:
            val writer = if (outputFileName == "") {
                BufferedWriter(OutputStreamWriter(System.out))
            } else {
                BufferedWriter(OutputStreamWriter(FileOutputStream(outputFileName)))
            }

            // определение выходных потоков и выделение конца
            if (inputFilesNames.isEmpty()) {
                val reader = BufferedReader(InputStreamReader(System.`in`))
                // TODO() ввод в System.'in'
                tail.takeTail(reader, writer)
            }
            for (fileName in inputFilesNames) {
                if (inputFilesNames.size > 1) {
                    writer.write("$fileName:")
                    writer.newLine()
                }
                tail.takeTail(BufferedReader(InputStreamReader(FileInputStream(fileName))), writer)
                writer.newLine()
            }
            writer.close()
        } catch (e: IOException) {
            println(e.message)
            return
        }
    }
}

