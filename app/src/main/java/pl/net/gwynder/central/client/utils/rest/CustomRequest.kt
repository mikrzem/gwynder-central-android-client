package pl.net.gwynder.central.client.utils.rest

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import java.nio.charset.Charset

typealias Deliver<T> = (data: T) -> Unit

abstract class CustomRequest<T>(
    method: Int,
    url: String,
    errorListener: Response.ErrorListener,
    private val headersContent: MutableMap<String, String>,
    private val bodyContent: String?,
    private val onDeliver: Deliver<T>
) : Request<T>(method, url, errorListener) {

    override fun getHeaders(): MutableMap<String, String> {
        return if (headersContent.isEmpty()) {
            super.getHeaders()
        } else {
            headersContent
        }
    }

    override fun getBody(): ByteArray {
        return bodyContent?.toByteArray(Charsets.UTF_8) ?: super.getBody()
    }

    override fun getBodyContentType(): String {
        return if (bodyContent == null) {
            super.getBodyContentType()
        } else {
            "application/json; charset=UTF-8"
        }
    }

    override fun deliverResponse(response: T) {
        onDeliver(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return when {
            response == null -> Response.error(VolleyError("Missing network response"))
            response.statusCode in 200..299 -> Response.success(
                parseResponseContent(response.data, HttpHeaderParser.parseCharset(response.headers)),
                HttpHeaderParser.parseCacheHeaders(response)
            )
            else -> Response.error(VolleyError("Returned response with code: ${response.statusCode}"))
        }
    }

    abstract fun parseResponseContent(data: ByteArray?, charset: String): T
}

class StringCustomRequest(
    method: Int,
    url: String,
    errorListener: Response.ErrorListener,
    headersContent: MutableMap<String, String>,
    bodyContent: String?,
    onDeliver: Deliver<String>
) : CustomRequest<String>(method, url, errorListener, headersContent, bodyContent, onDeliver) {

    override fun parseResponseContent(data: ByteArray?, charset: String): String {
        return if (data == null) {
            ""
        } else {
            String(data, Charset.forName(charset))
        }
    }

}

class VoidCustomRequest(
    method: Int,
    url: String,
    errorListener: Response.ErrorListener,
    headersContent: MutableMap<String, String>,
    bodyContent: String?,
    onDeliver: Deliver<String>
) : CustomRequest<String>(method, url, errorListener, headersContent, bodyContent, onDeliver) {

    override fun parseResponseContent(data: ByteArray?, charset: String): String {
        // TODO log warning if data if not empty
        return ""
    }

}

class JsonCustomRequest<T>(
    method: Int,
    url: String,
    errorListener: Response.ErrorListener,
    headersContent: MutableMap<String, String>,
    bodyContent: String?,
    onDeliver: Deliver<T>,
    private val deserializer: DeserializationStrategy<T>
) : CustomRequest<T>(method, url, errorListener, headersContent, bodyContent, onDeliver) {

    override fun parseResponseContent(data: ByteArray?, charset: String): T {
        val text = if (data == null) {
            "{}"
        } else {
            String(data, Charset.forName(charset))
        }
        return Json.parse(
            deserializer,
            text
        )
    }

}