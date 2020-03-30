package ru.spbstu

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.*

/**
 * Test class
 */
class TailTest {

    /**
     * Test for tailing last lines
     */
    @Test
    fun lastLines() {
        val reader = BufferedReader(InputStreamReader(FileInputStream(File("src\\test\\resources\\text.txt"))))
        val file = createTempFile("tmp.txt")
        val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file)))
        Tail(InputOption.LINES, 3).get(reader, writer)
        writer.close()
        assertEquals(File("src\\test\\resources\\text.txt").readLines().takeLast(3), file.readLines())
        file.deleteOnExit()
    }

    /**
     * Test for tailing last symbols
     */
    @Test
    fun lastSymbols() {
        val reader = BufferedReader(InputStreamReader(FileInputStream(File("src\\test\\resources\\text.txt"))))
        val file = createTempFile("tmp.txt")
        val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file)))
        Tail(InputOption.SYMBOLS, 10).get(reader, writer)
        writer.close()
        assertEquals(File("src\\test\\resources\\text.txt").readText().takeLast(10), file.readText().dropLast(2))
        file.deleteOnExit()
    }

    /**
     * Test for output if we have several files
     */
    @Test
    fun severalFiles() {
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        TailLauncher().launch(arrayOf("-c", "10", "src\\test\\resources\\text.txt", "src\\test\\resources\\text2.txt"))
        assertEquals(
            "src\\test\\resources\\text.txt:\r\n" +
                    "the child.\r\n" +
                    "\r\n" +
                    "src\\test\\resources\\text2.txt:\r\n" +
                    "ight(Node)\r\n" +
                    "\r\n", outContent.toString()
        )
    }

    /**
     * Test for reading data from console
     */
    @Test
    fun readFromCmd() {
        System.setIn(ByteArrayInputStream("There are several lines of evidence for the persistence of HCBD in the environment.".toByteArray()))
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        TailLauncher().launch(arrayOf("-c", "10"))
        assertEquals("vironment.", outContent.toString().dropLast(2))
    }
}