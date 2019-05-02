package pl.net.gwynder.central.client

import org.koin.dsl.module
import pl.net.gwynder.central.client.utils.Feedback
import pl.net.gwynder.central.client.utils.rest.RestClient

val centralModule = module {

    single { Feedback(get()) }
    single { RestClient(get(), get()) }

}