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
    private lateinit var hDice1 : ImageButton
    private lateinit var hDice2 : ImageButton
    private lateinit var hDice3 : ImageButton
    private lateinit var hDice4 : ImageButton
    private lateinit var hDice5 : ImageButton
    private var hDice1Resource : Int = R.drawable.throwing
    private var hDice2Resource : Int = R.drawable.throwing
    private var hDice3Resource : Int = R.drawable.throwing
    private var hDice4Resource : Int = R.drawable.throwing
    private var hDice5Resource : Int = R.drawable.throwing
    private lateinit var humanRandomNoArr : IntArray
    private var reRollArr : IntArray  = IntArray(5)
    private lateinit var Throw : Button
    private lateinit var score : Button
    private lateinit var reRoll : Button
    private lateinit var scoreBoard : TextView
    private var reRollsCount : Int = 2

    private var humanMainTotal : Int = 0
    private var reRollPressed : Boolean = false

    private lateinit var cDice1 : ImageButton
    private lateinit var cDice2 : ImageButton
    private lateinit var cDice3 : ImageButton
    private lateinit var cDice4 : ImageButton
    private lateinit var cDice5 : ImageButton
    private var cDice1Resource : Int = R.drawable.waiting2
    private var cDice2Resource : Int = R.drawable.waiting2
    private var cDice3Resource : Int = R.drawable.waiting2
    private var cDice4Resource : Int = R.drawable.waiting2
    private var cDice5Resource : Int = R.drawable.waiting2
    private lateinit var compRandomNoArr : IntArray
    private var compMainTotal :Int = 0

    private var tie : Boolean = false

    private var targetValue : Int = 0

    private lateinit var totalWin : TextView

    private lateinit var mediaPlayer : MediaPlayer

    private var gameMode : String = ""

    private var humanTotalWins : Int = 0
    private var compTotalWins : Int = 0

    private var gameRuleShown : Boolean = false

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        supportActionBar?.hide()
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
        gameMode = bundle.getString("gameMode")!!
        Toast.makeText(this, gameMode, Toast.LENGTH_SHORT).show()

//        initializing total win variable
        totalWin = findViewById(R.id.totalWinBoard)
//        initializing score variables
        humanTotalWins = bundle.getInt("humanTotalWins")
        compTotalWins = bundle.getInt("compTotalWins")

