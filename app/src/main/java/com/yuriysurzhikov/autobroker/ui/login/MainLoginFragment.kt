package com.yuriysurzhikov.autobroker.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.FragmentMainLoginBinding
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.ui.text.ErrorTextWatcher
import com.yuriysurzhikov.autobroker.ui.text.LoginTextWatcher
import com.yuriysurzhikov.autobroker.util.SoftKeyboardUtil
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException

@AndroidEntryPoint
class MainLoginFragment : AbstractLoginFragment() {

    private val TAG = LoginActivity::class.simpleName
    private val RC_SIGN_IN: Int = 21
    private var googleSignInClient: GoogleSignInClient? = null
    private lateinit var callback: ILoginCallback
    private lateinit var binding: FragmentMainLoginBinding

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
        binding = FragmentMainLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.usernameWatcher = LoginTextWatcher(timerTask)
        binding.usernameWatcher = ErrorTextWatcher(binding.loginInput)
        binding.passwordWatcher = ErrorTextWatcher(binding.passwordInput)
        viewModel.observeResult(viewLifecycleOwner, onResultObserver)

        binding.googleSignIn.setOnClickListener {
            signIn()
        }
        binding.loginButton.setOnClickListener {
            if (binding.loginInputText.text.isNullOrBlank()) {
                binding.loginInput.error = it.context.getString(R.string.error_field_empty)
                if (binding.passwordInputText.text.isNullOrBlank()) {
                    binding.passwordInput.error = it.context.getString(R.string.error_field_empty)
                }
                return@setOnClickListener
            }
            viewModel.tryLogin(
                binding.loginInputText.text.toString(),
                binding.passwordInputText.text.toString(),
                true
            )
        }
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
                    showMessage(context?.getString(R.string.error_fail_login))
                }
            }
    }

    private val timerTask = object : Runnable {
        override fun run() {
            viewModel.tryLogin(
                binding.loginInputText.text.toString(),
                binding.passwordInputText.text.toString(),
                false
            )
        }
    }

    private val onResultObserver = Observer<Pair<Int, Boolean>> { result ->
        when (result.first) {
            ErrorCode.OK -> {
                SoftKeyboardUtil.closeSoftKeyboard(view!!)
                viewModel.user.get()?.strId?.let { callback.onLoginSuccess(it) }
            }
            ErrorCode.ERROR_NO_SUCH_USER -> {
                if (result.second) {
                    showMessage(context?.getString(R.string.error_no_such_user))
                }
            }
            ErrorCode.ERROR_ON_BOARDING_NEEDED -> {
                SoftKeyboardUtil.closeSoftKeyboard(view!!)
                callback.openOnBoarding()
            }
        }
    }
}