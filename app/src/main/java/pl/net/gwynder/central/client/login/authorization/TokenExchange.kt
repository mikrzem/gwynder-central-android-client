package pl.net.gwynder.central.client.login.authorization

import com.android.volley.Response
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.rest.Deliver
import pl.net.gwynder.central.client.utils.rest.RestClient

class TokenExchange(
    private val rest: RestClient,
    private val configuration: CentralConfiguration
) {

    fun checkHealth(deliver: Deliver<Boolean>) {
        rest.request("status", "alive")
            .get()
            .returnsString(
                { deliver(true) },
                Response.ErrorListener { deliver(false) }
            )
    }

    fun checkAuthorization(user: Deliver<String>, unauthorized: Deliver<String>) {
        if (configuration.authorizationToken == "") {
            unauthorized("Missing token")
        } else {
            rest.request("status", "authorization")
                .get()
                .returnsString(
                    { username -> user(username) },
                    Response.ErrorListener { unauthorized("Authorization failed") }
                )
        }
    }

}