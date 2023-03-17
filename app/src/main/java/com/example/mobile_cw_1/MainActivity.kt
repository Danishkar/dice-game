package com.example.mobile_cw_1

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayer : MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mediaPlayer = MediaPlayer.create(this, R.raw.starting_music)
        mediaPlayer.setVolume(0.25f, 0.25f)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    fun aboutClicked(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view1 = inflater.inflate(R.layout.about_details, null)
        builder.setView(view1)
        val dialog = builder.create()
        dialog.show()
    }


    fun newGameClicked(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view1 = inflater.inflate(R.layout.default_value_layout, null)
        builder.setView(view1)

        val editText = view1.findViewById<EditText>(R.id.editText)
        val button = view1.findViewById<Button>(R.id.defaultButton)
        val errorMessage = view1.findViewById<TextView>(R.id.defaultErrorMessage)
        val dialog = builder.create()
        var validInt : Boolean = false

        button.setOnClickListener {
            // Get the text from the edit text
            errorMessage.setText("")
            val userText = editText.text.toString()
            var defaultValue : Int = 0
            var userInt = userText.toIntOrNull()
            if(userText == ""){
                defaultValue = 101
                validInt = true
                dialog.dismiss()

            }else{
                if (userInt == null) {
                    errorMessage.setText("Please enter a valid integer")
                    errorMessage.setTextColor(ContextCompat.getColor(this, R.color.red))
                } else {
                    if (userInt != null) {
                        defaultValue = userInt
                        validInt = true
                        dialog.dismiss()
                    }
                }
            }

            val bundle = Bundle()
            bundle.putInt("intValue",defaultValue)
            if (validInt){
                val newGameIntent = Intent(this,NewGame::class.java)
                newGameIntent.putExtras(bundle)
                startActivity(newGameIntent)
            }
        }
        dialog.show()
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