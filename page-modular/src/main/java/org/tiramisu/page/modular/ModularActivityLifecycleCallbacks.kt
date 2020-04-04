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
        (activity as? IActivityModularPage<*>)?.modular?.onCreate(savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
        (activity as? IActivityModularPage<*>)?.modular?.onStart()
    }

    override fun onActivityResumed(activity: Activity) {
        (activity as? IActivityModularPage<*>)?.modular?.onResume()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityPaused(activity: Activity) {
        (activity as? IActivityModularPage<*>)?.modular?.onPause()
    }

    override fun onActivityStopped(activity: Activity) {
        (activity as? IActivityModularPage<*>)?.modular?.onStop()
    }

    override fun onActivityDestroyed(activity: Activity) {
        (activity as? IActivityModularPage<*>)?.modular?.onDestroy()
    }

    override fun onActivityPostDestroyed(activity: Activity) {
        (activity as? FragmentActivity)?.supportFragmentManager
            ?.unregisterFragmentLifecycleCallbacks(ModularFragmentLifecycleCallback)
    }
}