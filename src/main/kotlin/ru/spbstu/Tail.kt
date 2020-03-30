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
    fun get(reader: BufferedReader, writer: BufferedWriter) {
        if (inputOption == InputOption.SYMBOLS) {
            lastSymbols(reader, writer)
        } else {
            lastLines(reader, writer)
        }
    }

    /**
     * Cut last lines
     * @param reader - buffered reader of input stream
     * @param writer - buffered writer of output stream
     */
    private fun lastLines(reader: BufferedReader, writer: BufferedWriter) {
        val linesToWrite = ArrayDeque<String>()
        var lastLine = reader.readLine()
        while (lastLine != null) {
            if (linesToWrite.size == length) {
                linesToWrite.pollFirst()
            }
            linesToWrite.add(lastLine)
            lastLine = reader.readLine()
        }
        writer.write(linesToWrite.joinToString("\n"))
        writer.newLine()
    }

    /**
     * Cut last symbols
     * @param reader - buffered reader of input stream
     * @param writer - buffered writer of output stream
     */
    private fun lastSymbols(reader: BufferedReader, writer: BufferedWriter) {
        val symbolsToWrite = ArrayDeque<Char>()
        var char = reader.read()
        while (char != -1) {
            if (symbolsToWrite.size == length) {
                symbolsToWrite.pollFirst()
            }
            symbolsToWrite.add(char.toChar())
            char = reader.read()
        }
        writer.write(symbolsToWrite.joinToString(""))
        writer.newLine()
    }
}