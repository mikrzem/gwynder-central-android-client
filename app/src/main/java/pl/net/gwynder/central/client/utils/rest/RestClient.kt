package pl.net.gwynder.central.client.utils.rest

import android.content.Context
import com.android.volley.toolbox.Volley
import pl.net.gwynder.central.client.utils.Feedback

class RestClient(
    private val context: Context,
    private val feedback: Feedback
) {
    private val baseUrl = "https://gwynder.net.pl/api/"

    private val queue = Volley.newRequestQueue(context)

    fun request(vararg urlParts: String) : RestRequestBuilder {
        val url = baseUrl + urlParts.joinToString("/")
        return RestRequestBuilder(queue, feedback, url)
    }
}