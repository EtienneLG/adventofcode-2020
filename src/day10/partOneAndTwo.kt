package day10

import readFileToInt

val possiblesJolts = listOf(1, 2, 3)

fun main(){
    val input = readFileToInt("input/day10/input.txt").sorted().toMutableList()
    input.add(0, 0)
    input.add(input.max()!!.plus(3))

    println("Part One --> ${pOne(input)} \nPart Two --> ${pTwo(input)}")
}

fun pTwo(input: MutableList<Int>): Long{
    val cacheArrangments = mutableMapOf<MutableList<Int>, Long>()
    fun arrangments(current: Int, allOutlet: MutableList<Long>): Long {
        allOutlet.add(input[current].toLong())
        val canBe = mutableListOf<Int>()

        for (i in 1..3){
            if (current + i >= input.size) break
            if (input[current + i] - input[current] in possiblesJolts) canBe.add(current + i)
            else break
        }
        return if (canBe.size == 0) 1
        else if (input.subList(current, input.size) in cacheArrangments.keys) {
            cacheArrangments[input.subList(current, input.size)]!!
        }
        else {
            val ofAll = mutableListOf<Long>()
            for (j in canBe) {
                ofAll.add(arrangments(j, allOutlet.toMutableList()))
            }
            cacheArrangments[input.subList(current, input.size)] = ofAll.sum()
            ofAll.sum() 
        }
    }
    return arrangments(0, mutableListOf())
}

fun pOne(input: MutableList<Int>): Int{
    var oneJolt = 0
    var threeJolt = 0
    for (i in 1 until input.size){
        when(input[i] - input[i - 1]){
            1 -> oneJolt++
            3 -> threeJolt++
        }
    }
    return oneJolt * threeJolt
}