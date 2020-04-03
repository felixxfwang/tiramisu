package org.tiramisu.page.modular

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

object ModularActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? FragmentActivity)?.supportFragmentManager
            ?.registerFragmentLifecycleCallbacks(ModularFragmentLifecycleCallback, true)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    override fun onActivityPostDestroyed(activity: Activity) {
        (activity as? FragmentActivity)?.supportFragmentManager
            ?.unregisterFragmentLifecycleCallbacks(ModularFragmentLifecycleCallback)
    }
}