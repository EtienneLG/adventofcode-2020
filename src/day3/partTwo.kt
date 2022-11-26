package day3

import readFileToString

fun main(){
    val input = readFileToString("input/day3/sample1.txt")
    val parsedInput = parseInput(input)
    var final = 1L
    final *= (findTreesInSlopes(parsedInput, 1, 1))
    final *= (findTreesInSlopes(parsedInput, 3, 1))
    final *= (findTreesInSlopes(parsedInput, 5, 1))
    final *= (findTreesInSlopes(parsedInput, 7, 1))
    final *= (findTreesInSlopes(parsedInput, 1, 2))
    println(final)
}

fun findTreesInSlopes(parsedInput: MutableList<MutableList<Char>>, right: Int, down: Int): Int{
    var startX = 0
    var startY = 0
    var trees = 0
    while (startY + down <= parsedInput.size - 1){
        startX = (startX + right) % (parsedInput[0].size)
        startY += down
        if (parsedInput[startY][startX] == '#'){
            trees += 1
        }
    }
    return trees
}

fun parseInput(input: List<String>): MutableList<MutableList<Char>>{
    val parsedInput = mutableListOf<MutableList<Char>>()
    for (i in input){
        parsedInput.add(mutableListOf())
        for (j in i){
            parsedInput[parsedInput.size - 1].add(j)
        }
    }
    return parsedInput
}