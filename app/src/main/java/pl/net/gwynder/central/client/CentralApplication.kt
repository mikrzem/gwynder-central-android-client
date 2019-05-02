package pl.net.gwynder.central.client

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CentralApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CentralApplication)
            modules(centralModule)
        }
    }

}