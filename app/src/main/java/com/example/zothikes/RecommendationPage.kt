package com.example.zothikes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecommendationPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation_page)
        findViewById<TextView>(R.id.first_trail).apply {text = intent.getStringExtra("0")}
        findViewById<TextView>(R.id.second_trail).apply {text = intent.getStringExtra("1")}
        findViewById<TextView>(R.id.third_trail).apply {text = intent.getStringExtra("2")}
        findViewById<TextView>(R.id.fourth_trail).apply {text = intent.getStringExtra("3")}
        findViewById<TextView>(R.id.fifth_trail).apply {text = intent.getStringExtra("4")}

//        //because 5 trails
//        intent.getStringExtra("latitudes0")?.let { Log.d("Latitude is:", it) }
//        intent.getStringExtra("longitudes0")?.let { Log.d("Longitude is:", it) }
        findViewById<Button>(R.id.view_on_map_button).setOnClickListener{
            val intent = Intent(this, NearbyMap::class.java).apply{
                putExtra("0", intent.getStringExtra("0"))
                putExtra("1", intent.getStringExtra("1"))
                putExtra("2", intent.getStringExtra("2"))
                putExtra("3", intent.getStringExtra("3"))
                putExtra("4", intent.getStringExtra("4"))
                putExtra("latitudes0", intent.getStringExtra("latitudes0"))
                putExtra("longitudes0", intent.getStringExtra("longitudes0"))
                putExtra("latitudes1", intent.getStringExtra("latitudes1"))
                putExtra("longitudes1", intent.getStringExtra("longitudes1"))
                putExtra("latitudes2", intent.getStringExtra("latitudes2"))
                putExtra("longitudes2", intent.getStringExtra("longitudes2"))
                putExtra("latitudes3", intent.getStringExtra("latitudes3"))
                putExtra("longitudes3", intent.getStringExtra("longitudes3"))
                putExtra("latitudes4", intent.getStringExtra("latitudes4"))
                putExtra("longitudes4", intent.getStringExtra("longitudes4"))
            }
            startActivity(intent)
        }

    }
}