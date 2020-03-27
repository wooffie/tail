package ru.spbstu


import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Класс утилиты, хранит в себе что и сколько мы считываем.
 */
class Tail(
    private val inputOption: InputOption,
    private val length: Int
) {
    /**
     *  Функция запуска утилиты для передаваемого входного и выходного потоков.
     */
    fun takeTail(reader: BufferedReader, writer: BufferedWriter) {
        if (inputOption == InputOption.SYMBOLS) {
            tailSymbols(reader, writer)
        } else {
            tailLines(reader, writer)
        }
    }

    /**
     * Выделение из файла последних строк.
     */
    private fun tailLines(reader: BufferedReader, writer: BufferedWriter) {
        val lines = mutableListOf<String>()
        while (reader.ready()) {
            if (lines.size == length) {
                lines.removeAt(0)
            }
            lines.add(reader.readLine())
        }
        writer.write(lines.joinToString("\n"))
        writer.newLine()
    }

    /**
     * Выделение последних символов.
     */
    private fun tailSymbols(reader: BufferedReader, writer: BufferedWriter) {
        val symbols = mutableListOf<Char>()
        while (reader.ready()) {
            if (symbols.size == length) {
                symbols.removeAt(0)
            }
            symbols.add(reader.read().toChar())
        }
        writer.write(symbols.joinToString(""))
        writer.newLine()
    }
}

