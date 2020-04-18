package org.tiramisu.account.signin

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_account.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.tiramisu.account.R
import org.tiramisu.account.data.model.UserData
import org.tiramisu.repository.Result
import org.tiramisu.utils.afterTextChanged

abstract class AccountActivity : AppCompatActivity() {

    private val accountViewModel by viewModels<AccountViewModel>(::getViewModelFactory)

    abstract fun getViewModelFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_account)

        accountViewModel.formState.observe(this, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                phone.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        accountViewModel.result.observe(this@AccountActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            when (loginResult) {
                is Result.Failure -> {
                    showLoginFailed(loginResult.error.message)
                }
                is Result.Success -> {
                    updateUiWithUser(loginResult.get())
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        })

        phone.afterTextChanged {
            accountViewModel.loginDataChanged(phone.text.toString(), password.text.toString())
        }

        password.afterTextChanged {
            accountViewModel.loginDataChanged(phone.text.toString(), password.text.toString())
        }

        password.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    accountViewModel.doAccountAction(phone.text.toString(), password.text.toString())
            }
            false
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            accountViewModel.doAccountAction(phone.text.toString(), password.text.toString())
        }
    }

    private fun updateUiWithUser(model: UserData) {
        val welcome = getString(R.string.welcome)
        val displayName = model.username
        longToast("$welcome $displayName")
    }

    private fun showLoginFailed(errorString: String?) {
        errorString?.let { toast(errorString) }
    }
}
