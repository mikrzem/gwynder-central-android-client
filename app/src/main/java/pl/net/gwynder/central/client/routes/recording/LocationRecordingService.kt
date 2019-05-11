package pl.net.gwynder.central.client.routes.recording

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import androidx.core.app.NotificationCompat
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.routes.RoutesActivity
import pl.net.gwynder.central.client.routes.storage.access.StoredEventStorage
import pl.net.gwynder.central.client.utils.CentralConfiguration

const val UPDATE_INTERVAL_MS: Long = 2000

const val MAX_INTERVAL_MS: Long = UPDATE_INTERVAL_MS / 2

const val NOTIFICATION_ID = 353235234

const val CHANNEL_ID = "location_channel"

const val STARTED_FROM_NOTIFICATION = "pl.net.gwynder.central.client.STARTED_FROM_NOTIFICATION"

class LocationRecordingService : Service() {

    private val configuration: CentralConfiguration by inject()

    private var state: LocationRecordingState? = null

    private var changingConfiguration = false

    private val binder = LocalRecordingBinder(this)

    private val eventStorage: StoredEventStorage by inject()

    override fun onCreate() {
        state = LocationRecordingState(
            getSystemService(Context.LOCATION_SERVICE) as LocationManager,
            createCallback(),
            createServiceHandler(),
            createNotificationChannel()
        )
    }

    private fun createCallback(): LocationListener {
        return object : LocationListener {
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }

            override fun onLocationChanged(location: Location?) {
                if (location != null) {
                    onLocation(location)
                }
            }
        }
    }

    private fun onLocation(location: Location) {
        eventStorage.savePoint(
            location.latitude,
            location.longitude,
            location.altitude,
            location.accuracy.toDouble()
        )
        // TODO
    }

    private fun createServiceHandler(): Handler {
        val handler = HandlerThread("LocationRecordingService")
        handler.start()
        return Handler(handler.looper)
    }

    private fun createNotificationChannel(): NotificationManager {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val name = getString(R.string.app_name)
        val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        changingConfiguration = true
    }

    override fun onBind(intent: Intent?): IBinder? {
        stopForeground(true)
        changingConfiguration = false
        return binder
    }

    override fun onRebind(intent: Intent?) {
        stopForeground(true)
        changingConfiguration = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (!changingConfiguration && configuration.requestingLocationUpdates) {
            startForeground(NOTIFICATION_ID, createNotification())
        }
        return true
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, LocationRecordingService::class.java)
        val text = "Gwynder Central is recoding current user location"
        intent.putExtra(STARTED_FROM_NOTIFICATION, true)
        val serviceIntent = PendingIntent.getService(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val activityIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, RoutesActivity::class.java),
            0
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .addAction(
                R.drawable.ic_launch_black_24dp,
                getString(R.string.recording_launch_activity),
                activityIntent
            )
            .addAction(
                R.drawable.ic_cancel_black_24dp,
                getString(R.string.recording_cancel),
                serviceIntent
            )
            .setContentText(text)
            .setContentTitle("Recording location")
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker(text)
            .setWhen(System.currentTimeMillis())
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getBooleanExtra(STARTED_FROM_NOTIFICATION, false) == true) {
            removeLocationUpdates()
        }
        return START_NOT_STICKY

    }

    fun removeLocationUpdates() {
        try {
            val currentState = state
            if (currentState != null) {
                currentState.client.removeUpdates(currentState.listener)
                configuration.requestingLocationUpdates = false
                stopSelf()
            }
        } catch (ex: Exception) {
            configuration.requestingLocationUpdates = true
        }
    }

    fun requestLocationUpdates() {
        configuration.requestingLocationUpdates = true
        startService(Intent(applicationContext, LocationRecordingService::class.java))
        try {
            val currentState = state
            currentState?.client?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MAX_INTERVAL_MS,
                2.0f,
                currentState.listener,
                Looper.myLooper()
            )
        } catch (ex: SecurityException) {
            configuration.requestingLocationUpdates = false
        }
    }
}

class LocalRecordingBinder(val service: LocationRecordingService) : Binder()