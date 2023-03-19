package com.example.mobile_cw_1

object MyArraySingleton {
    var totalScoreArr: IntArray = IntArray(2)
    var gameRuleShown:Boolean = false
    fun toggleGameRule(){
        gameRuleShown = !gameRuleShown
    }
    fun increHumanValue() {
        totalScoreArr[0] += 1
    }

    fun increCompValue() {
        totalScoreArr[1] += 1
    }
}