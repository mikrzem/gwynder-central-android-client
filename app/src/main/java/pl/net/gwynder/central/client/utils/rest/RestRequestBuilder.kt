package pl.net.gwynder.central.client.utils.rest

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import pl.net.gwynder.central.client.utils.Feedback
import java.net.URLEncoder

class RestRequestBuilder(
    private val queue: RequestQueue,
    private val feedback: Feedback,
    private val requestUrl: String
) {

    private var method: Int = Request.Method.GET

    fun get(): RestRequestBuilder {
        method = Request.Method.GET
        return this
    }

    fun post(): RestRequestBuilder {
        method = Request.Method.POST
        return this
    }

    fun put(): RestRequestBuilder {
        method = Request.Method.PUT
        return this
    }

    fun delete(): RestRequestBuilder {
        method = Request.Method.DELETE
        return this
    }

    private val headers: MutableMap<String, String> = HashMap()

    fun header(key: String, value: String): RestRequestBuilder {
        if (value != "") {
            headers[key] = value
        }
        return this
    }

    private val params: MutableMap<String, String> = HashMap()

    fun param(key: String, value: String): RestRequestBuilder {
        if (value != "") {
            params[key] = value
        }
        return this
    }

    private var bodyContent: String? = null

    fun <T> body(value: T, serializer: SerializationStrategy<T>): RestRequestBuilder {
        bodyContent = Json.stringify(serializer, value)
        return this
    }

    private val url: String
        get() = if (params.isEmpty()) {
            requestUrl
        } else {
            val paramsList = params.map { (key, value) ->
                val encodedKey = URLEncoder.encode(key, "UTF-8")
                val encodedValue = URLEncoder.encode(value, "UTF-8")
                return "$encodedKey=$encodedValue"
            }
            val paramsValue = paramsList.joinToString("&")
            "$requestUrl?$paramsValue"
        }

    private fun defaultErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            feedback.error(error.message ?: "Request failed")
        }
    }

    fun returnsString(
        deliver: Deliver<String>,
        onError: Response.ErrorListener? = null
    ) {
        val request = StringCustomRequest(
            method,
            url,
            onError ?: defaultErrorListener(),
            headers,
            bodyContent,
            deliver
        )
        queue.add(request)
    }

    fun <T> returnsJson(
        deserializer: DeserializationStrategy<T>,
        deliver: Deliver<T>,
        onError: Response.ErrorListener? = null
    ) {
        val request = JsonCustomRequest(
            method,
            url,
            onError ?: defaultErrorListener(),
            headers,
            bodyContent,
            deliver,
            deserializer
        )
        queue.add(request)
    }

    fun returnsVoid(
        deliver: Deliver<String>,
        onError: Response.ErrorListener? = null
    ) {
        val request = VoidCustomRequest(
            method,
            url,
            onError ?: defaultErrorListener(),
            headers,
            bodyContent,
            deliver
        )
        queue.add(request)
    }
}