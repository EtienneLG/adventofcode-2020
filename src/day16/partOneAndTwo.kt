package day16

import readFileToOneString

val MATCHREGEX = Regex("\\w*:\\s(\\d*-\\d*)\\sor\\s(\\d*-\\d*)")

val input = readFileToOneString("input/day16/input.txt").split("\r\n\r\n")
val ticketFields = input[0].split("\r\n").map { MATCHREGEX.find(it)!!.destructured.toList().map { s -> s.split('-').map { t -> t.toInt() } } }
val myTicket = input[1].drop(14).replace("\r\n", ",").split(',').map { it.toInt() }
val nearbyTickets = input[2].drop(17).split("\r\n").map { it.split(',').map { s -> s.toInt() } }.toMutableList()

fun main(){
    println(pOne())
    println(pTwo())
}

fun pTwo(): Long{
    val possibleOrder = ticketFields.mapIndexed { i, _ -> i to ticketFields.mapIndexed { j, _ -> j }.toMutableList() }.toMap()
    for (j in nearbyTickets){
        for ((find, i) in j.withIndex()){
            for ((tind, valid) in ticketFields.withIndex()) {
                if (i in valid[0][0]..valid[0][1] || i in valid[1][0]..valid[1][1]) continue
                else possibleOrder[tind]!!.remove(find)
            }
        }
    }
    while (!possibleOrder.all { it.value.size == 1 }){
        for ((k, v) in possibleOrder){
            if (v.size == 1) possibleOrder.map { if (it.value != v) it.value.remove(v[0]) }
        }
    }
    val departure = possibleOrder.values.take(6).fold(1L) { acc, s -> acc * myTicket[s[0]] }
    return departure
}

fun pOne(): Int{
    val unvalid = mutableListOf<Int>()
    val removeable = mutableListOf<List<Int>>()
    for (j in nearbyTickets){
        mainloop@for (i in j) {
            for (valid in ticketFields) {
                if (i in valid[0][0]..valid[0][1] || i in valid[1][0]..valid[1][1]) continue@mainloop
            }
            unvalid.add(i)
            removeable.add(j)
        }
    }
    nearbyTickets -= removeable
    return unvalid.sum()
}