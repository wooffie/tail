package ru.spbstu

import java.io.File
import java.io.IOException
import java.io.RandomAccessFile

class Tail(
    private val outputFileName: String, private val inputFilesNames: List<String>,
    private val inputOption: InputOption, private val amount: Int)
{
    private val printFileNames = inputFilesNames.size > 1


    fun start() {
        val result = mutableListOf<StringBuilder>()
        if (inputFilesNames.isEmpty()) {
            //
        } else {
            for (fileName in inputFilesNames) {
                result.add(readFromFile(fileName))
            }
        }
        write(result)
    }

    @Throws(IOException::class)
    private fun readFromFile(fileName: String): StringBuilder {
        File(fileName)
        val result = StringBuilder()
        val randomAccessFile = RandomAccessFile(File(fileName), "r")
        var pointer = File(fileName).length() - 1
        var flag = 0
        while (pointer >= 0 && flag < amount) {
            randomAccessFile.seek(pointer)
            val nextChar = randomAccessFile.read().toChar()
            if (nextChar == '\n' && inputOption == InputOption.LastLines) {
                flag++
                result.append(nextChar)
            }
            if (!nextChar.isISOControl()) {
                result.append(nextChar)
                if (inputOption == InputOption.LastSymbols) {
                    flag++
                }
            }
            pointer--
        }
        if (pointer < 0 && inputOption == InputOption.LastLines){
            result.append("\n")
        }

        return if (printFileNames) {
            if (inputOption == InputOption.LastLines) {
                StringBuilder("$fileName:").append(result.reverse(), "\n")
            } else {
                StringBuilder("$fileName:\n").append(result.reverse(), "\n")
            }
        } else {
            result.reverse()
        }
    }

    @Throws(IOException::class)
    private fun write(sb: List<StringBuilder>) {
        if (outputFileName.isEmpty()) {
            println(sb.joinToString(""))
        } else {
            File(outputFileName).writeText(sb.joinToString(""))
        }
    }
}


