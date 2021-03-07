package com.example.zothikes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * to do:
 * if possible make it so user doesn't have to retype everything after hitting back button
 */

class DisplayUserInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user_info)

        //confirm button code
        findViewById<Button>(R.id.confirm_button).setOnClickListener{
            Toast.makeText(this, "Information updated.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainMenu::class.java).apply{
                // Temporary way to pass User information
                putExtra("name", intent.getStringExtra("name"))
                putExtra("age", intent.getStringExtra("age"))
                putExtra("height", intent.getStringExtra("height"))
                putExtra("weight", intent.getStringExtra("weight"))
                putExtra("gender", intent.getStringExtra("gender"))
                putExtra("goal", intent.getStringExtra("goal"))
            }
            startActivity(intent)
        }

        //set TextViews to user data from previous activity
        findViewById<TextView>(R.id.confirm_name).apply {text = intent.getStringExtra("name")}
        findViewById<TextView>(R.id.confirm_age).apply {text = intent.getStringExtra("age")}
        findViewById<TextView>(R.id.confirm_height).apply {text = intent.getStringExtra("height")}
        findViewById<TextView>(R.id.confirm_weight).apply {text = intent.getStringExtra("weight")}
        findViewById<TextView>(R.id.confirm_gender).apply {text = intent.getStringExtra("gender")}
        findViewById<TextView>(R.id.confirm_goal).apply {text = intent.getStringExtra("goal")}
        findViewById<TextView>(R.id.confirm_wake).apply {text = intent.getStringExtra("wake")}
        findViewById<TextView>(R.id.confirm_sleep).apply {text = intent.getStringExtra("sleep")}

    }
}