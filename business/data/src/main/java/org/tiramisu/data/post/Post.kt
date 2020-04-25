package org.tiramisu.data.post

import org.tiramisu.data.user.User

class Post(
    val video: Video,
    val user: User,
    val like_count: Long,
    val comment_count: Long,
    val share_count: Long
)