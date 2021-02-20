package com.example.zothikes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * to do
 * add "update/change user info"
 * add "activity log"
 */

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Temporary way to pass User information
        var bundle: Bundle? = intent.extras
        val name: String? = bundle?.getString("name")
        val age: String? = bundle?.getString("age")
        val height: String? = bundle?.getString("height")
        val weight: String? = bundle?.getString("weight")
        val gender: String? = bundle?.getString("gender")
        val goal: String? = bundle?.getString("goal")
//        Log.v("TESTINGNAME", "" + name)

        findViewById<Button>(R.id.open_map_button).setOnClickListener{
            val intent = Intent(this, NearbyMap::class.java).apply{}
            startActivity(intent)
        }

        findViewById<Button>(R.id.recommend_button).setOnClickListener{
            val intent = Intent(this, RecommendationPage::class.java).apply{
                // Temporary way to pass User information
                putExtra("name", name)
                putExtra("age", age)
                putExtra("height", height)
                putExtra("weight", weight)
                putExtra("gender", gender)
                putExtra("goal", goal)
            }
            startActivity(intent)
        }
    }


}
