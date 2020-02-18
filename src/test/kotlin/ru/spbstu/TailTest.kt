package ru.spbstu

import org.junit.Assert.*
import org.junit.Test
import java.io.File


class TailTest{

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }


    @Test
    fun readFromFile(){
        var expected = Tail.OutputData()
        var result = Tail("", listOf("src\\test\\input\\text.txt"),"n",3).readFromFile()
        expected.add("", listOf("А бесы прыгали в веселии великом.", "" , "Я издали глядел — смущением томим."))
        assertEquals(expected,result)
        expected = Tail.OutputData()
        assertNotEquals(expected,result)
        result = Tail("", listOf("src\\test\\input\\text.txt","src\\test\\input\\text2.txt"),"n",2).readFromFile()
        expected.add("src\\test\\input\\text.txt", listOf("" , "Я издали глядел — смущением томим."))
        expected.add("src\\test\\input\\text2.txt", listOf("Он стоит, задумался глубоко," , "И тихонько плачет он в пустыне."))
        assertEquals(expected,result)
        expected = Tail.OutputData()
        result = Tail("", listOf("src\\test\\input\\text.txt","src\\test\\input\\text2.txt"),"c",10).readFromFile()
        expected.add("src\\test\\input\\text.txt", listOf("ием томим."))
        expected.add("src\\test\\input\\text2.txt", listOf("в пустыне."))
        assertEquals(expected,result)
    }

    @Test
    fun writeInFile(){
        Tail("temp.txt", listOf("src\\test\\input\\text.txt"),"n",3).start()
        assertFileContent("temp.txt","А бесы прыгали в веселии великом.\n" +
                "\n" +
                "Я издали глядел — смущением томим.")
        File("temp.txt").delete()

        Tail("temp.txt", listOf("src\\test\\input\\text.txt"),"n",999).start()
        assertFileContent("temp.txt",File("src\\test\\input\\text.txt").readLines().joinToString("\n"))
        File("temp.txt").delete()


        Tail("temp.txt", listOf("src\\test\\input\\text3.txt"),"c",23).start()
        assertFileContent("temp.txt","Коллекции и Concurrency")
        File("temp.txt").delete()

        Tail("temp.txt", listOf("src\\test\\input\\text3.txt"),"c",9999).start()
        assertFileContent("temp.txt","Типы в Java: примитивные / ссылочные, обёртки примитивных типов, массивы, null\n" +
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
                "Коллекции и Concurrency")
        File("temp.txt").delete()

    }
    }
