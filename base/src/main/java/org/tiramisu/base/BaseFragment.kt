package org.tiramisu.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.tiramisu.page.modular.fragment.IFragmentModularPage

abstract class BaseFragment : Fragment(), IFragmentModularPage {

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return modular.onFragmentViewPreCreate(this) ?: doOnCreateView(inflater, container, savedInstanceState)
    }

    abstract fun doOnCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        modular.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        modular.onSetUserVisibleHint(isVisibleToUser)
    }
}