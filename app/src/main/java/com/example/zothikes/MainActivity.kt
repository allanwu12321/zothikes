package com.example.zothikes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * to do:
 * create an app icon
 * make toast darker to cover button
 */

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

    //adds data to muteablelist and passes data to next activity on click of finish button
    private fun submitInfo(view: View, user_data: MutableList<String>) {
        user_data.clear() // prevent duplicates if user is making edits.
        user_data += findViewById<EditText>(R.id.name_input).text.toString()
        user_data += findViewById<EditText>(R.id.age_input).text.toString()
        user_data += findViewById<EditText>(R.id.height_input).text.toString()
        user_data += findViewById<EditText>(R.id.weight_input).text.toString()
        user_data += findViewById<EditText>(R.id.gender_input).text.toString()
        user_data += findViewById<EditText>(R.id.goal_input).text.toString()
        Toast.makeText(this, "Information updated.", Toast.LENGTH_SHORT).show()

        //pass data to next activity screen
        val intent = Intent(this, DisplayUserInfo::class.java).apply{
            //not sure how to pass the entire user_data MuteableList, so doing each String individually for now
            putExtra("name", user_data[0])
            putExtra("age", user_data[1])
            putExtra("height", user_data[2])
            putExtra("weight", user_data[3])
            putExtra("gender", user_data[4])
            putExtra("goal", user_data[5])
        }
        startActivity(intent)
    }
}