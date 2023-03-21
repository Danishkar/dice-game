package com.example.mobile_cw_1

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mediaPlayer : MediaPlayer
    private var humanTotalWins : Int = 0
    private var compTotalWins : Int = 0
    private var gameRuleShown :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mediaPlayer = MediaPlayer.create(this, R.raw.starting_music)
        mediaPlayer.setVolume(0.25f, 0.25f)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        val bundle = intent.extras
        if (bundle != null && bundle.containsKey("humanTotalWins")) {
            humanTotalWins = bundle.getInt("humanTotalWins")
            compTotalWins = bundle.getInt("compTotalWins")
            gameRuleShown = bundle.getBoolean("gameRuleShown")
        }

    }

    fun aboutClicked(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view1 = inflater.inflate(R.layout.about_details, null)
        builder.setView(view1)
        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    fun newGameClicked(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view1 = inflater.inflate(R.layout.default_value_layout, null)
        builder.setView(view1)
        val editText = view1.findViewById<EditText>(R.id.editText)
        val defaultButton = view1.findViewById<Button>(R.id.defaultButton)
        val errorMessage = view1.findViewById<TextView>(R.id.defaultErrorMessage)
        val defaultDialog = builder.create()
        var validInt = false
        defaultDialog.show()

        val builder2 = AlertDialog.Builder(this)
        val inflater2 = LayoutInflater.from(this)
        val view2 = inflater2.inflate(R.layout.mode_layout, null)
        builder2.setView(view2)
        val easyButton = view2.findViewById<Button>(R.id.easyMode)
        val hardButton = view2.findViewById<Button>(R.id.hardMode)
        val modeDialog = builder2.create()

        var defaultValue : Int = 0
        defaultButton.setOnClickListener {
            // Get the text from the edit text
            errorMessage.text = ""
            val userText = editText.text.toString()
            val userInt = userText.toIntOrNull()
            if(userText == ""){
                defaultValue = 101
                validInt = true
                defaultDialog.dismiss()
                modeDialog.show()
            }else{
                if (userInt == null) {
                    errorMessage.text = "Please enter a valid integer"
                    errorMessage.setTextColor(ContextCompat.getColor(this, R.color.red))
                } else {
                    defaultValue = userInt
                    validInt = true
                    defaultDialog.dismiss()
                    modeDialog.show()
                }
            }
        }
        easyButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("intValue",defaultValue)
            bundle.putString("gameMode","easy")
            bundle.putInt("humanTotalWins",humanTotalWins)
            bundle.putInt("compTotalWins",compTotalWins)
            bundle.putBoolean("gameRuleShown",gameRuleShown)
            if (validInt){
                val newGameIntent = Intent(this,NewGame::class.java)
                newGameIntent.putExtras(bundle)
                startActivity(newGameIntent)
            }
            modeDialog.dismiss()
        }
        hardButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("intValue",defaultValue)
            bundle.putString("gameMode","hard")
            bundle.putInt("humanTotalWins",humanTotalWins)
            bundle.putInt("compTotalWins",compTotalWins)
            bundle.putBoolean("gameRuleShown",gameRuleShown)
            if (validInt){
                val newGameIntent = Intent(this,NewGame::class.java)
                newGameIntent.putExtras(bundle)
                startActivity(newGameIntent)
            }
            modeDialog.dismiss()
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