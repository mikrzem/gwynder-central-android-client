package pl.net.gwynder.central.client.routes.recording

import android.app.NotificationManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Handler

class LocationRecordingState(
    val client: LocationManager,
    val listener: LocationListener,
    val serviceHandler: Handler,
    val notificationManager: NotificationManager
)