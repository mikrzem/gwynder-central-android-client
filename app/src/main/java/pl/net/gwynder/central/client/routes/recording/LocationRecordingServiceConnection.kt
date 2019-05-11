package pl.net.gwynder.central.client.routes.recording

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class LocationRecordingServiceConnection : ServiceConnection {

    var bound: Boolean = false

    var service: LocationRecordingService? = null

    override fun onServiceDisconnected(name: ComponentName?) {
        service = null
        bound = false
    }

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        if (binder != null && binder is LocalRecordingBinder) {
            service = binder.service
            bound = true
        }
    }

}