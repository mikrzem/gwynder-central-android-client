package pl.net.gwynder.central.client

import org.koin.dsl.module
import pl.net.gwynder.central.client.login.authorization.TokenExchange
import pl.net.gwynder.central.client.utils.ActivityContainer
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.Feedback
import pl.net.gwynder.central.client.utils.NavigationSupport
import pl.net.gwynder.central.client.utils.rest.RestClient

val centralModule = module {

    single { ActivityContainer() }
    single { NavigationSupport(get()) }
    single { Feedback(get(), get()) }
    single { CentralConfiguration(get()) }
    single { RestClient(get(), get(), get()) }
    single { TokenExchange(get(), get()) }

}