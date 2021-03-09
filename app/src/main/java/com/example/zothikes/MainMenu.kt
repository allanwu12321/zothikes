package com.example.zothikes

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.json.JSONObject

/**
 * to do:
 * get user activity levels and wake up time
 * add "activity log" -- data should go to recommendation system to help get to target weight
 * choose city? how do we know which hiking trails to display? maybe need to have a distance radius setting too.
 *
 * notification? -- complicated
 * no need to pass data around through intents, should be using db anyways.
 *
 * styling:
 * matching button sizes
 * maybe new font
 * new image
 *
 */

class MainMenu : AppCompatActivity() {

    var volleyRequestQueue: RequestQueue? = null
    var dialog: ProgressDialog? = null
    val serverAPIURL: String = "http://10.0.2.2:5000/api/get_recommendation"
    val TAG = "ZotHikes"
    fun SendSignUpDataToServer(email: String?) {
        volleyRequestQueue = Volley.newRequestQueue(this)
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        val parameters: MutableMap<String, String?> = HashMap()
        // Add your parameters in HashMap
        parameters["email"] = email;

        val strReq: StringRequest = object : StringRequest(
                Method.GET,serverAPIURL,
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
        setContentView(R.layout.activity_main_menu)
        val user_data = mutableListOf<String>()

        findViewById<Button>(R.id.open_map_button).setOnClickListener{
            val intent = Intent(this, NearbyMap::class.java).apply{}
            startActivity(intent)
        }

        findViewById<Button>(R.id.recommend_button).setOnClickListener{
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                SendSignUpDataToServer((acct.email))
            }
            val intent = Intent(this, RecommendationPage::class.java).apply{}
            startActivity(intent)
        }

        findViewById<Button>(R.id.log_button).setOnClickListener{
            val intent = Intent(this, ActivityLog::class.java).apply{}
            startActivity(intent)
        }

        findViewById<Button>(R.id.edit_info_button).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java).apply{}
            startActivity(intent)
            //mainActivity is to enter user's info.
        }

        findViewById<Button>(R.id.logout_button).setOnClickListener{
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this) {
                        // Update your UI here
                        val intent = Intent(this, UserLogin::class.java).apply{}
                        startActivity(intent)
                        Toast.makeText(this, "Successfully logged out.", Toast.LENGTH_SHORT).show()
                    }
        }


    }


}
