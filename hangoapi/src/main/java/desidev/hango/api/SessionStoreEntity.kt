package desidev.hango.api

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class SessionStoreEntity(
    @Id var id: Long = 0,
    var token: String,
    var uid: String,
    var name: String,
    var birthDate: Long,
    var gender: String,
    var profilePicture: String?,
    var email: String,
    var createdAt: Long,
    var status: String
)