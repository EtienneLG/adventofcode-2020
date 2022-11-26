package day18

import readFileToString

val input = readFileToString("input/day18/input.txt")

fun main(){
    println("Part One --> $pOne \nPart Two --> $pTwo")
}

val pOne = input.fold(0L) { acc, s -> acc + oper(0, s.replace(" ", "")) }

val pTwo = input.fold(0L) { acc, s -> acc + recurs(s.replace(" ", "")) }

fun recurs(current: String): Long{
    var operations = current
    while (true) {
        var bigger = 0
        var p = 0
        for ((index, el) in operations.withIndex()) {
            val ancient = p
            when (el) {
                '(' -> p++
                ')' -> p--
            }
            if (p > ancient) bigger = index
        }

        val c = if (operations.find { it == '(' } != null) operations.substring(bigger + 1, nextIndex(bigger, operations)) else operations
        var newSeq = c
        while (true) {
            val index = newSeq.indexOfFirst { it == '+' }
            if (index == -1) break
            val (a, b) = bothTerms(index, newSeq)
            newSeq = newSeq.replaceFirst("$a+$b", "${a + b}")
        }
        if (operations.find { it == '(' } != null) operations = operations.replace("($c)", calculRemain(newSeq))
        else {
            val a = calculRemain(newSeq).toLong()
            return a
        }
    }
}

fun calculRemain(seq: String): String{
    return seq.split('*').map { it.toLong() }.fold(1L) { acc, s -> acc * s }.toString()
}

fun bothTerms(index: Int, seq: String): Pair<Long, Long>{
    var a = ""
    var b = ""
    for (i in 1..index){
        if (seq[index - i].isDigit()) a += seq[index - i]
        else break
    }
    for (i in index + 1 until seq.length){
        if (seq[i].isDigit()) b += seq[i]
        else break
    }
    return Pair(a.reversed().toLong(), b.toLong())
}

fun oper(i: Int, current: String): Long{
    var index = i

    var firstNumber: Long? = null
    var sign: Char? = null
    for (useless in 0..200) {
        if (index >= current.length) return firstNumber!!
        when (current[index]) {
            '(' -> {
                if (firstNumber != null) {
                    when (sign) {
                        '+' -> firstNumber += oper(index + 1, current)
                        '*' -> firstNumber *= oper(index + 1, current)
                        else -> error("Sign --> $sign $current")
                    }
                } else firstNumber = oper(index + 1, current)
                index = nextIndex(index, current) + 1
            }
            ')' -> return firstNumber!!
            '+' -> { sign = '+' ; index += 1 }
            '*' -> { sign = '*' ; index += 1 }
            else -> {
                if (firstNumber == null) firstNumber = Character.getNumericValue(current[index]).toLong()
                else {
                    when (sign) {
                        '+' -> firstNumber += Character.getNumericValue(current[index])
                        '*' -> firstNumber *= Character.getNumericValue(current[index])
                        else -> error("Sign --> $sign $current")
                    }
                }
                index += 1
            }
        }
    }
    error("Fin de la boucle --> $index")
}

fun nextIndex(marge: Int, c: String): Int{
    var p = 0
    for ((j, i) in c.drop(marge).withIndex()){
        if (i == '(') p += 1
        else if (i == ')') {
            p -= 1
            if (p == 0) return marge + j
        }
    }
    error("Couldn't find --> E2 $marge $p")
}
