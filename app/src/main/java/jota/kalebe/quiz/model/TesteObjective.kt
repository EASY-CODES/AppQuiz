package jota.kalebe.quiz.model

import java.util.*

class TesteObjective {
//    Dado um array de números inteiros, escreva uma aplicação que retorne a média dos 3 maiores números menores
//    que 30 e dos 3 menores números maiores que 50 presentes no array.

    var list = listOf(5, 8, 51, 30, 25, 45, 10, 18, 14, 7, 54, 90, 55, 33, 45, 66, 58)

    fun teste_1(): Double {
        Collections.sort(list)

        val l1 = list.filter {v -> v < 30}.takeLast(3)
        return (l1.sum()/3.0)
    }

    fun teste_2(): Double {
        Collections.sort(list)

        val l1 = list.filter {v -> v > 50}.subList(0,3)
        return (l1.sum()/3.0)
    }
}