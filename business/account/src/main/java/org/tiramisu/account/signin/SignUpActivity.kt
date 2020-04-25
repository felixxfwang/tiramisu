package org.tiramisu.account.signin

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_account.*
import org.tiramisu.account.R

class SignUpActivity : AccountActivity() {
    override fun getViewModelFactory() = AccountViewModelFactory("sign_up")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        login.setText(R.string.action_sign_up)
        register.visibility = View.GONE
    }
}