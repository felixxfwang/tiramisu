package org.tiramisu.account

import androidx.fragment.app.Fragment
import org.tiramisu.base.ContainerActivity

class ProfileActivity : ContainerActivity() {
    override fun getFragment(): Fragment = ProfileFragment()
}