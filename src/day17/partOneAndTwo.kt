package day17

import readFileToString

enum class CubeStates{
    ACTIVE,
    INACTIVE
}

class ConwayCube(var x: Int, var y: Int, var z: Int, var state: CubeStates){
    var w = 0
}

val input = readFileToString("input/day17/input.txt").map { it.toList() }

fun main(){
    println("Part One --> ${pOne()} \nPart Two --> ${pTwo()}")
}

fun pTwo(): Int{
    var cubes = parseCubes()
    for (i in 0 until 6) {
        val newCubes = mutableListOf<ConwayCube>()

        val xSorted = cubes.sortedBy { it.x }
        val ySorted = cubes.sortedBy { it.y }
        val zSorted = cubes.sortedBy { it.z }
        val wSorted = cubes.sortedBy { it.w }

        val minX = xSorted.first().x
        val minY = ySorted.first().y
        val minZ = zSorted.first().z
        val minW = wSorted.first().w

        val maxX = xSorted.last().x
        val maxY = ySorted.last().y
        val maxZ = zSorted.last().z
        val maxW = wSorted.last().w

        for (x in minX - 1..maxX + 1) {
            for (y in minY - 1..maxY + 1) {
                for (z in minZ - 1..0) {
                    for (w in minW - 1..0) {
                        val adjacent = mutableListOf<CubeStates>()

                        for (a in -1..1) {
                            for (b in -1..1) {
                                for (c in -1..1) {
                                    for (d in -1..1) {
                                        if (a == 0 && b == 0 && c == 0 && d == 0) continue
                                        adjacent.add(findStateFour(x + a, y + b, z + c, w + d, cubes))
                                    }
                                }
                            }
                        }

                        val c = cubes.find { it.x == x && it.y == y && it.z == z && it.w == w}
                        val adj = adjacent.count { it == CubeStates.ACTIVE }
                        if (c != null && c.state == CubeStates.ACTIVE) {
                            if (adj in 2..3) newCubes.add(c)
                            else newCubes.add(ConwayCube(x, y, z, CubeStates.INACTIVE).apply { this.w = w })
                        } else if (c == null || c.state == CubeStates.INACTIVE) {
                            if (adj == 3) newCubes.add(ConwayCube(x, y, z, CubeStates.ACTIVE).apply { this.w = w })
                            else newCubes.add(ConwayCube(x, y, z, CubeStates.INACTIVE).apply { this.w = w })
                        }
                    }
                }
            }
        }

        val secondHalf = newCubes.filter { it.z != 0 }.map { ConwayCube(it.x, it.y, -it.z, it.state).apply { this.w = it.w } }
        newCubes.addAll(secondHalf)
        newCubes.addAll(newCubes.filter { it.w < 0 }.map { ConwayCube(it.x, it.y, it.z, it.state).apply { this.w = -it.w } })

        cubes = newCubes.toMutableList()
    }
    return cubes.count { it.state == CubeStates.ACTIVE }
}

fun pOne(): Int{
    var cubes = parseCubes()
    for (i in 0 until 6) {
        val newCubes = mutableListOf<ConwayCube>()

        val xSorted = cubes.sortedBy { it.x }
        val ySorted = cubes.sortedBy { it.y }
        val zSorted = cubes.sortedBy { it.z }

        val minX = xSorted.first().x
        val minY = ySorted.first().y
        val minZ = zSorted.first().z

        val maxX = xSorted.last().x
        val maxY = ySorted.last().y
        val maxZ = zSorted.last().z

        for (x in minX - 1..maxX + 1) {
            for (y in minY - 1..maxY + 1) {
                for (z in minZ - 1..0) {
                    val adjacent = mutableListOf<CubeStates>()

                    for (a in -1..1){
                        for (b in -1..1){
                            for (c in -1..1){
                                if (a == 0 && b == 0 && c == 0) continue
                                adjacent.add(findState(x + a, y + b, z + c, cubes))
                            }
                        }
                    }

                    val c = cubes.find { it.x == x && it.y == y && it.z == z }
                    val adj = adjacent.count { it == CubeStates.ACTIVE }
                    if (c != null && c.state == CubeStates.ACTIVE) {
                        if (adj in 2..3) newCubes.add(c)
                        else newCubes.add(ConwayCube(x, y, z, CubeStates.INACTIVE))
                    } else if (c == null || c.state == CubeStates.INACTIVE) {
                        if (adj == 3) newCubes.add(ConwayCube(x, y, z, CubeStates.ACTIVE))
                        else newCubes.add(ConwayCube(x, y, z, CubeStates.INACTIVE))
                    }
                }
            }
        }
        val secondHalf = newCubes.filter { it.z != 0 }.map { ConwayCube(it.x, it.y, -it.z, it.state) }
        newCubes.addAll(secondHalf)
        cubes = newCubes.toMutableList()
    }

    return cubes.count { it.state == CubeStates.ACTIVE }
}

fun findStateFour(x: Int, y: Int, z: Int, w: Int, cubes: MutableList<ConwayCube>): CubeStates{
    val possible = cubes.find { it.x == x && it.y == y && it.z == z && it.w == w}
    if (possible != null) {
        return possible.state
    }
    return CubeStates.INACTIVE
}

fun findState(x: Int, y: Int, z: Int, cubes: MutableList<ConwayCube>): CubeStates{
    val possible = cubes.find { it.x == x && it.y == y && it.z == z}
    if (possible != null) {
        return possible.state
    }
    return CubeStates.INACTIVE
}

fun parseCubes(): MutableList<ConwayCube> {
    val cubes = mutableListOf<ConwayCube>()
    for (x in input.indices){
        for (y in input[0].indices){
            cubes.add(ConwayCube(y, x, 0, when (input[x][y]){
                '#' -> CubeStates.ACTIVE
                '.' -> CubeStates.INACTIVE
                else -> error("State isn't known")
            }))
        }
    }
    return cubes
}