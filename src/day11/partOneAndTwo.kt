package day11

import readFileToString

enum class TypesSeats {
    EMPTY,
    OCCUPIED,
    FLOOR
}

class FerryPos(val x: Int, val y: Int){
    val addTwoPos = { (a, b): Pair<Int, Int> ->
        FerryPos(x + a, y + b)
    }
}

class Seat(private val boatPos: FerryPos, charOnMap: Char) {
    var kindOfSeat = TypesSeats.EMPTY
    init {
        when(charOnMap){
            'L' -> kindOfSeat = TypesSeats.EMPTY
            '#' -> kindOfSeat = TypesSeats.OCCUPIED
            '.' -> kindOfSeat = TypesSeats.FLOOR
        }
    }
    val adjacentSeatsPOne = {
        val closeSeats = mutableMapOf<TypesSeats, Int>()
        val adjacentPosition = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0), Pair(1, 1), Pair(1, -1), Pair(-1, -1), Pair(-1, 1))
        for (adj in adjacentPosition){
            val newSeat = boatPos.addTwoPos(adj)
            if (newSeat.x in input.boat.indices && newSeat.y in input.boat.indices) {
                val valueOfSeat = input.boat[newSeat.x][newSeat.y].kindOfSeat
                closeSeats.putIfAbsent(valueOfSeat, 0)
                closeSeats[valueOfSeat] = closeSeats[valueOfSeat]!!.plus(1)
            }
        }
        closeSeats
    }

    val adjacentSeatsPTwo = {
        val closeSeats = mutableMapOf<TypesSeats, Int>()
        val adjacentPosition = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0), Pair(1, 1), Pair(1, -1), Pair(-1, -1), Pair(-1, 1))
        for (adj in adjacentPosition){
            var newSeat = boatPos.addTwoPos(adj)
            thisone@while (true){
                if (newSeat.x in input.boat.indices && newSeat.y in input.boat.indices) {
                    val valueOfSeat = input.boat[newSeat.x][newSeat.y].kindOfSeat
                    closeSeats.putIfAbsent(valueOfSeat, 0)
                    closeSeats[valueOfSeat] = closeSeats[valueOfSeat]!!.plus(1)
                    if (valueOfSeat != TypesSeats.FLOOR) break@thisone
                } else break@thisone
                newSeat = newSeat.addTwoPos(adj)
            }
        }
        closeSeats
    }
}

data class Input(var boat: List<List<Seat>>, var allOccupied: Int)
var input = Input(readFileToString("input/day11/input.txt").mapIndexed { fI, s -> s.toList().mapIndexed { sI, t -> Seat(FerryPos(fI, sI), t) } }, 0)

fun main(){
    pOne()
    pTwo()
}


fun pTwo(){
    while (true) {
        val newInput = mutableListOf<MutableList<Seat>>()
        var occupied = 0
        for ((i, s) in input.boat.withIndex()) {
            newInput.add(mutableListOf())
            for ((j, t) in s.withIndex()) {
                if (t.kindOfSeat != TypesSeats.FLOOR) {
                    val localSeats = t.adjacentSeatsPTwo()
                    if (TypesSeats.OCCUPIED !in localSeats.keys) {
                        newInput[newInput.size - 1].add(Seat(FerryPos(i, j), '#'))
                        occupied ++
                    } else if (localSeats[TypesSeats.OCCUPIED]!! >= 5) {
                        newInput[newInput.size - 1].add(Seat(FerryPos(i, j), 'L'))
                    } else {
                        newInput[newInput.size - 1].add(t)
                        if (t.kindOfSeat == TypesSeats.OCCUPIED) occupied ++
                    }
                } else {
                    newInput[newInput.size - 1].add(Seat(FerryPos(i, j), '.'))
                }
            }
        }
        if (occupied == input.allOccupied) {
            println(occupied)
            break
        }
        input = Input(newInput, occupied)
    }
}

fun pOne(){
    while (true) {
        val newInput = mutableListOf<MutableList<Seat>>()
        var occupied = 0
        for ((i, s) in input.boat.withIndex()) {
            newInput.add(mutableListOf())
            for ((j, t) in s.withIndex()) {
                if (t.kindOfSeat != TypesSeats.FLOOR) {
                    val localSeats = t.adjacentSeatsPOne()
                    if (TypesSeats.OCCUPIED !in localSeats.keys) {
                        newInput[newInput.size - 1].add(Seat(FerryPos(i, j), '#'))
                        occupied ++
                    } else if (localSeats[TypesSeats.OCCUPIED]!! >= 4) {
                        newInput[newInput.size - 1].add(Seat(FerryPos(i, j), 'L'))
                    } else {
                        newInput[newInput.size - 1].add(t)
                        if (t.kindOfSeat == TypesSeats.OCCUPIED) occupied ++
                    }
                } else {
                    newInput[newInput.size - 1].add(Seat(FerryPos(i, j), '.'))
                }
            }
        }
        if (occupied == input.allOccupied) {
            println(occupied)
            break
        }
        input = Input(newInput, occupied)
    }
}

fun debugBoat(input: List<List<Seat>>){
    for (i in input) {
        for (j in i) {
            when (j.kindOfSeat) {
                TypesSeats.EMPTY -> print('L')
                TypesSeats.OCCUPIED -> print('#')
                TypesSeats.FLOOR -> print('.')
            }
        }
        println()
    }
    println()
}