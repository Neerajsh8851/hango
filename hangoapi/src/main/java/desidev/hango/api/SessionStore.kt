package desidev.hango.api

import desidev.hango.api.model.LocalUser
import desidev.hango.api.model.SessionInfo
import desidev.kotlinutils.Option
import io.objectbox.kotlin.awaitCallInTx
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal interface SessionStore {
    suspend fun saveSession(session: SessionInfo)
    suspend fun getSession(): Option<SessionInfo>
    suspend fun clearSession()
}


internal class DefaultSessionStore private constructor(dir: File) : SessionStore {

    companion object {
        private var instance: DefaultSessionStore? = null

        fun init(dir: File) {
            if (instance == null) {
                instance = DefaultSessionStore(dir)
            }
        }

        fun getInstance(): DefaultSessionStore = synchronized(this) {
            instance ?: throw RuntimeException("SessionStore not initialized")
        }
    }


    private val boxStore = MyObjectBox.builder()
        .baseDirectory(dir)
        .build()

    override suspend fun saveSession(session: SessionInfo): Unit = withContext(Dispatchers.IO) {
        with(boxStore) {
            awaitCallInTx {
                boxFor(SessionStoreEntity::class).put(session.mapToStoreEntity())
            }
        }
    }

    override suspend fun getSession(): Option<SessionInfo> = withContext(Dispatchers.IO) {
        with(boxStore) {
            awaitCallInTx {
                boxFor(SessionStoreEntity::class).all.firstOrNull()?.let {
                    Option.Some(it.mapToSessionInfo())
                }
            } ?: Option.None
        }
    }


    override suspend fun clearSession(): Unit = withContext(Dispatchers.IO) {
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
            createdAt = user.createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    private fun SessionStoreEntity.mapToSessionInfo(): SessionInfo {
        return SessionInfo(
            user = LocalUser(
                id = uid,
                name = name,
                birthDate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                        birthDate
                    ), ZoneId.systemDefault()
                ),
                gender = gender,
                email = email,
                profilePic = profilePicture,
                createdAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                        createdAt
                    ), ZoneId.systemDefault()
                )
            ),
            sessionToken = token
        )
    }
}