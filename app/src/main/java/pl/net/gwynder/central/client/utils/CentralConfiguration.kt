package pl.net.gwynder.central.client.utils

import android.content.Context

class CentralConfiguration(
    private val context: Context
) {

    private val fileName = "pl.net.gwynder.central.client.CONFIGURATION"

    private val preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    private val authorizationTokenProperty = "authorizationToken"

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
}