package ru.spbstu

import java.io.*

class Tail(
    private val outputFileName: String, private val inputFilesNames: List<String>,
    private val inputOption: InputOption, private val amount: Int
) {
    private val printFileNames = inputFilesNames.size > 1
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


fun main() {
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

fun foo3(){
    val file = createTempFile("tmp")
    file.writeBytes(System.`in`.readAllBytes())
    println(foo2(file,6).toString())
    file.deleteOnExit()
}





//foo 2 is faster