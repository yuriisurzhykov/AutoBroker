package com.yuriysurzhikov.autobroker.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.R

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName
    private val RC_SIGN_IN: Int = 21
    private var googleSignInClient: GoogleSignInClient? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createClient()
        findViewById<View>(R.id.google_sign_in).setOnClickListener {
            signIn()
        }
    }

    private fun createClient() {
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_oauth_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.e(TAG, "onActivityResult: ${e.message}")
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.e(TAG, "firebaseAuthWithGoogle: ${user?.uid}")
                    Log.e(TAG, "firebaseAuthWithGoogle: ${user?.metadata}")
                    Log.e(TAG, "firebaseAuthWithGoogle: ${user?.displayName}")
                    Log.e(TAG, "firebaseAuthWithGoogle: ${user?.email}")
                    Log.e(TAG, "firebaseAuthWithGoogle: ${user?.phoneNumber}")
                    Log.e(TAG, "firebaseAuthWithGoogle: ${user?.isEmailVerified}")
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }.addOnCanceledListener {
                Log.e(TAG, "firebaseAuthWithGoogle: canceled auth")
            }.addOnFailureListener {
                Log.e(TAG, "firebaseAuthWithGoogle: failed to auth")
            }
    }
}