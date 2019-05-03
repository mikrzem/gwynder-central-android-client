package pl.net.gwynder.central.client.login.authorization

import android.content.Context
import android.content.Intent
import com.android.volley.Response
import pl.net.gwynder.central.client.login.fragments.BeginAuthorizationFragment
import pl.net.gwynder.central.client.routes.RoutesActivity
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.Feedback
import pl.net.gwynder.central.client.utils.NavigationSupport
import pl.net.gwynder.central.client.utils.rest.Deliver
import pl.net.gwynder.central.client.utils.rest.RestClient

class TokenExchange(
    private val rest: RestClient,
    private val configuration: CentralConfiguration,
    private val feedback: Feedback,
    private val navigation: NavigationSupport
) {

    fun checkHealth(deliver: Deliver<Boolean>) {
        rest.request("status", "alive")
            .get()
            .returnsString(
                { deliver(true) },
                Response.ErrorListener { deliver(false) }
            )
    }

    private fun checkAuthorization(user: Deliver<String>, unauthorized: Deliver<String>) {
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

    fun validateAuthorization(context: Context) {
        feedback.loadingStarted()
        checkAuthorization(
            { user ->
                feedback.info("Logged in as $user")
                context.startActivity(Intent(context, RoutesActivity::class.java))
            },
            {
                feedback.loadingFinished()
                navigation.show(BeginAuthorizationFragment())
            }
        )
    }

    private val applicationName = "Gwynder Central - android client"

    fun confirmCode(code: String, response: Deliver<CentralUserTokenConfirmed>) {
        val request = CentralUserTokenConfirmationRequest(
            code,
            applicationName
        )
        rest.request("user", "token", "confirmation")
            .post()
            .body(request, CentralUserTokenConfirmationRequest.serializer())
            .returnsJson(CentralUserTokenConfirmed.serializer(), response)
    }

}