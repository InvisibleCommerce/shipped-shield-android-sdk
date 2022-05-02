package com.shippedsuite.shippedshield.http

import com.shippedsuite.shippedshield.exception.APIConnectionException
import com.shippedsuite.shippedshield.exception.InvalidRequestException
import com.shippedsuite.shippedshield.log.Logger
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection

class HttpClient {

    @Throws(IOException::class, InvalidRequestException::class)
    fun execute(request: HttpRequest): HttpResponse {
        Logger.info(request.toString())
        HttpConnection(
            (URL(request.url).openConnection() as HttpsURLConnection).apply {
                connectTimeout = CONNECT_TIMEOUT
                readTimeout = READ_TIMEOUT
                useCaches = false
                requestMethod = request.method.code

                request.headers.forEach { (key, value) ->
                    setRequestProperty(key, value)
                }

                if (HttpRequest.Method.POST == request.method) {
                    doOutput = true
                    setRequestProperty(HEADER_CONTENT_TYPE, request.contentType)
                    outputStream.use { output -> request.writeBody(output) }
                }
            }
        ).use {
            try {
                val response = it.response
                Logger.info(response.toString())
                return response
            } catch (e: IOException) {
                throw APIConnectionException.create(e, request.url)
            }
        }
    }

    private companion object {
        private val CONNECT_TIMEOUT = TimeUnit.SECONDS.toMillis(300).toInt()
        private val READ_TIMEOUT = TimeUnit.SECONDS.toMillis(800).toInt()

        private const val HEADER_CONTENT_TYPE = "Content-Type"
    }
}