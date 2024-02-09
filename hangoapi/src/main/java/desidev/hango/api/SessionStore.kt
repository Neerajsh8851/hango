package desidev.hango.api

import desidev.hango.api.model.SessionInfo
import desidev.hango.api.model.User
import desidev.kotlinutils.Option
import desidev.kotlinutils.enumValueOf
import io.objectbox.kotlin.awaitCallInTx
import io.objectbox.kotlin.boxFor
import java.io.File
import java.time.Instant
import java.time.ZoneId

internal interface SessionStore {
    suspend fun saveSession(session: SessionInfo)
    suspend fun getSession(): Option<SessionInfo>
    suspend fun clearSession()
}


internal class DefaultSessionStore(dir: File) : SessionStore {

    private val boxStore = MyObjectBox.builder()
        .baseDirectory(dir)
        .build()

    override suspend fun saveSession(session: SessionInfo) {
        with(boxStore) {
            awaitCallInTx {
                boxFor(SessionStoreEntity::class).put(session.mapToStoreEntity())
            }
        }
    }

    override suspend fun getSession(): Option<SessionInfo> {
        return with(boxStore) {
            awaitCallInTx {
                boxFor(SessionStoreEntity::class).all.firstOrNull()?.let {
                    Option.Some(it.mapToSessionInfo())
                }
            } ?: Option.None
        }
    }


    override suspend fun clearSession() {
        with(boxStore) {
            awaitCallInTx {
                boxFor(SessionStoreEntity::class).removeAll()
            }
        }
    }


    private fun SessionInfo.mapToStoreEntity(): SessionStoreEntity {
        return SessionStoreEntity(
            uid = user.id,
            token = sessionToken,
            name = user.name,
            birthDate = user.birthDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            gender = user.gender,
            email = user.email,
            profilePicture = user.profilePic,
            status = user.status.name,
            createdAt = user.createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    private fun SessionStoreEntity.mapToSessionInfo(): SessionInfo {
        return SessionInfo(
            user = User(
                id = uid,
                name = name,
                birthDate = java.time.LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                        birthDate
                    ), ZoneId.systemDefault()
                ),
                gender = gender,
                email = email,
                profilePic = profilePicture,
                status = enumValueOf<User.Status>(status)
                    ?: throw RuntimeException("Invalid status value"),
                createdAt = java.time.LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                        createdAt
                    ), ZoneId.systemDefault()
                )
            ),
            sessionToken = token
        )
    }
}