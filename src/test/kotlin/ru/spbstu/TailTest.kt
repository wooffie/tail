package ru.spbstu

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

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
    fun bigTextTest() {
        Tail("temp.txt", listOf("src\\test\\resources\\bigtext.txt"), "c", 90000).start()
        assertEquals(
            File("temp.txt").readText(), File("src\\test\\resources\\bigtext.txt").readText()
        )
        Tail("temp.txt", listOf("src\\test\\resources\\bigtext2.txt"), "n", 100500).start()

        assertFileContent(
            "temp.txt", File("src\\test\\resources\\bigtext2.txt").readText()
        )
        // не проходит , хотя пишет , что "Contents are identical"
        // в ручных тестах всё работает хорошо
        File("temp.txt").delete()

    }


    @Test
    fun readFromFile() {
        var expected = Tail.OutputData()
        var result =
            Tail("", listOf("src\\test\\resources\\text.txt"), "n", 3).readFromFile()
        expected.add("", listOf("А бесы прыгали в веселии великом.", "", "Я издали глядел — смущением томим."))
        assertEquals(expected, result)
        expected = Tail.OutputData()
        assertNotEquals(expected, result)
        result =
            Tail(
                "", listOf("src\\test\\resources\\text.txt", "src\\test\\resources\\text2.txt"),
                "n", 2
            ).readFromFile()
        expected.add("src\\test\\resources\\text.txt", listOf("", "Я издали глядел — смущением томим."))
        expected.add(
            "src\\test\\resources\\text2.txt",
            listOf("Он стоит, задумался глубоко,", "И тихонько плачет он в пустыне.")
        )
        assertEquals(expected, result)
        expected = Tail.OutputData()
        result = Tail(
            "", listOf("src\\test\\resources\\text.txt", "src\\test\\resources\\text2.txt"),
            "c", 10
        ).readFromFile()
        expected.add("src\\test\\resources\\text.txt", listOf("ием томим."))
        expected.add("src\\test\\resources\\text2.txt", listOf("в пустыне."))
        assertEquals(expected, result)
    }

    @Test
    fun writeInFile() {
        Tail("temp.txt", listOf("src\\test\\resources\\text.txt"), "n", 3).start()
        assertFileContent(
            "temp.txt", "А бесы прыгали в веселии великом.\n" +
                    "\n" +
                    "Я издали глядел — смущением томим."
        )
        File("temp.txt").delete()

        Tail("temp.txt", listOf("src\\test\\resources\\text.txt"), "n", 999).start()
        assertFileContent(
            "temp.txt",
            File("src\\test\\resources\\text.txt").readLines().joinToString("\n")
        )
        File("temp.txt").delete()

        Tail("temp.txt", listOf("src\\test\\resources\\text3.txt"), "c", 23).start()
        assertFileContent("temp.txt", "Коллекции и Concurrency")
        File("temp.txt").delete()

        Tail("temp.txt", listOf("src\\test\\resources\\text3.txt"), "c", 9999).start()
        assertFileContent(
            "temp.txt", "Типы в Java: примитивные / ссылочные, обёртки примитивных типов, массивы, null\n" +
                    "Класс Object: equals / hashCode / toString\n" +
                    "Видимость в языке Java: public / protected / private / package private\n" +
                    "Сравнение классов, абстрактных классов и интерфейсов\n" +
                    "Статические и не-статические члены класса\n" +
                    "Финальные и не-финальные члены класса\n" +
                    "SAM-интерфейсы и лямбды в Java\n" +
                    "Исключения: контролируемые и нет, Throwable / Exception / RuntimeException / Error\n" +
                    "Перечисления, поля и методы перечислений\n" +
                    "Итераторы и их применение\n" +
                    "Разновидности коллекций: Collection / List / Set / Deque\n" +
                    "Реализации коллекций: List / ArrayList / LinkedList\n" +
                    "Реализации коллекций: Set / HashSet / TreeSet / EnumSet\n" +
                    "Реализации коллекций: Map / HashMap / TreeMap / EnumMap\n" +
                    "Реализации коллекций: Queue / Deque / ArrayDeque / LinkedList\n" +
                    "Шаблонные типы, сырые типы, wildcard типы\n" +
                    "GUI: общая организация приложения\n" +
                    "GUI: model-view-controller\n" +
                    "GUI: механизмы и методы отрисовки\n" +
                    "GUI: обработка событий, механизм слушателей\n" +
                    "GUI: контейнеры и менеджеры размещения\n" +
                    "JVM: вычисление выражений\n" +
                    "JVM: виды вложенных классов\n" +
                    "JVM: ветвления\n" +
                    "JVM: вызовы методов\n" +
                    "JVM: массивы\n" +
                    "Runnable и Thread\n" +
                    "synchronized и volatile\n" +
                    "Object: wait / notify / notifyAll\n" +
                    "Коллекции и Concurrency"
        )
        File("temp.txt").delete()
    }


    private val outContent = ByteArrayOutputStream()
    @Test
    fun writeInCmd() {
        System.setOut(PrintStream(outContent))
        Tail(
            "", listOf("src\\test\\resources\\text4.txt", "src\\test\\resources\\text3.txt"),
            "c", 10
        ).start()
        assertEquals(
            "src\\test\\resources\\text4.txt\n" +
                    " студенты.\n" +
                    "src\\test\\resources\\text3.txt\n" +
                    "oncurrency", outContent.toString()
        )

        Tail(
            "", listOf("src\\test\\resources\\text4.txt", "src\\test\\resources\\text3.txt"),
            "n", 2
        ).start()
        assertEquals(
            "src\\test\\resources\\text4.txt\n" +
                    " студенты.\n" +
                    "src\\test\\resources\\text3.txt\n" +
                    "oncurrencysrc\\test\\resources\\text4.txt\n" +
                    "Информационный поиск\n" +
                    "Выполняются НИР как с отечественными, так и с зарубежными партнерами. В НИР принимают активное " +
                    "участие преподаватели, аспиранты и студенты.\n" +
                    "src\\test\\resources\\text3.txt\n" +
                    "Object: wait / notify / notifyAll\n" +
                    "Коллекции и Concurrency", outContent.toString()
        )
    }

}

