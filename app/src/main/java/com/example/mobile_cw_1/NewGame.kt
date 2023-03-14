package com.example.mobile_cw_1

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat


class NewGame : AppCompatActivity() {
    lateinit var hDice1 : ImageButton
    lateinit var hDice2 : ImageButton
    lateinit var hDice3 : ImageButton
    lateinit var hDice4 : ImageButton
    lateinit var hDice5 : ImageButton
    lateinit var humanRandomNoArr : IntArray
    lateinit var reRollArr : IntArray
    lateinit var Throw : Button
    lateinit var score : Button
    lateinit var reRoll : Button
    lateinit var scoreBoard : TextView
    var reRollsCount : Int = 2

    var humanMainTotal : Int = 0
    var reRollPressed : Boolean = false

    lateinit var cDice1 : ImageButton
    lateinit var cDice2 : ImageButton
    lateinit var cDice3 : ImageButton
    lateinit var cDice4 : ImageButton
    lateinit var cDice5 : ImageButton
    lateinit var compRandomNoArr : IntArray
    var compMainTotal :Int = 0

    var tie : Boolean = false

    var targetValue : Int = 0

    lateinit var totalWin : TextView

    lateinit var mediaPlayer : MediaPlayer

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        mediaPlayer = MediaPlayer.create(this, R.raw.game_music2)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

//        initializing human dice images
        hDice1 = findViewById(R.id.humanImageButton1)
        hDice2 = findViewById(R.id.humanImageButton2)
        hDice3 = findViewById(R.id.humanImageButton3)
        hDice4 = findViewById(R.id.humanImageButton4)
        hDice5 = findViewById(R.id.humanImageButton5)
//        initializing throw button
        Throw = findViewById(R.id.Throw)
//        initializing score button
        score = findViewById(R.id.score)
        score.isEnabled = false
//        initializing reRoll button
        reRoll = findViewById(R.id.reRolls)
        reRoll.isEnabled = false
//        initialiZing score board
        scoreBoard = findViewById(R.id.scoreBoard)
//        making the human images not clickable
        toggleFunctionalityOfHumanImage(false)
//        Array to store the human generated random no
        humanRandomNoArr = IntArray(5)
//        initializing computer dice images
        cDice1 = findViewById(R.id.compImageButton1)
        cDice2 = findViewById(R.id.compImageButton2)
        cDice3 = findViewById(R.id.compImageButton3)
        cDice4 = findViewById(R.id.compImageButton4)
        cDice5 = findViewById(R.id.compImageButton5)
//        Array to store the computer generated random no
        compRandomNoArr = IntArray(5)

//        getting the target value from the bundle object
        val bundle = intent.extras
        targetValue = bundle?.getInt("intValue",0)!!
//        initializing total win variable
        totalWin = findViewById(R.id.totalWinBoard)
//        initializing score variables
        var humanTotalWins = MyArraySingleton.totalScoreArr[0]
        var compTotalWins = MyArraySingleton.totalScoreArr[1]

