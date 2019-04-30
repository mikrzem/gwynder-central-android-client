package pl.net.gwynder.client.utils.rest

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

class RestResponseError(val code: Int) : RuntimeException("HTTP response code: $code")

class RestResponseMissingBody() : RuntimeException("Rest call missing it's response body")

abstract class AbstractRestRequestExecutor<T>(
    private val client: OkHttpClient,
    private val request: Request
) {

    fun execute(): Observable<T> {
        return Observable.create<T> { subscriber ->
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        subscriber.onNext(parseResponseBody(body))
                    } else {
                        subscriber.onNext(onEmptyBody())
                    }
                } else {
                    subscriber.onError(RestResponseError(response.code()))
                }
            } catch (error: Exception) {
                if (!subscriber.isDisposed) {
                    subscriber.onError(error)
                }
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    protected abstract fun parseResponseBody(body: ResponseBody): T

    protected abstract fun onEmptyBody(): T

}

class StringRestRequestExecutor(
    client: OkHttpClient,
    request: Request
) : AbstractRestRequestExecutor<String>(client, request) {

    override fun parseResponseBody(body: ResponseBody): String {
        return body.string()
    }

    override fun onEmptyBody(): String {
        return ""
    }

}

class JsonRestRequestExecutor<T>(
    client: OkHttpClient,
    request: Request,
    private val serializer: DeserializationStrategy<T>
) : AbstractRestRequestExecutor<T>(client, request) {

    override fun parseResponseBody(body: ResponseBody): T {
        return Json.parse(serializer, body.string())
    }

    override fun onEmptyBody(): T {
        throw RestResponseMissingBody()
    }
}

class EmptyRestRequestExecutor(
    client: OkHttpClient,
    request: Request
) : AbstractRestRequestExecutor<String>(client, request) {

    override fun parseResponseBody(body: ResponseBody): String {
        // TODO: add logging to warn against unexpected result
        return ""
    }

    override fun onEmptyBody(): String {
        return ""
    }


}