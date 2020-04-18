package org.tiramisu.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.tiramisu.account.Constants.REQUEST_CODE_SIGN_IN
import org.tiramisu.account.signin.SignInActivity
import org.tiramisu.base.BaseFragment
import org.tiramisu.image.with

class ProfileFragment : BaseFragment() {

    companion object {
    }

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUserData()
    }

    override fun doOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.userData.observe(viewLifecycleOwner) {
            username.text = it.username
            signature.text = it.signature
            avatar.with(this).load(it.avatar)
        }
        avatar.setOnClickListener {
            if (!viewModel.isSignedIn()) {
                requireActivity().startActivityForResult<SignInActivity>(REQUEST_CODE_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN && resultCode == RESULT_OK) {
            viewModel.onSignedIn(data)
        }
    }
}
