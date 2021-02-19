package com.example.zothikes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        findViewById<Button>(R.id.open_map_button).setOnClickListener{
            val intent = Intent(this, NearbyMap::class.java).apply{}
            startActivity(intent)
        }
    }


}
