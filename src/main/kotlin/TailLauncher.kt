import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option


fun main(args: Array<String>){
    TailLauncher().launch(args)
}


class TailLauncher{
    @Option(name = "-c",metaVar = "LastSymbols", required = false, usage="Take last *num* symbols")
    private var c = 0

    @Option(name = "-n",metaVar = "LastLine", required = false, usage="Take last *num* lines")
    private var n = 0

    @Option(name = "-o",metaVar = "OutputName", required = false, usage="Output file name")
    private var outputFileName = ""

    @Argument( required = false , metaVar = "InputFiles", usage="Input files names")
    private var inputFilesNames = mutableListOf<String>()


    fun launch(args: Array<String>){
        val parser = CmdLineParser(this)
        try{
            parser.parseArgument(*args)
        }catch (e: CmdLineException){
            println(e.message)
            println("java -jar tail.jar [-c num|-n num] [-o ofile] file0 file1 file2")
            parser.printUsage(System.out)
            return
        }

        if (c < 0 || n < 0 || c+n ==0){
            throw Exception("Incorrect options")
        }

    }
}

