package pl.net.gwynder.central.client.login.authorization

import kotlinx.serialization.Serializable

@Serializable
data class CentralUserTokenConfirmed(
        val code: String,
        val authorizationToken: String
)