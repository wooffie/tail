package ru.spbstu

import java.io.*


class TailHeadOn(
    private val outputFileName: String, private val inputFilesNames: List<String>,
    private val inputOption: InputOption, private val amount: Int
) {
    private val printFileNames = inputFilesNames.size > 1

    @Throws(IOException::class)
    fun start() {
        val x: OutputStreamWriter = if (outputFileName == "") {
            OutputStreamWriter(System.out)
        } else {
            OutputStreamWriter(FileOutputStream(outputFileName))
        }

        if (inputFilesNames.isNotEmpty()) {
            for (fileName in inputFilesNames) {
                if (printFileNames) {
                    x.write("$fileName:\n")
                }
                if (inputOption == InputOption.LastLines) {
                    x.write(readLastLines(InputStreamReader(FileInputStream(fileName))).joinToString("\n"))
                } else {
                    x.write(readLastSymbols(InputStreamReader(FileInputStream(fileName))).joinToString(""))
                }
            }
        } else {
            val buf = InputStreamReader(System.`in`)
            BufferedReader(buf).readText()
            if (inputOption == InputOption.LastLines) {
                x.write(readLastLines(buf).joinToString("\n"))
            } else {
                x.write(readLastSymbols(buf).joinToString(""))
            }
        }
    }

    private fun readLastLines(inputStream: InputStreamReader): List<String> {
        val list = mutableListOf<String>()
        val reader = BufferedReader(inputStream)
        while (reader.ready()) {
            if (list.size == amount) {
                list.removeAt(0)
            }
            list.add(reader.readLine())
        }
        return list
    }

    private fun readLastSymbols(inputStream: InputStreamReader): List<Char> {
        val list = mutableListOf<Char>()
        val reader = BufferedReader(inputStream)
        while (reader.ready()) {
            if (list.size == amount) {
                list.removeAt(0)
            }
            list.add(reader.read().toChar())
        }
        return list
    }

}

fun main(){
    TailHeadOn("out.txt", listOf(),InputOption.LastSymbols,5).start()
}