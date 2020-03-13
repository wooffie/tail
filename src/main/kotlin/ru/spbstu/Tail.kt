package ru.spbstu


import java.io.*


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
                val stream = FileInputStream(File(fileName))
                data.append(readFromFile(stream, amount), "\n")
                stream.close()
            }
        } else {
            data.append(readFromCmd())
        }

        write(data)
    }

    private fun readFromFile(inputStream: FileInputStream, amount: Int): StringBuilder {
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

    private fun readFromCmd(): StringBuilder {

        return StringBuilder()
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

