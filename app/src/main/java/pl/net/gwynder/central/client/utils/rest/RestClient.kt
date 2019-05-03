package pl.net.gwynder.central.client.utils.rest

import android.content.Context
import com.android.volley.toolbox.Volley
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.Feedback

class RestClient(
    private val context: Context,
    private val feedback: Feedback,
    private val configuration: CentralConfiguration
) {
//    private val baseUrl = "https://gwynder.net.pl/api/" // server
    private val baseUrl = "http://10.0.0.68/api/" // local

    private val queue = Volley.newRequestQueue(context)

    fun request(vararg urlParts: String): RestRequestBuilder {
        val url = baseUrl + urlParts.joinToString("/")
        val builder = RestRequestBuilder(queue, feedback, url)
        val token = configuration.authorizationToken
        return if (token == "") {
            builder
        } else {
            builder.header("ApplicationUserToken", token)
        }
    }
}