package pl.net.gwynder.central.client.login.authorization

import kotlinx.serialization.Serializable

@Serializable
data class CentralUserTokenConfirmationRequest(
        val code: String,
        val applicationName: String
)