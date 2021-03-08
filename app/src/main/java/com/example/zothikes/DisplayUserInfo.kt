package com.example.zothikes

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

/**
 * to do:
 * if possible make it so user doesn't have to retype everything after hitting back button
 */

class DisplayUserInfo : AppCompatActivity() {
    var volleyRequestQueue: RequestQueue? = null
    var dialog: ProgressDialog? = null
    val serverAPIURL: String = "http://10.0.2.2:5000/api/post_user"
    val TAG = "ZotHikes"
    fun SendSignUpDataToServer(name: String?, age: String?, height: String?, weight: String?, gender: String?, target_weight: String?) {
        volleyRequestQueue = Volley.newRequestQueue(this)
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        val parameters: MutableMap<String, String?> = HashMap()
        // Add your parameters in HashMap
        parameters["name"] = name;
        parameters["age"] = age;
        parameters["height"] = height;
        parameters["weight"] = weight;
        parameters["gender"] = gender;
        parameters["target_weight"] = target_weight;

        val strReq: StringRequest = object : StringRequest(
                Method.POST,serverAPIURL,
                Response.Listener { response ->
                    Log.e(TAG, "response: " + response)
                    dialog?.dismiss()

                    // Handle Server response here
//                    try {
//                        val responseObj = JSONObject(response)
//                        val isSuccess = responseObj.getBoolean("isSuccess")
//                        val code = responseObj.getInt("code")
//                        val message = responseObj.getString("message")
//                        if (responseObj.has("data")) {
//                            val data = responseObj.getJSONObject("data")
//                            // Handle your server response data here
//                        }
//                        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
//
//                    } catch (e: Exception) { // caught while parsing the response
//                        Log.e(TAG, "problem occurred")
//                        e.printStackTrace()
//                    }
                },
                Response.ErrorListener { volleyError -> // error occurred
                    Log.e(TAG, "problem occurred, volley error: " + volleyError.message)
                }) {

            override fun getParams(): MutableMap<String, String?> {
                return parameters;
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                return JSONObject(parameters as Map<*, *>).toString().toByteArray()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {

                val headers: MutableMap<String, String> = HashMap()
                // Add your Header paramters here
                return headers
            }
        }
        // Adding request to request queue
        volleyRequestQueue?.add(strReq)
    }
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
//            intent.getStringExtra("name")?.let { it1 -> Log.d("name", it1) }
            SendSignUpDataToServer(intent.getStringExtra("name"), intent.getStringExtra("age"), intent.getStringExtra("height"), intent.getStringExtra("weight"), intent.getStringExtra("gender"), intent.getStringExtra("goal"))
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