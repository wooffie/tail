package ru.spbstu

import java.io.File
import java.io.IOException

// я сделал функции internal для тестов , по хорошему как класс , так и функции кроме start должны быть private

class Tail(
    private val outputFileName: String, private val inputFilesNames: List<String>,
    inputOption: String, private val amount: Int
) {

    /** Переменная для хранения типа вывода
     */
    private val trueIfLines: Boolean = inputOption == "n"

    /** Вспомогательный класс для более удобной работы с данными
     * По умолчанию хранит список списков. В последних в первой ячейке хранится название файла
     * Если название файла оказывается ненужным, то returnData() не возвращает его в списках
     */
    internal class OutputData {
        private val data = mutableListOf<List<String>>()

        fun add(fileName: String, fileData: List<String>) {
            data.add(listOf(fileName).plus(fileData))
        }

        fun returnData(): List<List<String>> = if (data.size == 1) {
            listOf(data.first().drop(1))
        } else {
            data
        }

        // для тестов
        override fun equals(other: Any?) =
            other is OutputData &&
                    returnData().toSet() == other.returnData().toSet()

        override fun hashCode(): Int {
            return data.hashCode()
        }

    }

    /** Функция начала работы утилиты. Тут проводятся проверки для того , чтобы понимать откуда и куда мы будем выводить
     */
    fun start() {
        if (outputFileName.isNotEmpty()) {
            if (inputFilesNames.isNotEmpty()) {
                writeInFile(readFromFile()) // Files -> File
            } else {
                writeInFile(readFromCmd()) // Cmd -> File
            }
        } else {
            if (inputFilesNames.isNotEmpty()) {
                writeInCmd(readFromFile()) // Files -> Cmd
            } else {
                writeInCmd(readFromCmd()) // Cmd -> File
            }
        }
    }

    /** Функция которая читает нужные нам файлы и записывает только необходимую информацию для вывода пользователю
     *  Возращает готовые данные для вывода в экземпляре класса OutputData
     */
    @Throws(IOException::class)
    internal fun readFromFile(): OutputData {
        val outData = OutputData()
        for (fileName in inputFilesNames) {
            if (trueIfLines) {
                outData.add(fileName, File(fileName).readLines().takeLast(amount))
            } else {
                outData.add(fileName, listOf(File(fileName).readText().takeLast(amount)))
            }
        }
        return outData
    }

    /** Функция которая читает командную строку и записывает только необходимую информацию для вывода пользователю
     *  Возращает готовые данные для вывода в экземпляре класса OutputData
     */
    internal fun readFromCmd(): OutputData {
        val outData = OutputData()
        println("Input text:")
        outData.add("", listOf(readLine()!!.takeLast(amount)))
        return outData
    }

    /** Функция для записи в файл , принимает необходимые данные и записывает в файл.
     */
    @Throws(IOException::class)
    internal fun writeInFile(outData: OutputData) {
        File(outputFileName).writeText(outData.returnData().joinToString("\n") { it.joinToString("\n") })
    }


    /** Функция вывода в командную строку
     */
    internal fun writeInCmd(outData: OutputData) {
        print(outData.returnData().joinToString("\n") { it.joinToString("\n") })
    }
}

