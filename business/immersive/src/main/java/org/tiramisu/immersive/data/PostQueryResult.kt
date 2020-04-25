package org.tiramisu.immersive.data

import org.tiramisu.data.post.Post

data class PostQueryResult(
    val is_end: Boolean,
    val posts: List<Post>
) {
    override fun toString(): String {
        return "VideoQueryResult(is_end=$is_end, videos=${posts.size})"
    }
}