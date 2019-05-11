package pl.net.gwynder.central.client

import androidx.room.Room
import org.koin.dsl.module
import pl.net.gwynder.central.client.login.authorization.TokenExchange
import pl.net.gwynder.central.client.routes.recording.LocationRecordingServiceConnection
import pl.net.gwynder.central.client.routes.storage.access.StoredEventStorage
import pl.net.gwynder.central.client.utils.ActivityContainer
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.Feedback
import pl.net.gwynder.central.client.utils.NavigationSupport
import pl.net.gwynder.central.client.utils.database.CentralDatabase
import pl.net.gwynder.central.client.utils.rest.RestClient

val centralModule = module {

    single { ActivityContainer() }
    single { NavigationSupport(get()) }
    single { Feedback(get(), get()) }
    single { CentralConfiguration(get()) }
    single { RestClient(get(), get(), get()) }
    single { TokenExchange(get(), get(), get(), get()) }
    single {
        Room.databaseBuilder(
            get(),
            CentralDatabase::class.java, "gwynder-central"
        )
            .allowMainThreadQueries()
            .build()
    }

    single { LocationRecordingServiceConnection() }
    single { StoredEventStorage(get(), get()) }

}