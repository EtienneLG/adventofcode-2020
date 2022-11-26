package day9

import readFileToLong

fun main(){
    val input = readFileToLong("input/day9/input.txt")

    val preamble = 25
    for (num in preamble until input.size){
        val currentNum = input[num]
        val beforeNums = input.subList(num - 5, num)
        if (respectTwoNums(beforeNums.sorted(), currentNum)) continue
        else {
            println(currentNum)
            break
        }
    }
}

fun respectTwoNums(previous: List<Long>, goal: Long): Boolean{
    var left = 0
    var right = previous.size - 1
    while (right != left) {
        val sumedNum = previous[left] + previous[right]
        if (sumedNum > goal){
            right--
        } else if (sumedNum < goal) {
            left++
        } else {
            return true
        }
    }
    return false
}