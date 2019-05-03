package pl.net.gwynder.central.client.login.authorization

class CentralUserTokenConfirmationRequest(
        val code: String,
        val applicationName: String
)