package ru.spbstu

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.*

//junit ru.spbstu.TailTest

class TailTest {

    @Test(expected = IllegalArgumentException::class)
    fun optionsExceptionForBoth() {
        TailLauncher().launch(arrayOf("-c", "5", "-n", "3"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun optionsExceptionForOne() {
        TailLauncher().launch(arrayOf("-c", "-5"))
        TailLauncher().launch(arrayOf("-n", "-5"))
    }

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    fun lastLines(){
        val reader = BufferedReader(InputStreamReader(FileInputStream(File("src\\test\\resources\\text.txt"))))
        val file = createTempFile("tmp.txt")
        val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file)))
        Tail(InputOption.LastLines,3).takeTail(reader,writer)
        writer.close()
        assertEquals(File("src\\test\\resources\\text.txt").readLines().takeLast(3),file.readLines())
        file.deleteOnExit()
    }


    @Test
    fun lastSymbols(){
        val reader = BufferedReader(InputStreamReader(FileInputStream(File("src\\test\\resources\\text.txt"))))
        val file = createTempFile("tmp.txt")
        val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file)))
        Tail(InputOption.LastSymbols,10).takeTail(reader,writer)
        writer.close()
        assertEquals(File("src\\test\\resources\\text.txt").readText().takeLast(10),file.readText().dropLast(2))
        file.deleteOnExit()
    }

    @Test
    fun severalFiles(){
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        TailLauncher().launch(arrayOf("-c","10","src\\test\\resources\\text.txt","src\\test\\resources\\text2.txt"))
        assertEquals("src\\test\\resources\\text.txt\r\n" +
                "the child.\r\n" +
                "\r\n" +
                "src\\test\\resources\\text2.txt\r\n" +
                "ight(Node)\r\n" +
                "\r\n",outContent.toString())
    }

    @Test
    fun readFromCmd(){
        System.setIn(ByteArrayInputStream("There are several lines of evidence for the persistence of HCBD in the environment.".toByteArray()))
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        TailLauncher().launch(arrayOf("-c","10"))
        assertEquals("vironment.",outContent.toString().dropLast(2))
    }
}