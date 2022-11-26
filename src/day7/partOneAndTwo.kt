package day7

import readFileToString

val input = readFileToString("input/day7/input.txt")
val regexParentBags = Regex("(\\w+\\s\\w+)")
val regexChildBags = Regex("(\\d)\\s(\\w+\\s\\w+)")

fun main(){
    println("Part one --> ${pOne()} \nPart two --> ${pTwo("shiny gold")}")
}

fun pTwo(goal: String): Int{
    for (i in input){
        val parentBagage = regexParentBags.find(i)!!.value

        if (parentBagage == goal){
            val childBagage = regexChildBags.findAll(i).toList().map { it.groupValues.drop(1) }
            return if (childBagage.isNotEmpty()) {
                val final = mutableListOf<Int>()
                for (j in childBagage) {
                    final.add(j[0].toInt() + j[0].toInt() * pTwo(j[1]))
                }
                final.sum()
            } else {
                0
            }
        }
    }
    return -1
}

fun pOne(): Int{
    val needed = mutableListOf("shiny gold")
    val possiblesBags = mutableListOf<String>()
    while (needed.size != 0){
        val need = needed.removeFirst()
        for (i in input){
            val childBagage = regexChildBags.findAll(i).toList().map { it.groupValues[2] }
            val parentBagage = regexParentBags.find(i)!!.value

            if (need in childBagage && parentBagage !in possiblesBags){
                needed.add(parentBagage)
                possiblesBags.add(parentBagage)
            }
        }
    }
    return possiblesBags.size
}