package org.tiramisu.log

import android.util.Log

object TLog: ILogger {
    override fun v(tag: String, message: String) {
        Log.v(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun e(tag: String, message: String, tr: Throwable?) {
        Log.e(tag, message, tr)
    }
}