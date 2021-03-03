package com.example.zothikes

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * to do :
 * fix button/editText disappearance after a certain length of text
 * only take in minutes of exercise? and then add to db
 */
@Suppress("DEPRECATION")
class ActivityLog : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        val activityLog = findViewById<LinearLayout>(R.id.activity_log)
        findViewById<Button>(R.id.add_activity_button).setOnClickListener {
            val dateAndTime: String = SimpleDateFormat("'['MM/dd/yyyy']' HH:mm:ss ': '").format(Date())
            val newActivity = TextView(this)
            newActivity.textSize = 20.0F
            newActivity.setPadding(8, 0, 8, 0)
            newActivity.text = Html.fromHtml("<b>" + dateAndTime + "</b> " + findViewById<EditText>(R.id.input_activity).text.toString())
            findViewById<EditText>(R.id.input_activity).text.clear()
            activityLog.addView(newActivity)
        }
    }
}