package pl.net.gwynder.client.utils.rest

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import okhttp3.*
import pl.net.gwynder.client.utils.ConfigurationProvider

class RestClient(
    private val configuration: ConfigurationProvider
) {

    private val baseUrl = "https://gwynder.net.pl/api/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("ApplicationUserToken", configuration.authorizationToken)
                .build()
            chain.proceed(request)
        }
        .build()

    fun request(vararg urlParts: Any): RestRequest {
        return RestRequest(
            client,
            baseUrl + urlParts.joinToString("/") { part -> part.toString() })
    }

}


