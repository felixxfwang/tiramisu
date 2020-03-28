package org.tiramisu.image

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

val imageClient: ImageLoadClient = GlideImageClient()

class ImageRequest internal constructor(
    private val context: Any,
    private val view: ImageView
){
    private var options: ImageOptions? = null

    fun options(options: ImageOptions): ImageRequest {
        this.options = options
        return this
    }

    fun load(imageUri: Any?) {
        imageClient.load(context, view, imageUri, options)
    }
}

fun ImageView.with(fragment: Fragment) = ImageRequest(fragment, this)

fun ImageView.with(activity: FragmentActivity) = ImageRequest(activity, this)

fun ImageView.with(activity: Activity) = ImageRequest(activity, this)

fun ImageView.with(context: Context) = ImageRequest(context, this)