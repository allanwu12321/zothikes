package com.example.zothikes

import android.os.Bundle
import android.util.Log
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

        //because 5 trails

    }
}