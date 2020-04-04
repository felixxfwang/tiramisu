package org.tiramisu.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import org.tiramisu.page.modular.IActivityModularPage

abstract class BaseActivity: AppCompatActivity(), IActivityModularPage {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置沉浸式透明标题栏
        QMUIStatusBarHelper.translucent(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        modular.onActivityResult(requestCode, resultCode, data)
    }
}