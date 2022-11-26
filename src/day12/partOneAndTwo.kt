package day12

import readFileToString
import kotlin.math.abs
import kotlin.math.roundToInt

class Boat{
    var xPosition = 0
    var yPosition = 0
    val canDirection = listOf('N', 'E', 'S', 'W')
    var cDirection = 1
}

fun changeBoatPos(boat: Boat, inscpecting: Char, movingValue: Int){
    when(inscpecting) {
        'N' -> boat.yPosition += movingValue
        'E' -> boat.xPosition += movingValue
        'S' -> boat.yPosition -= movingValue
        'W' -> boat.xPosition -= movingValue
    }
}

val input = readFileToString("input/day12/input.txt")

fun main(){
    println("Part One --> ${pOne()} \nPart Two --> ${pTwo()}")
}

fun pTwo(): Int{
    val waypoint = Boat()
    waypoint.xPosition = 10
    waypoint.yPosition = 1
    val boat = Boat()

    for (i in input){
        val instruction = i[0]
        val movingValue = i.substring(1, i.length).toInt()

        when(instruction){
            in waypoint.canDirection -> changeBoatPos(waypoint, instruction, movingValue)

            'R' -> {
                for (o in 0 until movingValue / 90) {
                    waypoint.yPosition = -waypoint.xPosition.also {
                        waypoint.xPosition = waypoint.yPosition
                    }
                }
            }
            'L' -> {
                for (o in 0 until movingValue / 90) {
                    waypoint.xPosition = -waypoint.yPosition.also {
                        waypoint.yPosition = waypoint.xPosition
                    }
                }
            }

            'F' -> {
                boat.xPosition += waypoint.xPosition * movingValue
                boat.yPosition += waypoint.yPosition * movingValue
            }
        }
    }
    return abs(boat.xPosition) + abs(boat.yPosition)
}

fun pOne(): Int{
    val boat = Boat()

    for (i in input){
        val instruction = i[0]
        val movingValue = i.substring(1, i.length).toInt()

        when(instruction){
            in boat.canDirection -> changeBoatPos(boat, instruction, movingValue)

            'R' -> boat.cDirection += movingValue / 90
            'L' -> boat.cDirection -= movingValue / 90

            'F' -> changeBoatPos(boat, boat.canDirection[Math.floorMod(boat.cDirection, 4)], movingValue)
        }
    }
    return abs(boat.xPosition) + abs(boat.yPosition)
}
