package com.shippedsuite.shippedshield

import android.os.Parcelable
import com.shippedsuite.shippedshield.exception.*
import com.shippedsuite.shippedshield.http.HttpClient
import com.shippedsuite.shippedshield.http.HttpRequest
import com.shippedsuite.shippedshield.http.HttpResponse
import com.shippedsuite.shippedshield.model.Options
import com.shippedsuite.shippedshield.model.ShieldOffer
import com.shippedsuite.shippedshield.model.ShieldRequest
import com.shippedsuite.shippedshield.model.ShieldModel
import com.shippedsuite.shippedshield.model.parser.ModelJsonParser
import com.shippedsuite.shippedshield.model.parser.ShieldOfferParser
import com.shippedsuite.shippedshield.model.parser.ShieldErrorParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import java.io.IOException
import java.net.HttpURLConnection
import java.util.*

internal class ShieldAPIRepository : APIRepository {

    private val httpClient: HttpClient = HttpClient()

    @Parcelize
    class ShieldRequestOptions constructor(
        val request: ShieldRequest
    ) : Options, Parcelable

    /**
    The request of getting shield fee.
     */
    override suspend fun getShieldFee(options: Options): ShieldOffer {
        val baseUrl: String = ShieldPlugins.environment.baseUrl()

        return executeApiRequest(
            HttpRequest.createPost(
                url = createShieldOffersUrl(baseUrl),
                params = (options as ShieldRequestOptions).request.toParamMap()
            ),
            ShieldOfferParser()
        )
    }

    @Throws(
        AuthenticationException::class,
        InvalidRequestException::class,
        PermissionException::class,
        APIException::class,
        APIConnectionException::class
    )
    private suspend fun <ModelType : ShieldModel> executeApiRequest(
        request: HttpRequest,
        jsonParser: ModelJsonParser<ModelType>
    ): ModelType = withContext(Dispatchers.IO) {
        val response = runCatching {
            httpClient.execute(request)
        }.getOrElse {
            throw when (it) {
                is IOException -> APIConnectionException.create(it, request.url)
                else -> it
            }
        }

        if (response.isError) {
            handleApiError(response)
        }

        jsonParser.parse(response.responseJson)
    }

    @Throws(
        AuthenticationException::class,
        InvalidRequestException::class,
        PermissionException::class,
        APIException::class
    )
    private fun handleApiError(response: HttpResponse) {
        val responseCode = response.code
        val error = ShieldErrorParser().parse(response.responseJson)
        when (responseCode) {
            HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_NOT_FOUND -> {
                throw InvalidRequestException(error = error)
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                throw AuthenticationException(error = error)
            }
            HttpURLConnection.HTTP_FORBIDDEN -> {
                throw PermissionException(error = error)
            }
            else -> {
                throw APIException(error = error, statusCode = responseCode)
            }
        }
    }

    companion object {
        internal fun createShieldOffersUrl(baseUrl: String): String {
            return getApiUrl(
                baseUrl,
                "v1/shield_offers"
            )
        }

        @Suppress("DEPRECATION")
        internal fun getApiUrl(baseUrl: String, path: String, vararg args: Any): String {
            return "$baseUrl/${String.format(Locale.ENGLISH, path, *args)}"
        }
    }
}
