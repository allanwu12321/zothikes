package com.example.zothikes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val user_data = mutableListOf<String>()

        findViewById<Button>(R.id.open_map_button).setOnClickListener{
            val intent = Intent(this, NearbyMap::class.java).apply{}
            startActivity(intent)
        }

        findViewById<Button>(R.id.recommend_button).setOnClickListener{
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