//        checks if the game rules are shown initially
        gameRuleShown = bundle.getBoolean("gameRuleShown")

        totalWin.text = "H:$humanTotalWins        /        C:$compTotalWins"

        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view1 = inflater.inflate(R.layout.game_rule_layout, null)
        builder.setView(view1)
        val ruleDialog = builder.create()
        val gameRuleButton = view1.findViewById<Button>(R.id.gameRuleButton)
        gameRuleButton.setOnClickListener{
            ruleDialog.dismiss()
        }


        if (savedInstanceState != null) {
            humanRandomNoArr = savedInstanceState.getIntArray("Human_Random_No_Arr")!!
            compRandomNoArr = savedInstanceState.getIntArray("Computer_Random_No_Arr")!!
            hDice1.setImageResource(savedInstanceState.getInt("Human_Dice_1_Resource"))
            hDice1Resource = savedInstanceState.getInt("Human_Dice_1_Resource")
            hDice2.setImageResource(savedInstanceState.getInt("Human_Dice_2_Resource"))
            hDice2Resource = savedInstanceState.getInt("Human_Dice_2_Resource")
            hDice3.setImageResource(savedInstanceState.getInt("Human_Dice_3_Resource"))
            hDice3Resource = savedInstanceState.getInt("Human_Dice_3_Resource")
            hDice4.setImageResource(savedInstanceState.getInt("Human_Dice_4_Resource"))
            hDice4Resource = savedInstanceState.getInt("Human_Dice_4_Resource")
            hDice5.setImageResource(savedInstanceState.getInt("Human_Dice_5_Resource"))
            hDice5Resource = savedInstanceState.getInt("Human_Dice_5_Resource")

            toggleFunctionalityOfHumanImage(savedInstanceState.getBoolean("Toggle_Functionality_Of_Human_Image"))

            cDice1.setImageResource(savedInstanceState.getInt("Computer_Dice_1_Resource"))
            cDice1Resource = savedInstanceState.getInt("Computer_Dice_1_Resource")
            cDice2.setImageResource(savedInstanceState.getInt("Computer_Dice_2_Resource"))
            cDice2Resource = savedInstanceState.getInt("Computer_Dice_2_Resource")
            cDice3.setImageResource(savedInstanceState.getInt("Computer_Dice_3_Resource"))
            cDice3Resource = savedInstanceState.getInt("Computer_Dice_3_Resource")
            cDice4.setImageResource(savedInstanceState.getInt("Computer_Dice_4_Resource"))
            cDice4Resource = savedInstanceState.getInt("Computer_Dice_4_Resource")
            cDice5.setImageResource(savedInstanceState.getInt("Computer_Dice_5_Resource"))
            cDice5Resource = savedInstanceState.getInt("Computer_Dice_5_Resource")

            Throw.isEnabled = savedInstanceState.getBoolean("Throw_Is_Enabled")
            reRollsCount = savedInstanceState.getInt("ReRolls_Count")
            reRollArr = savedInstanceState.getIntArray("ReRoll_Array")!!
            reRoll.isEnabled =savedInstanceState.getBoolean("ReRoll_Is_Enabled")
            reRoll.text = savedInstanceState.getString("ReRoll_Text")
            reRollPressed = savedInstanceState.getBoolean("ReRoll_Pressed")
            score.isEnabled = savedInstanceState.getBoolean("Score_Is_Enabled")
            humanMainTotal = savedInstanceState.getInt("Human_Main_Total")
            compMainTotal = savedInstanceState.getInt("Computer_Main_Total")
            scoreBoard.text = savedInstanceState.getString("Score_Board")
            scoreBoard.setTextColor(savedInstanceState.getInt("Score_Board_Color"))

            gameRuleShown = savedInstanceState.getBoolean("Game_Rule_Shown")

        }
        if(!gameRuleShown){
            ruleDialog.show()
            gameRuleShown = !gameRuleShown
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("Human_Random_No_Arr",humanRandomNoArr)
        outState.putIntArray("Computer_Random_No_Arr",compRandomNoArr)
        outState.putInt("Human_Dice_1_Resource",hDice1Resource)
        outState.putInt("Human_Dice_2_Resource",hDice2Resource)
        outState.putInt("Human_Dice_3_Resource",hDice3Resource)
        outState.putInt("Human_Dice_4_Resource",hDice4Resource)
        outState.putInt("Human_Dice_5_Resource",hDice5Resource)

        if(hDice1.isEnabled){
            outState.putBoolean("Toggle_Functionality_Of_Human_Image",true)
        }else{
            outState.putBoolean("Toggle_Functionality_Of_Human_Image",false)
        }

        outState.putInt("Computer_Dice_1_Resource",cDice1Resource)
        outState.putInt("Computer_Dice_2_Resource",cDice2Resource)
        outState.putInt("Computer_Dice_3_Resource",cDice3Resource)
        outState.putInt("Computer_Dice_4_Resource",cDice4Resource)
        outState.putInt("Computer_Dice_5_Resource",cDice5Resource)

        outState.putBoolean("Throw_Is_Enabled", Throw.isEnabled)
        outState.putInt("ReRolls_Count",reRollsCount)
        outState.putIntArray("ReRoll_Array",reRollArr)
        outState.putBoolean("ReRoll_Is_Enabled", reRoll.isEnabled)
        outState.putString("ReRoll_Text", reRoll.text.toString())
        outState.putBoolean("ReRoll_Pressed",reRollPressed)
        outState.putBoolean("Score_Is_Enabled", score.isEnabled)
        outState.putInt("Human_Main_Total",humanMainTotal)
        outState.putInt("Computer_Main_Total",compMainTotal)
        outState.putString("Score_Board",scoreBoard.text.toString())
        outState.putInt("Score_Board_Color",scoreBoard.currentTextColor)

        outState.putBoolean("Game_Rule_Shown",gameRuleShown)

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
        hDice1Resource = getImage(humanRandomNoArr[0])

        humanRandomNoArr[1] = getRandomHumanDiceNumber()
        hDice2.setImageResource(getImage(humanRandomNoArr[1]))
        hDice2Resource = getImage(humanRandomNoArr[1])

        humanRandomNoArr[2] = getRandomHumanDiceNumber()
        hDice3.setImageResource(getImage(humanRandomNoArr[2]))
        hDice3Resource = getImage(humanRandomNoArr[2])

        humanRandomNoArr[3] = getRandomHumanDiceNumber()
        hDice4.setImageResource(getImage(humanRandomNoArr[3]))
        hDice4Resource = getImage(humanRandomNoArr[3])

        humanRandomNoArr[4] = getRandomHumanDiceNumber()
        hDice5.setImageResource(getImage(humanRandomNoArr[4]))
        hDice5Resource = getImage(humanRandomNoArr[4])
    }

    private fun rollCompDice() {
        compRandomNoArr[0] = getRandomCompDiceNumber()
        cDice1.setImageResource(getImage(compRandomNoArr[0]))
        cDice1Resource = getImage(compRandomNoArr[0])

        compRandomNoArr[1] = getRandomCompDiceNumber()
        cDice2.setImageResource(getImage(compRandomNoArr[1]))
        cDice2Resource = getImage(compRandomNoArr[1])

        compRandomNoArr[2] = getRandomCompDiceNumber()
        cDice3.setImageResource(getImage(compRandomNoArr[2]))
        cDice3Resource = getImage(compRandomNoArr[2])

        compRandomNoArr[3] = getRandomCompDiceNumber()
        cDice4.setImageResource(getImage(compRandomNoArr[3]))
        cDice4Resource = getImage(compRandomNoArr[3])

        compRandomNoArr[4] = getRandomCompDiceNumber()
        cDice5.setImageResource(getImage(compRandomNoArr[4]))
        cDice5Resource = getImage(compRandomNoArr[4])
    }
    private fun getRandomHumanDiceNumber(): Int {
        return (1..6).random()
    }

    private fun getRandomCompDiceNumber(): Int {
        return (1..6).random()
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
            if(gameMode == "hard"){
//            new computer strategy
                compReRollStrategy()
            }else{
//            for the comp player strategy(Random)
                compReRoll()
            }
            reRoll.isEnabled = reRollsCount != 0
            for (i in reRollArr.indices) {
                if (reRollArr[i] == 0) {
                    if( i+1 == 1 ){
                        humanRandomNoArr[0] = getRandomHumanDiceNumber()
                        hDice1.setImageResource(getImage(humanRandomNoArr[0]))
                        hDice1Resource = getImage(humanRandomNoArr[0])
                    }else if(i+1 == 2){
                        humanRandomNoArr[1] = getRandomHumanDiceNumber()
                        hDice2.setImageResource(getImage(humanRandomNoArr[1]))
                        hDice2Resource = getImage(humanRandomNoArr[1])
                    }else if(i+1 == 3){
                        humanRandomNoArr[2] = getRandomHumanDiceNumber()
                        hDice3.setImageResource(getImage(humanRandomNoArr[2]))
                        hDice3Resource = getImage(humanRandomNoArr[2])
                    }else if(i+1 == 4){
                        humanRandomNoArr[3] = getRandomHumanDiceNumber()
                        hDice4.setImageResource(getImage(humanRandomNoArr[3]))
                        hDice4Resource = getImage(humanRandomNoArr[3])
                    }else if(i+1 == 5){
                        humanRandomNoArr[4] = getRandomHumanDiceNumber()
                        hDice5.setImageResource(getImage(humanRandomNoArr[4]))
                        hDice5Resource = getImage(humanRandomNoArr[4])
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
                            hDice1Resource = getImage(humanRandomNoArr[0])
                        }else if(i+1 == 2){
                            hDice2.setImageResource(getImage(humanRandomNoArr[1]))
                            hDice2Resource = getImage(humanRandomNoArr[1])
                        }else if(i+1 == 3){
                            hDice3.setImageResource(getImage(humanRandomNoArr[2]))
                            hDice3Resource = getImage(humanRandomNoArr[2])
                        }else if(i+1 == 4){
                            hDice4.setImageResource(getImage(humanRandomNoArr[3]))
                            hDice4Resource = getImage(humanRandomNoArr[3])
                        }else if(i+1 == 5){
                            hDice5.setImageResource(getImage(humanRandomNoArr[4]))
                            hDice5Resource = getImage(humanRandomNoArr[4])
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
        val reRollText : String = "$reRollsCount - REROLL"
        reRoll.text = reRollText

    }

    fun humanImg1Clicked(view: View) {
        val targetNumber = 1
        val count = reRollArr.count { it == targetNumber }
        if (reRollArr[0] == 1){
            hDice1.setImageResource(getImage(humanRandomNoArr[0]))
            hDice1Resource = getImage(humanRandomNoArr[0])
            reRollArr[0] = 0
        }else{
            if(count == 4){
                Toast.makeText(this, "Only maximum 4 dices can be selected.", Toast.LENGTH_SHORT).show()
            }else{
                hDice1.setImageResource(getImageWithSelection(humanRandomNoArr[0]))
                hDice1Resource = getImageWithSelection(humanRandomNoArr[0])
                reRollArr[0] = 1
            }
        }
    }
    fun humanImg2Clicked(view: View) {
        val targetNumber = 1
        val count = reRollArr.count { it == targetNumber }
        if(reRollArr[1] == 1){
            hDice2.setImageResource(getImage(humanRandomNoArr[1]))
            hDice2Resource = getImage(humanRandomNoArr[1])
            reRollArr[1] = 0

        }else{
            if(count == 4){
                Toast.makeText(this, "Only maximum 4 dices can be selected.", Toast.LENGTH_SHORT).show()
            }else{
                hDice2.setImageResource(getImageWithSelection(humanRandomNoArr[1]))
                hDice2Resource = getImageWithSelection(humanRandomNoArr[1])
                reRollArr[1] = 1
            }
        }
    }
    fun humanImg3Clicked(view: View) {
        val targetNumber = 1
        val count = reRollArr.count { it == targetNumber }
        if(reRollArr[2] == 1){
            hDice3.setImageResource(getImage(humanRandomNoArr[2]))
            hDice3Resource = getImage(humanRandomNoArr[2])
            reRollArr[2] = 0
        }else{
            if(count == 4){
                Toast.makeText(this, "Only maximum 4 dices can be selected.", Toast.LENGTH_SHORT).show()
            }else{
                hDice3.setImageResource(getImageWithSelection(humanRandomNoArr[2]))
                hDice3Resource = getImageWithSelection(humanRandomNoArr[2])
                reRollArr[2] = 1
            }
        }
    }
    fun humanImg4Clicked(view: View) {
        val targetNumber = 1
        val count = reRollArr.count { it == targetNumber }
        if(reRollArr[3] == 1 ){
            hDice4.setImageResource(getImage(humanRandomNoArr[3]))
            hDice4Resource = getImage(humanRandomNoArr[3])
            reRollArr[3] = 0

        }else{
            if(count == 4){
                Toast.makeText(this, "Only maximum 4 dices can be selected.", Toast.LENGTH_SHORT).show()
            }else{
                hDice4.setImageResource(getImageWithSelection(humanRandomNoArr[3]))
                hDice4Resource = getImageWithSelection(humanRandomNoArr[3])
                reRollArr[3] = 1
            }
        }
    }
    fun humanImg5Clicked(view: View) {
        val targetNumber = 1
        val count = reRollArr.count { it == targetNumber }
        if(reRollArr[4] == 1){
            hDice5.setImageResource(getImage(humanRandomNoArr[4]))
            hDice5Resource = getImage(humanRandomNoArr[4])
            reRollArr[4] = 0
        }else{
            if(count == 4){
                Toast.makeText(this, "Only maximum 4 dices can be selected.", Toast.LENGTH_SHORT).show()
            }else{
                hDice5.setImageResource(getImageWithSelection(humanRandomNoArr[4]))
                hDice5Resource = getImageWithSelection(humanRandomNoArr[4])
                reRollArr[4] = 1
            }
        }
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    fun scoreButtonClicked(view: View) {
        if(reRollsCount == 2){
            if(gameMode == "hard"){
//            new computer strategy
                compReRollStrategy()
                compReRollStrategy()
            }else{
//            for the comp player strategy(Random)
                compReRoll()
                compReRoll()
            }
        }else if(reRollsCount == 1){
            if(gameMode == "hard"){
//            new computer strategy
                compReRollStrategy()
            }else{
//            for the comp player strategy(Random)
                compReRoll()
            }
        }

        reRollsCount = 2
        val reRollText : String = "$reRollsCount - REROLL"
        reRoll.text = reRollText

        humanMainTotal += humanRandomNoArr.sum()

        compMainTotal += compRandomNoArr.sum()

        if(humanMainTotal > compMainTotal){
            scoreBoard.setTextColor(Color.GREEN)
        }else if(humanMainTotal == compMainTotal){
            scoreBoard.setTextColor(Color.YELLOW)
        }else{
            scoreBoard.setTextColor(Color.RED)
        }

        scoreBoard.text = "H:$humanMainTotal        -        C:$compMainTotal"

        var gameOver = false

        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view1 = inflater.inflate(R.layout.final_result_layout, null)
        builder.setView(view1)
        val dialog = builder.create()
        val title = view1.findViewById<TextView>(R.id.finalTitle)
        val message = view1.findViewById<TextView>(R.id.finalMessage)
        val back = view1.findViewById<Button>(R.id.finalButton)


        if(humanMainTotal >= targetValue && compMainTotal >= targetValue){
            if(humanMainTotal > compMainTotal){
                title.text = "You Win!"
                title.setTextColor(Color.GREEN)
                message.text = "Congratulations, you have reached more than $targetValue points!"
                humanTotalWins += 1
                gameOver = true
            }else if (humanMainTotal == compMainTotal){
                tie = true
                Toast.makeText(this, "Match tied throw again", Toast.LENGTH_SHORT).show()
            }else{
                title.text = "You Lose!"
                title.setTextColor(Color.RED)
                message.text = "Better luck next time!!"
                compTotalWins += 1
                gameOver = true
            }
        }else if(humanMainTotal>= targetValue){
            title.text = "You Win!"
            title.setTextColor(Color.GREEN)
            message.text = "Congratulations, you have reached more than $targetValue points!"
            humanTotalWins += 1
            gameOver = true
        }else if(compMainTotal >= targetValue){
            title.text = "You Lose!"
            title.setTextColor(Color.RED)
            message.text = "Better luck next time!!"
            compTotalWins += 1
            gameOver = true
        }

        if (gameOver){
            val bundle = Bundle()
            bundle.putInt("humanTotalWins",humanTotalWins)
            bundle.putInt("compTotalWins",compTotalWins)
            bundle.putBoolean("gameRuleShown",gameRuleShown)
            dialog.show()
            back.setOnClickListener(){
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.putExtras(bundle)
                startActivity(mainIntent)
                finish()
            }
            dialog.setOnCancelListener {
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.putExtras(bundle)
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
            hDice1Resource = R.drawable.throwing
            hDice2.setImageResource(R.drawable.throwing)
            hDice2Resource = R.drawable.throwing
            hDice3.setImageResource(R.drawable.throwing)
            hDice3Resource = R.drawable.throwing
            hDice4.setImageResource(R.drawable.throwing)
            hDice4Resource = R.drawable.throwing
            hDice5.setImageResource(R.drawable.throwing)
            hDice5Resource = R.drawable.throwing

            cDice1.setImageResource(R.drawable.waiting2)
            cDice1Resource = R.drawable.waiting2
            cDice2.setImageResource(R.drawable.waiting2)
            cDice2Resource = R.drawable.waiting2
            cDice3.setImageResource(R.drawable.waiting2)
            cDice3Resource = R.drawable.waiting2
            cDice4.setImageResource(R.drawable.waiting2)
            cDice4Resource = R.drawable.waiting2
            cDice5.setImageResource(R.drawable.waiting2)
            cDice5Resource = R.drawable.waiting2

        }, 350)
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
        val bool = compDecisionOnReRoll()
        if(bool){
            val compReRollArr = IntArray(5)
            for (i in compReRollArr.indices){
                val decision = (0..1).random()
                compReRollArr[i] = decision
            }
            for ( i in compReRollArr.indices){
                if(compReRollArr[i] == 1){
                    if( i+1 == 1 ){
                        compRandomNoArr[0] = getRandomCompDiceNumber()
                        cDice1.setImageResource(getImage(compRandomNoArr[0]))
                        cDice1Resource = getImage(compRandomNoArr[0])
                    }else if(i+1 == 2){
                        compRandomNoArr[1] = getRandomCompDiceNumber()
                        cDice2.setImageResource(getImage(compRandomNoArr[1]))
                        cDice2Resource = getImage(compRandomNoArr[1])
                    }else if(i+1 == 3){
                        compRandomNoArr[2] = getRandomCompDiceNumber()
                        cDice3.setImageResource(getImage(compRandomNoArr[2]))
                        cDice3Resource = getImage(compRandomNoArr[2])
                    }else if(i+1 == 4){
                        compRandomNoArr[3] = getRandomCompDiceNumber()
                        cDice4.setImageResource(getImage(compRandomNoArr[3]))
                        cDice4Resource = getImage(compRandomNoArr[3])
                    }else if(i+1 == 5){
                        compRandomNoArr[4] = getRandomCompDiceNumber()
                        cDice5.setImageResource(getImage(compRandomNoArr[4]))
                        cDice5Resource = getImage(compRandomNoArr[4])
                    }
                }
            }
        }

    }

/* For the efficient strategy the compReRollStrategy function is called. The strategy is it checks
   whether the human total is greater than or equal to computers total if so it will keep all
   the dice images that are greater than 5 and re-roll others. If the human total is less than the
   computer total then all the dice images that are greater than the 4 are kept and other dice are
   rerolled*/
    private fun compReRollStrategy(){
        val compReRollArr = IntArray(5)
        for ( i in compRandomNoArr.indices){
            if(humanMainTotal >= compMainTotal){
                if(compRandomNoArr[i] < 5){
                    compReRollArr[i] = 1
                }
            }else{
                if(compRandomNoArr[i] < 4){
                    compReRollArr[i] = 1
                }
            }
        }
        for ( i in compReRollArr.indices){
            if(compReRollArr[i] == 1){
                if( i+1 == 1 ){
                    compRandomNoArr[0] = getRandomCompDiceNumber()
                    cDice1.setImageResource(getImage(compRandomNoArr[0]))
                    cDice1Resource = getImage(compRandomNoArr[0])
                }else if(i+1 == 2){
                    compRandomNoArr[1] = getRandomCompDiceNumber()
                    cDice2.setImageResource(getImage(compRandomNoArr[1]))
                    cDice2Resource = getImage(compRandomNoArr[1])
                }else if(i+1 == 3){
                    compRandomNoArr[2] = getRandomCompDiceNumber()
                    cDice3.setImageResource(getImage(compRandomNoArr[2]))
                    cDice3Resource = getImage(compRandomNoArr[2])
                }else if(i+1 == 4){
                    compRandomNoArr[3] = getRandomCompDiceNumber()
                    cDice4.setImageResource(getImage(compRandomNoArr[3]))
                    cDice4Resource = getImage(compRandomNoArr[3])
                }else if(i+1 == 5){
                    compRandomNoArr[4] = getRandomCompDiceNumber()
                    cDice5.setImageResource(getImage(compRandomNoArr[4]))
                    cDice5Resource = getImage(compRandomNoArr[4])
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