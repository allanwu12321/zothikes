package com.example.zothikes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

//used code from https://johncodeos.com/how-to-add-google-login-button-to-your-android-app-using-kotlin/
lateinit var mGoogleSignInClient: GoogleSignInClient
private val RC_SIGN_IN = 9001

class UserLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("273771959787-tqmqc0csjdupjb6tadevbtck6lgt9dfs.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val loginButton = findViewById<Button>(R.id.login_button)
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        loginButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )
    }
    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // Update your UI here
            }
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // Update your UI here
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID",googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

            Toast.makeText(this, "Successfully logged in.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainMenu::class.java)
            intent.putExtra("google_id", googleId)
            intent.putExtra("google_first_name", googleFirstName)
            intent.putExtra("google_last_name", googleLastName)
            intent.putExtra("google_email", googleEmail)
            intent.putExtra("google_profile_pic_url", googleProfilePicURL)
            intent.putExtra("google_id_token", googleIdToken)
            this.startActivity(intent)

        } catch (e: ApiException) {
            Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }

}