        totalWin.setText("H:$humanTotalWins        /        C:$compTotalWins")
    }

    private fun toggleFunctionalityOfHumanImage(bool : Boolean){
        hDice1.isEnabled = bool
        hDice2.isEnabled = bool
        hDice3.isEnabled = bool
        hDice4.isEnabled = bool
        hDice5.isEnabled = bool
    }

    private fun rollHumanDice() {
        humanRandomNoArr[0] = getRandomHumanDiceNumber()
        hDice1.setImageResource(getImage(humanRandomNoArr[0]))
        humanRandomNoArr[1] = getRandomHumanDiceNumber()
        hDice2.setImageResource(getImage(humanRandomNoArr[1]))
        humanRandomNoArr[2] = getRandomHumanDiceNumber()
        hDice3.setImageResource(getImage(humanRandomNoArr[2]))
        humanRandomNoArr[3] = getRandomHumanDiceNumber()
        hDice4.setImageResource(getImage(humanRandomNoArr[3]))
        humanRandomNoArr[4] = getRandomHumanDiceNumber()
        hDice5.setImageResource(getImage(humanRandomNoArr[4]))
    }

    private fun rollCompDice() {
        compRandomNoArr[0] = getRandomCompDiceNumber()
        cDice1.setImageResource(getImage(compRandomNoArr[0]))
        compRandomNoArr[1] = getRandomCompDiceNumber()
        cDice2.setImageResource(getImage(compRandomNoArr[1]))
        compRandomNoArr[2] = getRandomCompDiceNumber()
        cDice3.setImageResource(getImage(compRandomNoArr[2]))
        compRandomNoArr[3] = getRandomCompDiceNumber()
        cDice4.setImageResource(getImage(compRandomNoArr[3]))
        compRandomNoArr[4] = getRandomCompDiceNumber()
        cDice5.setImageResource(getImage(compRandomNoArr[4]))
    }
    private fun getRandomHumanDiceNumber() : Int {
//        var randomInt: Int
//        do {
//            randomInt = (1..6).random()
//        } while (humanRandomNoArr.count { it == randomInt } >= 2)
        var randomInt = (1..6).random()
        return randomInt
    }

    private fun getRandomCompDiceNumber() : Int {
//        var randomInt: Int
//        do {
//            randomInt = (1..6).random()
//        } while (compRandomNoArr.count { it == randomInt } >= 2)
        var randomInt = (1..6).random()
        return randomInt
    }

    private fun getImage(randomInt:Int):Int{
        val drawableResource = when (randomInt) {
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }
        return drawableResource
    }

    private fun getImageWithSelection(randomInt:Int):Int{
        val drawableResource = when (randomInt) {
            1 -> R.drawable.die_face_1_selected
            2 -> R.drawable.die_face_2_selected
            3 -> R.drawable.die_face_3_selected
            4 -> R.drawable.die_face_4_selected
            5 -> R.drawable.die_face_5_selected
            else -> R.drawable.die_face_6_selected
        }
        return drawableResource
    }
    fun throwButtonClicked(view: View) {
        if (reRollPressed){

//            new computer strategy
            compReRollStrategy()
//            for the comp player strategy
//            compReRoll()
            reRoll.isEnabled = reRollsCount != 0
            for (i in reRollArr.indices) {
                if (reRollArr[i] == 0) {
                    if( i+1 == 1 ){
                        humanRandomNoArr[0] = getRandomHumanDiceNumber()
                        hDice1.setImageResource(getImage(humanRandomNoArr[0]))
                    }else if(i+1 == 2){
                        humanRandomNoArr[1] = getRandomHumanDiceNumber()
                        hDice2.setImageResource(getImage(humanRandomNoArr[1]))
                    }else if(i+1 == 3){
                        humanRandomNoArr[2] = getRandomHumanDiceNumber()
                        hDice3.setImageResource(getImage(humanRandomNoArr[2]))
                    }else if(i+1 == 4){
                        humanRandomNoArr[3] = getRandomHumanDiceNumber()
                        hDice4.setImageResource(getImage(humanRandomNoArr[3]))
                    }else if(i+1 == 5){
                        humanRandomNoArr[4] = getRandomHumanDiceNumber()
                        hDice5.setImageResource(getImage(humanRandomNoArr[4]))
                    }
                }
            }

            toggleFunctionalityOfHumanImage(false)

            val handler = HandlerCompat.createAsync(Looper.getMainLooper())
            handler.postDelayed({
                for (i in reRollArr.indices) {
                    if (reRollArr[i] == 1) {
                        if( i+1 == 1 ){
                            hDice1.setImageResource(getImage(humanRandomNoArr[0]))
                        }else if(i+1 == 2){
                            hDice2.setImageResource(getImage(humanRandomNoArr[1]))
                        }else if(i+1 == 3){
                            hDice3.setImageResource(getImage(humanRandomNoArr[2]))
                        }else if(i+1 == 4){
                            hDice4.setImageResource(getImage(humanRandomNoArr[3]))
                        }else if(i+1 == 5){
                            hDice5.setImageResource(getImage(humanRandomNoArr[4]))
                        }
                    }

                }
            }, 500)
            if(reRollsCount == 0){
                Throw.isEnabled = false
                val handler = HandlerCompat.createAsync(Looper.getMainLooper())
                handler.postDelayed({
                    score.performClick()
                }, 500)
            }else{
                Throw.isEnabled = false
                score.isEnabled = true
            }
            reRollPressed = false


        }else if(tie){
            rollHumanDice()

            rollCompDice()

            reRoll.isEnabled = false
            Throw.isEnabled = false
            score.isEnabled = true
        }else{
            rollHumanDice()

            rollCompDice()

            reRoll.isEnabled = true
            Throw.isEnabled = false
            score.isEnabled = true
        }
    }


    fun reRollsButtonClicked(view: View) {

        reRollArr = IntArray(5)

        toggleFunctionalityOfHumanImage(true)

        Throw.isEnabled = true
        reRollPressed = true
        reRoll.isEnabled = false
        score.isEnabled = false
        reRollsCount -= 1
        var reRollText : String = "$reRollsCount - REROLL"
        reRoll.setText(reRollText)

    }

    fun humanImg1Clicked(view: View) {
        hDice1.setImageResource(getImageWithSelection(humanRandomNoArr[0]))
        reRollArr[0] = 1
    }
    fun humanImg2Clicked(view: View) {
        hDice2.setImageResource(getImageWithSelection(humanRandomNoArr[1]))
        reRollArr[1] = 1
    }
    fun humanImg3Clicked(view: View) {
        hDice3.setImageResource(getImageWithSelection(humanRandomNoArr[2]))
        reRollArr[2] = 1
    }
    fun humanImg4Clicked(view: View) {
        hDice4.setImageResource(getImageWithSelection(humanRandomNoArr[3]))
        reRollArr[3] = 1
    }
    fun humanImg5Clicked(view: View) {
        hDice5.setImageResource(getImageWithSelection(humanRandomNoArr[4]))
        reRollArr[4] = 1
    }

    @SuppressLint("MissingInflatedId")
    fun scoreButtonClicked(view: View) {
        if(reRollsCount == 2){
//            new computer strategy
            compReRollStrategy()
            compReRollStrategy()
//            compReRoll()
//            compReRoll()
        }else if(reRollsCount == 1){
//            new computer strategy
            compReRollStrategy()
//            compReRoll()
        }

        reRollsCount = 2
        var reRollText : String = "$reRollsCount - REROLL"
        reRoll.setText(reRollText)

        humanMainTotal += humanRandomNoArr.sum()

        compMainTotal += compRandomNoArr.sum()

        if(humanMainTotal > compMainTotal){
            scoreBoard.setTextColor(Color.GREEN)
        }else if(humanMainTotal == compMainTotal){
            scoreBoard.setTextColor(Color.YELLOW)
        }else{
            scoreBoard.setTextColor(Color.RED)
        }

        scoreBoard.setText("$humanMainTotal        -        $compMainTotal")

        var gameOver = false

        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view1 = inflater.inflate(R.layout.final_result_layout, null)
        builder.setView(view1)
        val dialog = builder.create()
        var title = view1.findViewById<TextView>(R.id.finalTitle)
        var message = view1.findViewById<TextView>(R.id.finalMessage)
        var back = view1.findViewById<Button>(R.id.finalButton)


        if(humanMainTotal >= targetValue && compMainTotal >= targetValue){
            if(humanMainTotal > compMainTotal){
                title.setText("You Win!")
                title.setTextColor(Color.GREEN)
                message.setText("Congratulations, you have reached more than $targetValue points!")
                MyArraySingleton.increHumanValue()
                gameOver = true
            }else if (humanMainTotal == compMainTotal){
                tie = true
                Toast.makeText(this, "Match tied throw again", Toast.LENGTH_SHORT).show()
            }else{
                title.setText("You Lose!")
                title.setTextColor(Color.RED)
                message.setText("Better luck next time!!")
                MyArraySingleton.increCompValue()
                gameOver = true
            }
        }else if(humanMainTotal>= targetValue){
            title.setText("You Win!")
            title.setTextColor(Color.GREEN)
            message.setText("Congratulations, you have reached more than $targetValue points!")
            MyArraySingleton.increHumanValue()
            gameOver = true
        }else if(compMainTotal >= targetValue){
            title.setText("You Lose!")
            title.setTextColor(Color.RED)
            message.setText("Better luck next time!!")
            MyArraySingleton.increCompValue()
            gameOver = true
        }

        if (gameOver){
            dialog.show()
            back.setOnClickListener(){
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }
            dialog.setOnCancelListener {
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }

        }else{
            Throw.isEnabled = true
        }

        reRoll.isEnabled = false
        score.isEnabled = false

        val handler = HandlerCompat.createAsync(Looper.getMainLooper())
        handler.postDelayed({
            hDice1.setImageResource(R.drawable.throwing)
            hDice2.setImageResource(R.drawable.throwing)
            hDice3.setImageResource(R.drawable.throwing)
            hDice4.setImageResource(R.drawable.throwing)
            hDice5.setImageResource(R.drawable.throwing)

            cDice1.setImageResource(R.drawable.waiting2)
            cDice2.setImageResource(R.drawable.waiting2)
            cDice3.setImageResource(R.drawable.waiting2)
            cDice4.setImageResource(R.drawable.waiting2)
            cDice5.setImageResource(R.drawable.waiting2)

        }, 200)
    }
    private fun compDecisionOnReRoll():Boolean{
        var bool : Boolean
        var decision = (0..1).random()
        if(decision == 0){
            bool = false
        }else{
            bool = true
        }

        return bool
    }

    private fun compReRoll(){
        var bool = compDecisionOnReRoll()
        if(bool){
            var compReRollArr = IntArray(5)
            for (i in compReRollArr.indices){
                var decision = (0..1).random()
                compReRollArr[i] = decision
            }
            for ( i in compReRollArr.indices){
                if(compReRollArr[i] == 1){
                    if( i+1 == 1 ){
                        compRandomNoArr[0] = getRandomCompDiceNumber()
                        cDice1.setImageResource(getImage(compRandomNoArr[0]))
                    }else if(i+1 == 2){
                        compRandomNoArr[1] = getRandomCompDiceNumber()
                        cDice2.setImageResource(getImage(compRandomNoArr[1]))
                    }else if(i+1 == 3){
                        compRandomNoArr[2] = getRandomCompDiceNumber()
                        cDice3.setImageResource(getImage(compRandomNoArr[2]))
                    }else if(i+1 == 4){
                        compRandomNoArr[3] = getRandomCompDiceNumber()
                        cDice4.setImageResource(getImage(compRandomNoArr[3]))
                    }else if(i+1 == 5){
                        compRandomNoArr[4] = getRandomCompDiceNumber()
                        cDice5.setImageResource(getImage(compRandomNoArr[4]))
                    }
                }
            }
        }

    }

    private fun compReRollStrategy(){
        var compReRollArr = IntArray(5)
        for ( i in compRandomNoArr.indices){
            if(compRandomNoArr[i] < 4){
                compReRollArr[i] = 1
            }
        }
        for ( i in compReRollArr.indices){
            if(compReRollArr[i] == 1){
                if( i+1 == 1 ){
                    compRandomNoArr[0] = getRandomCompDiceNumber()
                    cDice1.setImageResource(getImage(compRandomNoArr[0]))
                }else if(i+1 == 2){
                    compRandomNoArr[1] = getRandomCompDiceNumber()
                    cDice2.setImageResource(getImage(compRandomNoArr[1]))
                }else if(i+1 == 3){
                    compRandomNoArr[2] = getRandomCompDiceNumber()
                    cDice3.setImageResource(getImage(compRandomNoArr[2]))
                }else if(i+1 == 4){
                    compRandomNoArr[3] = getRandomCompDiceNumber()
                    cDice4.setImageResource(getImage(compRandomNoArr[3]))
                }else if(i+1 == 5){
                    compRandomNoArr[4] = getRandomCompDiceNumber()
                    cDice5.setImageResource(getImage(compRandomNoArr[4]))
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}