package day5

import readFileToString

fun main() {
    val toUsableBinary = { before: String -> before.replace('F', '0').replace('B', '1').replace('R', '1').replace('L', '0') }
    val seats = readFileToString("input/day5/input.txt").map { toUsableBinary(it) }.map { it.substring(0, 7).toInt(2) * 8 + it.substring(7, 10).toInt(2) }.toList().sorted()
    seats.dropLast(1).forEachIndexed { i, s -> if (seats[i + 1] == s + 2) { println("Part one: ${seats.max()}\nPart two: ${s + 1}") } }
}
