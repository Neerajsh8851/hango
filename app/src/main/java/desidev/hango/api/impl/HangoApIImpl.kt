package desidev.hango.api.impl

import desidev.hango.api.HangoApi
import desidev.hango.api.model.JwtToken
import desidev.hango.api.model.LocalDateTypeAdapter
import desidev.hango.api.model.UserCredentials
import desidev.hango.api.model.UserSignUp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.CancellationException
import java.lang.RuntimeException

class DefualtHangoApi(private val baseUrl: String) : HangoApi {

    private val client by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                gson {
                    registerTypeAdapter(LocalDateTypeAdapter::class.java, LocalDateTypeAdapter())
                }
            }
        }
    }

    override suspend fun userSignIn(payload: UserCredentials): Result<JwtToken> {
       try {
           val response = client.post("$baseUrl/user/sign-in") {
               contentType(ContentType.Application.Json)
               setBody(payload)
           }

           return if (response.status == HttpStatusCode.OK) {
               val jwtToken = response.body<JwtToken>()
               Result.success(jwtToken)
           } else {
               throw RuntimeException("Invalid credentials!")
           }

       } catch (ex: Exception) {
           if (ex is CancellationException) throw ex
           return Result.failure(ex)
       }
    }


    override suspend fun userSignUp(payload: UserSignUp): Result<Unit> {
        return try {
            val response = client.post("$baseUrl/user/sign-up") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }

            if (response.status == HttpStatusCode.OK) {
                Result.success(Unit)
            }  else {
                throw RuntimeException("Couldn't sign up'")
            }

        }catch (ex: Exception) {
            if (ex is CancellationException) throw ex
            return Result.failure(ex)
        }
    }
}