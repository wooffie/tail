package ru.spbstu


import java.io.BufferedReader
import java.io.BufferedWriter
import java.util.*

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
        val lines = ArrayDeque<String>()
        var lastLine = reader.readLine()
        while (lastLine != null) {
            if (lines.size == length) {
                lines.pollFirst()
            }
            lines.add(lastLine)
            lastLine = reader.readLine()
        }
        writer.write(lines.joinToString("\n"))
        writer.newLine()
    }

    // dropWhile is slow
    /**
     * Выделение последних символов.
     */
    private fun tailSymbols(reader: BufferedReader, writer: BufferedWriter) {
        val symbols = ArrayDeque<Char>()
        var char = reader.read()
        while (char != -1) {
            if (symbols.size == length) {
                symbols.pollFirst()
            }
            symbols.add(char.toChar())
            char = reader.read()
        }
        writer.write(symbols.joinToString(""))
        writer.newLine()
    }
}