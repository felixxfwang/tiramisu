package org.tiramisu.image

import android.widget.ImageView

interface ImageLoadClient {
    fun load(context: Any, view: ImageView, imageUri: Any?, options: ImageOptions? = null)

    fun load(context: Any, imageUri: Any?, callback: ImageLoadCallback)

    fun load(context: Any, imageUri: Any?, callback: ImageLoadFunction)
}

class ImageOptions(
)
