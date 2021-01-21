package com.project.travelmedrivers

import android.R.*
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.travelmedrivers.*


class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
            if (sharedPreferences.getBoolean(currentUser.uid, false)) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return
            }
        }
        createSignInIntent()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),


            )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers).setIsSmartLockEnabled(false)
                .build(),
            RC_SIGN_IN
        )
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @SuppressLint("CommitPrefEdits")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                currentUser = mAuth.currentUser!!
                // Successfully signed in
                if (!currentUser.isEmailVerified){
                    findViewById<TextView>(R.id.tvLogin).setText("AN EMAIL SENT TO :${currentUser.email} \n PLEASE VERIFY YOUR EMAIL ADDRESS AND COME BACK")
                    sendEmailVerification()}
                else {
                    val editor = sharedPreferences.edit()
                    editor.putBoolean(currentUser.uid, true)
                    editor.apply()
                    findViewById<TextView>(R.id.tvLogin).setText("CONNECTED")

                    startActivity(Intent(this, MainActivity::class.java))
                }
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    // [END auth_fui_result]

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_signout]
    }

    private fun delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_delete]
    }

    private fun themeAndLogo() {
        val providers = emptyList<AuthUI.IdpConfig>()
        // [START auth_fui_theme_logo]
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)

                // Set theme
                .build(),
            RC_SIGN_IN
        )
        // [END auth_fui_theme_logo]
    }

    private fun privacyAndTerms() {
        val providers = emptyList<AuthUI.IdpConfig>()
        // [START auth_fui_pp_tos]
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html"
                )
                .build(),
            RC_SIGN_IN
        )
        // [END auth_fui_pp_tos]
    }

    companion object {

        const val RC_SIGN_IN = 123
    }

    private fun sendEmailVerification() {
        currentUser.sendEmailVerification()
            .addOnCompleteListener(
                this
            ) { task ->
                // Re-enable Verify Email button
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Verification email sent to " + currentUser.email,
                        Toast.LENGTH_SHORT
                    ).show()
                    if (currentUser.isEmailVerified) {
                        // user is verified, so you can finish this activity or send user to activity which you want.
                        Log.i("fff", "email")
                        startActivity(Intent(this, MainActivity::class.java))
                        Toast.makeText(
                            this@LoginActivity,
                            "Successfully logged in",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

