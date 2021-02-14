package com.example.zothikes

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //List containing user input. Goes in order of name, age, height, weight, gender, target weight.
        val user_data = mutableListOf<String>()
        //Finish button action
        findViewById<Button>(R.id.finish_button).setOnClickListener{
            submitInfo(it, user_data)

        }

    }

    /**
     * to do:
     * verify data is correctly extracted from user inputs
     * fix keyboard not being hidden
     * create an app icon
     * make toast darker to cover button
     */

    private fun submitInfo(view: View, user_data: MutableList<String>) {
        user_data += findViewById<EditText>(R.id.name_input).text.toString()
        user_data += findViewById<EditText>(R.id.age_input).text.toString()
        user_data += findViewById<EditText>(R.id.height_input).text.toString()
        user_data += findViewById<EditText>(R.id.weight_input).text.toString()
        user_data += findViewById<EditText>(R.id.gender_input).text.toString()
        user_data += findViewById<EditText>(R.id.goal_input).text.toString()
        Toast.makeText(this, "Information updated.", Toast.LENGTH_SHORT).show()

        val editText = findViewById<EditText>(R.id.goal_input)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayUserInfo::class.java).apply{
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}