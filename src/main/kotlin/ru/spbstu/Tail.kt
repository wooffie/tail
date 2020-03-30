package ru.spbstu


import java.io.BufferedReader
import java.io.BufferedWriter
import java.util.*

/**
 * Tail class with records the last piece of file.
 * @param inputOption read last LINES or SYMBOLS
 * @param length size of cut
 */
class Tail(
    private val inputOption: InputOption,
    private val length: Int
) {
    /**
     *  Function to cut ending of file
     *  @param reader - buffered reader of input stream
     *  @param writer - buffered writer of output stream
     */
    fun takeTail(reader: BufferedReader, writer: BufferedWriter) {
        if (inputOption == InputOption.SYMBOLS) {
            tailSymbols(reader, writer)
        } else {
            tailLines(reader, writer)
        }
    }

    /**
     * Cut last lines
     * @param reader - buffered reader of input stream
     * @param writer - buffered writer of output stream
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

    /**
     * Cut last symbols
     * @param reader - buffered reader of input stream
     * @param writer - buffered writer of output stream
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