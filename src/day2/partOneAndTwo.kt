package day2

import readFileToString

val regexPattern = Regex("""(\d{1,2})[-](\d{1,2})\s(.)[:]\s(.+)""")

fun main() {
    val input = readFileToString("input/day2/day2.txt")
    val resultPartOne = countIsValid(input, isValidPartOne)
    val resultPartTwo = countIsValid(input, isValidPartTwo)
    println(resultPartOne)
    println(resultPartTwo)
}

fun countIsValid(input: List<String>, validationFunction: (MatchResult) -> Boolean): Int {
    return input.map { regexPattern.find(it)!! }.filter { validationFunction(it) }.count()
}

val isValidPartOne = { result: MatchResult ->
    val (minimum, maximum, letter, password) = result.destructured
    val numberOfLetter = password.count { it == letter.single() }
    numberOfLetter in minimum.toInt()..maximum.toInt()
}

val isValidPartTwo = { result: MatchResult ->
    val (minimum, maximum, letter, password) = result.destructured
    val firstCondition = password[minimum.toInt() - 1] == letter.single()
    val secondCondition = password[maximum.toInt() - 1] == letter.single()
    (firstCondition && !secondCondition) || (secondCondition && !firstCondition)
}