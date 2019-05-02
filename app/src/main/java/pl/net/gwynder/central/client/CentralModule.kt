package pl.net.gwynder.central.client

import org.koin.dsl.module
import pl.net.gwynder.central.client.utils.ActivityContainer
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.Feedback
import pl.net.gwynder.central.client.utils.rest.RestClient

val centralModule = module {

    single { ActivityContainer() }
    single { Feedback(get()) }
    single { CentralConfiguration(get()) }
    single { RestClient(get(), get(), get()) }

}