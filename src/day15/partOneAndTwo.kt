package day15

import readFileToOneString

val input = readFileToOneString("input/day15/input.txt").split(',').map { it.toInt() }

fun main(){
    println("Part One --> ${pOAndT(2020L)} \nPart Two --> ${pOAndT(30000000L)}")
}

fun pOAndT(turns: Long): Int{
    val allSpoken = mutableMapOf<Int, Int>()
    input.dropLast(1).forEachIndexed { index, i -> allSpoken[i] = index }
    var lastSpoken = Pair(input.last(), input.size - 1)
    for (i in 0 until turns - input.size){
        if (lastSpoken.first !in allSpoken.keys){
            allSpoken[lastSpoken.first] = lastSpoken.second
            lastSpoken = Pair(0, lastSpoken.second.plus(1))
        } else {
            val newSpoken = lastSpoken.second.minus(allSpoken[lastSpoken.first]!!)
            allSpoken[lastSpoken.first] = lastSpoken.second
            lastSpoken = Pair(newSpoken, lastSpoken.second.plus(1))
        }
    }
    return lastSpoken.first
}