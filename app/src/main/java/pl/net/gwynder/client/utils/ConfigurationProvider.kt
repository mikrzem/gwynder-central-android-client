package pl.net.gwynder.client.utils

import android.content.Context

class ConfigurationProvider(
    private val context: Context
) {

    private val authorizationTokenKey = "authorization-token"

    private val preferences =
        context.getSharedPreferences("pl.net.gwynder.gwynder_central_android_client.ROOT", Context.MODE_PRIVATE)

    var authorizationToken: String
        get() {
            return preferences.getString(authorizationTokenKey, "") ?: ""
        }
        set(value) {
            with(preferences.edit()) {
                putString(authorizationTokenKey, value)
                apply()
            }
        }
}