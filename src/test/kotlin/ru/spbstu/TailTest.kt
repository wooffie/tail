package ru.spbstu

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

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



}

