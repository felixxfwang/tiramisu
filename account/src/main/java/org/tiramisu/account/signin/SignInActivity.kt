package org.tiramisu.account.signin

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_account.*
import org.jetbrains.anko.startActivityForResult

class SignInActivity : AccountActivity() {

    companion object {
        const val REQUEST_CODE_SIGN_UP = 10000
    }

    override fun getViewModelFactory() = AccountViewModelFactory("sign_in")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        register.setOnClickListener { startActivityForResult<SignUpActivity>(REQUEST_CODE_SIGN_UP) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_UP && resultCode == RESULT_OK) {
            finish()
        }
    }
}