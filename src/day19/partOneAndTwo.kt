package  day19

import readFileToOneString

val input = readFileToOneString("input/day19/input.txt").split("\r\n\r\n")
val rules = input[0].split("\r\n")
val messages = input[1].split("\r\n")
var combinations = mutableMapOf("0" to "")
val comb = mutableMapOf(mutableListOf("0") to "")
var goodOnes = mutableListOf<String>()

fun main(){
    checkRuleTwo(0, mutableListOf("0"))
    println("Check done")
    goodOnes.addAll(messages.filter { verifyMessage(it) })
    println(goodOnes)
}

var verifyMessage = { m: String -> m in comb.values }

fun checkRule(r: Int, index: String){
    var gone = false
    for (i in rules){
        if (Character.getNumericValue(i[0]) == r){
            if (i.last() == '"') {
                val letter = i.substringAfter(' ').replace("\"", "")
                combinations[index] += letter
            }
            else {
                val params = i.substringAfter(' ').split(" | ")
                var nInd = index
                val nope = combinations[index]!!
                for (p in params[0].split(' ')){
                    for (k in combinations.keys.filter { it.startsWith(nInd) }){
                        combinations[k + p] = combinations[k].toString()
                        checkRule(p.toInt(), k + p)
                        combinations.remove(k)
                    }
                    nInd += p
                }
                if (params.size != 1){
                    combinations[index] = nope
                    val cant = nInd.substring(0, nInd.length - 1)
                    for (p in params[1].split(' ')){
                        for (k in combinations.keys.filter { it.startsWith(index) && !it.startsWith(cant) }){
                            combinations[k + p] = combinations[k].toString()
                            checkRule(p.toInt(), k + p)
                            combinations.remove(k)
                        }
                        nInd += p
                    }
                }
            }
            gone = true
            break
        }
    }
    if (!gone) println("nobody")
}

fun checkRuleTwo(r: Int, index: MutableList<String>){
    var gone = false
    for (i in rules){
        if (i.split(':')[0].toInt() == r){
            if (i.last() == '"') {
                val letter = i.substringAfter(' ').replace("\"", "")
                comb[index] += letter
            }
            else {
                val params = i.substringAfter(' ').split(" | ")
                val nInd = index.toMutableList()
                val nope = comb[index]!!
                for (p in params[0].split(' ')){
                    for (k in comb.keys.filter { listStartWith(it, nInd) }){
                        comb[(k + mutableListOf(p)).toMutableList()] = comb[k].toString()
                        checkRuleTwo(p.toInt(), (k + mutableListOf(p)).toMutableList())
                        comb.remove(k)
                    }
                    nInd.add(p)
                }
                if (params.size != 1){
                    comb[index] = nope
                    val cant = nInd.toMutableList()
                    cant.removeLast()
                    for (p in params[1].split(' ')){
                        for (k in comb.keys.filter { listStartWith(it, index) && !listStartWith(it, cant) }){
                            comb[(k + mutableListOf(p)).toMutableList()] = comb[k].toString()
                            checkRuleTwo(p.toInt(), (k + mutableListOf(p)).toMutableList())
                            comb.remove(k)
                        }
                        nInd.add(p)
                    }
                }
            }
            gone = true
            break
        }
    }
    if (!gone) println("nobody")
}

fun listStartWith(c: MutableList<String>, l: MutableList<String>): Boolean{
    return if (c.size < l.size) false
    else c.subList(0, l.size) == l
}