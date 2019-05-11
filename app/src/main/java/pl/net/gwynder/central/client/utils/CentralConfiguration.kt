package pl.net.gwynder.central.client.utils

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class CentralConfiguration(
    context: Context
) {

    private val fileName = "pl.net.gwynder.central.client.CONFIGURATION"

    private val preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    private val authorizationTokenProperty = "authorizationToken"
    private val requestingLocationUpdatesProperty = "requestingLocationUpdates"

    var authorizationToken: String
        get() {
            return preferences.getString(authorizationTokenProperty, "") ?: ""
        }
        set(value) {
            with(preferences.edit()) {
                putString(authorizationTokenProperty, value)
                apply()
            }
        }

    var requestingLocationUpdates: Boolean
        get() {
            return preferences.getBoolean(requestingLocationUpdatesProperty, false)
        }
        set(value) {
            with(preferences.edit()) {
                putBoolean(requestingLocationUpdatesProperty, value)
                apply()
            }
        }

    val observeRequestingLocationUpdates: Observable<Boolean> = Observable.create<Boolean> { emitter ->
        preferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == requestingLocationUpdatesProperty) {
                emitter.onNext(requestingLocationUpdates)
            }
        }
    }.subscribeOn(AndroidSchedulers.mainThread())

}