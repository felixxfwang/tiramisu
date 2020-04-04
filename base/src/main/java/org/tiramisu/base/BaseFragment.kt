package org.tiramisu.base

import androidx.fragment.app.Fragment
import org.tiramisu.page.modular.fragment.IFragmentModularPage

abstract class BaseFragment : Fragment(), IFragmentModularPage {

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        modular.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        modular.onSetUserVisibleHint(isVisibleToUser)
    }
}