package org.tiramisu.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

abstract class ContainerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportFragmentManager.commit {
            add(R.id.container, getFragment())
        }
    }

    abstract fun getFragment(): Fragment
}