package day6

import readFileToOneString

val input = readFileToOneString("input/day6/input.txt").split("\r\n\r\n")
val groupsAnswers = input.map { it.split("\r\n") }

fun main() {
    println("Part one --> ${getAnyoneAnswers()} \nPart two --> ${getEveryoneAnswers()}")
}

val getAnyoneAnswers = { input.fold(0) { a, b -> a + b.replace("\r\n", "").toSet().size } }
val getEveryoneAnswers = { groupsAnswers.sumBy { it.fold(it[0].toSet()) { acc, s -> acc intersect s.toSet() }.size } }
