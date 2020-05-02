package org.tiramisu.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.tiramisu.base.BaseFragment

class ProfileChannelFragment : BaseFragment() {

    override fun doOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile_channel, container, false)
    }
}
