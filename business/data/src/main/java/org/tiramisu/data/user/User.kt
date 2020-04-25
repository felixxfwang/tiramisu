package org.tiramisu.data.user

import android.os.Parcelable
import com.twitter.serial.serializer.ObjectSerializer
import com.twitter.serial.serializer.SerializationContext
import com.twitter.serial.stream.SerializerInput
import com.twitter.serial.stream.SerializerOutput
import kotlinx.android.parcel.Parcelize
import org.tiramisu.utils.extensions.toStringExt

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Parcelize
data class User(
    val userId: String,
    val username: String,
    var avatar: String,
    var signature: String
): Parcelable {
    companion object {
        val serializer = object : ObjectSerializer<User>() {
            override fun serializeObject(
                context: SerializationContext,
                output: SerializerOutput<out SerializerOutput<*>>,
                data: User
            ) {
                output.writeString(data.userId)
                    .writeString(data.username)
                    .writeString(data.avatar)
                    .writeString(data.signature)
            }

            override fun deserializeObject(
                context: SerializationContext,
                input: SerializerInput,
                versionNumber: Int
            ): User? {
                return User(
                    input.readString().toStringExt(),
                    input.readString().toStringExt(),
                    input.readString().toStringExt(),
                    input.readString().toStringExt()
                )
            }

        }
    }
}
