package org.tiramisu.page.modular

import android.content.Intent
import android.os.Bundle

interface IActivityModule : IPageModule {

    fun onCreate(savedInstanceState: Bundle?) {}

    fun onStart() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}