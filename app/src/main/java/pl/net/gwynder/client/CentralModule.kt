package pl.net.gwynder.client

import org.koin.dsl.module
import pl.net.gwynder.client.utils.ConfigurationProvider
import pl.net.gwynder.client.utils.rest.RestClient

val centralModule = module {
    single { ConfigurationProvider(get()) }
    single { RestClient(get()) }
}