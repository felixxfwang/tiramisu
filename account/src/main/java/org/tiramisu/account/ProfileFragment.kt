package org.tiramisu.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.startActivity
import org.tiramisu.account.login.LoginActivity
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
        avatar.setOnClickListener { requireActivity().startActivity<LoginActivity>() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
