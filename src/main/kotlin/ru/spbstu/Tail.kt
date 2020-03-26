package ru.spbstu


import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException


class Tail(
    private val inputOption: InputOption,
    private val amount: Int
) {

    @Throws(IOException::class)
    fun takeTail(reader: BufferedReader, writer: BufferedWriter) {
        if (inputOption == InputOption.LastSymbols) {
            readLastSymbols(reader, writer)
        } else {
            readLastLines(reader, writer)
        }
    }

    private fun readLastLines(reader: BufferedReader, writer: BufferedWriter) {
        val list = mutableListOf<String>()
        while (reader.ready()) {
            if (list.size == amount) {
                list.removeAt(0)
            }
            list.add(reader.readLine())
        }
        writer.write(list.joinToString("\n"))
        writer.newLine()
    }

    private fun readLastSymbols(reader: BufferedReader, writer: BufferedWriter) {
        val list = mutableListOf<Char>()
        while (reader.ready()) {
            if (list.size == amount) {
                list.removeAt(0)
            }
            list.add(reader.read().toChar())
        }
        writer.write(list.joinToString(""))
        writer.newLine()
    }
}

