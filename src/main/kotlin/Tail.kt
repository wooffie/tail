import java.io.File
import java.io.IOException

class Tail(private val outputFileName : String , private val inputFilesNames : List<String>, inputOption : String , private val num : Int){
    private val trueIfLines : Boolean = inputOption == "n"


    fun start(){
        if (outputFileName.isNotEmpty()){
            if (inputFilesNames.isNotEmpty()){
                writeInFile(readFromFile())
            } else{
                writeInFile(readFromCmd())
            }
        } else {
            if (inputFilesNames.isNotEmpty()){
                writeInCmd(readFromFile())
            } else{
                writeInCmd(readFromCmd())
            }
        }
    }

    @Throws(IOException::class)
    fun readFromFile() : List<List<String>>{
        val result = mutableListOf<List<String>>()
        for(fileName in inputFilesNames){
            if (trueIfLines){
                result.add(File(fileName).readLines().takeLast(num))
            } else{
                result.add(listOf(File(fileName).readText().takeLast(num)))
            }
        }
        return result
    }

    fun readFromCmd() : List<List<String>>{
        TODO()
    }

    @Throws(IOException::class)
    fun writeInFile(lists : List<List<String>>) {
        if (inputFilesNames.size <= 1) {
            File(outputFileName).writeText(lists.first().joinToString("\n"))
        } else {
            File(outputFileName).bufferedWriter().use {
                for(ind in inputFilesNames.indices){
                    it.write("file: " + inputFilesNames[ind]+"\n")
                    it.write(lists[ind].joinToString("\n"))
                    it.newLine()
                }
            }
        }
    }

    fun writeInCmd(lists : List<List<String>>) {
        if (inputFilesNames.size <= 1) {
            println(lists.first().joinToString("\n"))
        } else {
                for(ind in inputFilesNames.indices){
                    println("file: " + inputFilesNames[ind])
                    println(lists[ind].joinToString("\n"))
                    println()
                }
        }
    }

}

fun main(){
    println(Tail("",listOf("C:\\Users\\woof\\IdeaProjects\\console_utility\\src\\main\\resources\\text.txt" ,
        "C:\\Users\\woof\\IdeaProjects\\console_utility\\src\\main\\resources\\text2.txt"),"n",4).start())

}