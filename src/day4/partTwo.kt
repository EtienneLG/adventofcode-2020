package day4

import readFileToOneString

val regexPattern = Regex("""(\w{3})[:]([^\s]+)""") //https://regex101.com/r/aoKsPR/2
val GOODFIELDS = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")
val EYESCOLORS = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
val input = readFileToOneString("input/day4/input.txt")

fun main(){
    println(findValidPassports(2))
}

fun findValidPassports(part: Int): Int{
    val passports = input.split("\r\n\r\n")
    var validPassportsPartOne = 0
    var validPassportsPartTwo = 0
    pasp@for (pas in passports) {
        val fields = regexPattern.findAll(pas).toList().map { it.destructured.toList() }
        val keysPassports = fields.map { it[0] } + "cid"
        if ((GOODFIELDS subtract keysPassports.toSet()).isNotEmpty()) {
            continue@pasp
        } else if (part == 1) {
            validPassportsPartOne ++
        }
        if (part == 2) {
            for (f in fields) {
                val currentPKey = f[0]
                val currentPValue = f[1]
                when (currentPKey) {
                    "byr" -> if (currentPValue.toInt() !in 1920..2002) { continue@pasp }
                    "iyr" -> if (currentPValue.toInt() !in 2010..2020) { continue@pasp }
                    "eyr" -> if (currentPValue.toInt() !in 2020..2030) { continue@pasp}
                    "hgt" -> {
                        if (currentPValue.last() == 'm') {
                            if (currentPValue.substringBefore("cm").toInt() !in 150..193) {
                                continue@pasp
                            }
                        } else if (currentPValue.last() == 'n') {
                            if (currentPValue.substringBefore("in").toInt() !in 59..76) {
                                continue@pasp
                            }
                        } else { continue@pasp }
                    }
                    "hcl" -> if (!currentPValue.matches("[#][0-9a-f]{6}\\b".toRegex())) { continue@pasp }
                    "ecl" -> if (currentPValue !in EYESCOLORS) { continue@pasp }
                    "pid" -> if (currentPValue.length != 9) { continue@pasp }
                    "cid" -> {}
                }
            }
            validPassportsPartTwo++
        }
    }
    return if (part == 1) validPassportsPartOne else validPassportsPartTwo
}