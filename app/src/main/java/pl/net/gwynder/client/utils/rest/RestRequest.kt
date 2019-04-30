package pl.net.gwynder.client.utils.rest

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class RestRequest(
    private val client: OkHttpClient,
    private val url: String
) {

    private var method: String = "GET"

    fun get(): RestRequest {
        method = "GET"
        return this
    }

    fun post(): RestRequest {
        method = "POST"
        return this
    }

    fun put(): RestRequest {
        method = "PUT"
        return this
    }

    fun delete(): RestRequest {
        method = "DELETE"
        return this
    }

    private var body: String? = null

    fun <T> body(content: T, serializer: SerializationStrategy<T>): RestRequest {
        body = Json.stringify(serializer, content)
        return this
    }

    private fun buildUrl(): String {
        return url
    }

    private fun buildBody(): RequestBody? {
        val currentBody = body
        return if (currentBody == null) {
            null
        } else {
            RequestBody.create(MediaType.get("application/json; charset=utf-8"), currentBody)
        }
    }

    private fun buildRequest(): Request {
        var builder = Request.Builder().url(buildUrl())
        builder = builder.method(method, buildBody())
        return builder.build()
    }

    fun <T> returns(serializer: DeserializationStrategy<T>): JsonRestRequestExecutor<T> {
        return JsonRestRequestExecutor(
            client,
            buildRequest(),
            serializer
        )
    }

    fun returnsString(): StringRestRequestExecutor {
        return StringRestRequestExecutor(client, buildRequest())
    }

    fun returnsVoid(): EmptyRestRequestExecutor {
        return EmptyRestRequestExecutor(client, buildRequest())
    }
}