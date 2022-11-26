package day8

import readFileToOneString

val instrRegex = Regex("(\\w{3})\\s([-|+]\\d+)")

fun main(){
    val input = instrRegex.findAll(readFileToOneString("input/day8/input.txt")).toList().map { it.groupValues.drop(1) }
    bootCode(input.map { it.toMutableList() }.toMutableList())
}

// Mouais bof
fun bootCode(input: MutableList<MutableList<String>>){
    var codeIndex = 0
    var accumulator = 0
    val previousLines = mutableListOf<Int>()
    val nopsAndJmps = mutableListOf<Int>()
    while (codeIndex < input.size - 1) {
        codeIndex = 0
        accumulator = 0
        var tried = false
        previousLines.clear()
        while (codeIndex !in previousLines) {
            previousLines.add(codeIndex)
            //println("On est à $codeIndex = ${input[codeIndex]} et l'acc est à $accumulator")
            var curInstruction = input[codeIndex][0]
            val curArgument = input[codeIndex][1].toInt()

            if (curInstruction in listOf("nop", "jmp") && !tried && codeIndex !in nopsAndJmps){
                curInstruction = if (curInstruction == "jmp") "nop" else "jmp"
                tried = true
                nopsAndJmps.add(codeIndex)
            }

            when (curInstruction) {
                "acc" -> {
                    accumulator += curArgument; codeIndex += 1
                }
                "jmp" -> codeIndex += curArgument
                "nop" -> codeIndex += 1
            }
            if (codeIndex >= input.size){ break }
        }
    }
    println(accumulator)
}