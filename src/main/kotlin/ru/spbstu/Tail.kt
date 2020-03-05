package ru.spbstu

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.RandomAccessFile

class Tail(
    private val outputFileName: String, private val inputFilesNames: List<String>,
    private val inputOption: InputOption, private val amount: Int
) {
    private val printFileNames = inputFilesNames.size > 1

    @Throws(IOException::class)
    fun start() {
        val data = StringBuilder()
        if (inputFilesNames.isNotEmpty()) {
            for (fileName in inputFilesNames) {
                if (printFileNames) {
                    data.append("$fileName:\n")
                }
                data.append(read(FileInputStream(File(fileName)), amount),"\n")
            }
        } else {
            /// TODO()
        }

        write(data)
    }

    private fun read(inputStream: FileInputStream, amount: Int): StringBuilder {
        val result = StringBuilder()
        val fis = inputStream.channel
        var pos = fis.size() - 1
        var flag = 0
        while (flag != amount && pos >= 0) {
            fis.position(pos)
            val c = inputStream.read().toChar()
            result.append(c)
            if (inputOption == InputOption.LastSymbols) {
                flag++
            } else {
                if (c == '\n') {
                    flag++
                }
            }
            pos--
        }
        return result.reverse()
    }

    @Throws(IOException::class)
    private fun write(data: StringBuilder) {
        if (outputFileName.isEmpty()) {
            print(data.toString())
        } else {
            File(outputFileName).writeText(data.toString())
        }
    }
}


fun foo(inputStream: FileInputStream, amount: Int): StringBuilder {
    val result = StringBuilder()
    val time = System.currentTimeMillis()
    val fis = inputStream.channel
    var pos = fis.size() - 1
    var flag = 0
    while (flag != amount) {
        fis.position(pos)
        result.append(inputStream.read().toChar())
        flag++
        pos--
    }
    println("time: ${System.currentTimeMillis() - time} ms")
    return result.reverse()
}

fun foos() {
    val y = FileInputStream(File("txt.txt"))
    println(foo(y, 6))
    println(foo2(File("txt.txt"), 6))
    println(foo3())
}

fun foo2(file: File, amount: Int): StringBuilder {
    val time = System.currentTimeMillis()
    val result = StringBuilder()
    val randomAccessFile = RandomAccessFile(file, "r")
    var pointer = file.length() - 1
    var flag = 0
    while (pointer >= 0 && flag < amount) {
        randomAccessFile.seek(pointer)
        val nextChar = randomAccessFile.read().toChar()
        result.append(nextChar)
        flag++
        pointer--
    }
    println("time: ${System.currentTimeMillis() - time} ms")
    return result.reverse()
}

fun foo3() {
    val file = createTempFile("tmp")
    file.writeBytes(System.`in`.readAllBytes())
    println(foo2(file, 6).toString())
    file.deleteOnExit()
}
