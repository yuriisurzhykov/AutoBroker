package com.yuriysurzhikov.autobroker.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import java.lang.IllegalStateException

class MainLoginFragment : AbstractLoginFragment() {

    private val TAG = LoginActivity::class.simpleName
    private val RC_SIGN_IN: Int = 21
    private var googleSignInClient: GoogleSignInClient? = null
    private lateinit var callback: ILoginCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        check(context is ILoginCallback) {
            throw IllegalStateException("Activity must implement ILoginCallback's")
        }
        callback = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createClient()
    }

    private fun createClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_oauth_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.google_sign_in)?.setOnClickListener {
            signIn()
        }
        viewModel.observeResult(viewLifecycleOwner, onResultObserver)
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
                context?.getString(R.string.error_fail_login)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    viewModel.tryLogin(Firebase.auth.currentUser)
                } else {
                    showError(context?.getString(R.string.error_fail_login))
                }
            }
    }

    private fun showError(message: String?) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private val onResultObserver = Observer<Int> { result ->
        when (result) {
            ErrorCode.OK -> callback.onLoginSuccess()
            ErrorCode.ON_BOARDING_NEEDED -> callback.openOnBoarding()
        }
    }
}