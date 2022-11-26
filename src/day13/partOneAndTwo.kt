package day13

import readFileToOneString

val input = readFileToOneString("input/day13/input.txt").split("\r\n")

fun main(){
    println("Part One --> ${pOne()} \nPart Two --> ${pTwo()}")
}

fun pTwo(): Long{
    val busIDs = input[1].split(",")
    val congruences = mutableMapOf<Int, Int>()
    busIDs.forEachIndexed { index, s -> if (s != "x") congruences[s.toInt()] = (busIDs.size-1)-index }

    val bigest = congruences.keys.fold(1L) { acc, i -> acc * i }
    val allInts = mutableListOf<Long>()

    for ((mod, remainder) in congruences){
        var n = bigest / mod
        val ancientN = n
        while (n % mod != 1L){
            n += ancientN
        }
        allInts.add(remainder * n)
    }
    return ((allInts.sum() % bigest) - busIDs.size + 1)
}

fun pOne(): Int{
    val earliest = input[0].toInt()
    val busIDs = input[1].split(",").filter { it != "x" }.map { it.toInt() }

    var waiting = earliest
    loop@while (true){
        for (id in busIDs){
            if (waiting % id == 0) return (waiting - earliest) * id
        }
        waiting++
    }
}