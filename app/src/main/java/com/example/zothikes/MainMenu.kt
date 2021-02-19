package com.example.zothikes

import android.content.Intent
import android.os.Bundle
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
        findViewById<Button>(R.id.open_map_button).setOnClickListener{
            val intent = Intent(this, NearbyMap::class.java).apply{}
            startActivity(intent)
        }

        findViewById<Button>(R.id.recommend_button).setOnClickListener{
            val intent = Intent(this, RecommendationPage::class.java).apply{}
            startActivity(intent)
        }
    }


}
