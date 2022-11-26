package day1

import readFileToInt

fun main(){
    val input = readFileToInt("input/day1/day1.txt")
    loop@for ((i, n1) in input.withIndex()){
        for ((j, n2) in input.subList(i, input.size).withIndex()){
            for (n3 in input.subList(j, input.size)) {
                if (n1 + n2 + n3 == 2020) {
                    print(n1 * n2 * n3)
                    break@loop
                }
            }
        }
    }
}
