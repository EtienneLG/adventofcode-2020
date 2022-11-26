import java.io.File

fun readFileToInt(fileName: String): List<Int>
        = File(fileName).readLines().map { it.toInt() }

fun readFileToLong(fileName: String): List<Long>
        = File(fileName).readLines().map { it.toLong() }

fun readFileToString(fileName: String): List<String>
        = File(fileName).readLines()

fun readFileToOneString(fileName: String): String
        = File(fileName).readText()