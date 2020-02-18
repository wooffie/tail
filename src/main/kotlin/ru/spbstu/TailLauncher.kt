package ru.spbstu

import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.IOException

/**
 * Главная функция
 */
fun main(args: Array<String>){
    TailLauncher().launch(args)
}

/**
 * Класс который обрабатывает аргументы и запускает утилиту
 */
class TailLauncher{
    @Option(name = "-c",metaVar = "LastSymbols", required = false, usage="Take last *num* symbols")
    private var c : Int? = null // последние символы

    @Option(name = "-n",metaVar = "LastLine", required = false, usage="Take last *num* lines")
    private var n : Int? = null // последние строки

    @Option(name = "-o",metaVar = "OutputName", required = false, usage="Output file name")
    private var outputFileName = "" // файл вывода

    @Argument( required = false , metaVar = "InputFiles", usage="Input files names")
    private var inputFilesNames = mutableListOf<String>() // файлы ввода


    fun launch(args: Array<String>){
        val parser = CmdLineParser(this)
        try{
            parser.parseArgument(*args)
        }catch (e: CmdLineException){
            println(e.message)
            println("java -jar tail.jar [-c num|-n num] [-o ofile] file0 file1 file2")
            parser.printUsage(System.out) // если что-то не так то выдаём пользователю список опций и аргументов
            return
        }

        if (c == null && n == null){
            n = 10
        } else {
            if (c != null && n != null) {
                throw Exception("Incorrect options") // проверка того , чтобы не было одновременно -c и -n
            }
        }
        //запускаем утилиту
        try{
            if ((n ?: 0) > 0) {
                Tail(outputFileName, inputFilesNames, "n", n!!).start()
            } else {
                requireNotNull(c)
                require(c!! > 0) {"option must to be natural number"}
                Tail(outputFileName, inputFilesNames, "c", c!!).start()
            }
        }catch (e : IOException){
            println(e.message) // чтобы ничего не бросать в классе , делаем это тут
            // над функциями которые работают с файлами указаны аннотации
            return
        }
    }
}

