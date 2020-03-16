package ru.spbstu


import java.io.*


class Tail(
    private val outputFileName: String, private val inputFilesNames: List<String>,
    private val inputOption: InputOption, private val amount: Int
) {
    private val printFileNames = inputFilesNames.size > 1

    @Throws(IOException::class)
    fun start() {
        val outStream: OutputStreamWriter = if (outputFileName == "") {
            OutputStreamWriter(System.out)
        } else {
            OutputStreamWriter(FileOutputStream(outputFileName))
        }

        if (inputFilesNames.isNotEmpty()) {
            for (fileName in inputFilesNames) {
                if (printFileNames) {
                    outStream.write("$fileName:\n")
                }
                outStream.write(readData(FileInputStream(File(fileName)), amount).toString() + "\n")

            }
        } else {
            val tmpFile = createTempFile("tmp")
            tmpFile.writeBytes(System.`in`.readAllBytes())
            println(tmpFile.readText())
            outStream.write(readData(FileInputStream(tmpFile),amount).toString() + "\n")
            tmpFile.deleteOnExit()
        }
        outStream.close()
    }

    private fun readData(inputStream: FileInputStream, amount: Int): StringBuilder {
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

}


fun readRandomAccess(file: File, amount: Int): StringBuilder {
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
    return result.reverse()
}

fun readFromFile1(inputStream: FileInputStream, amount: Int): StringBuilder {
    val result = StringBuilder()
    val fis = inputStream.channel
    var pos = fis.size() - 1
    var flag = 0
    while (flag != amount && pos >= 0) {
        fis.position(pos)
        val c = inputStream.read().toChar()
        result.append(c)
        flag++
        pos--
    }
    return result.reverse()
}

fun main(){
    Tail("", listOf(),InputOption.LastSymbols,5).start()
}
