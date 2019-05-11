package pl.net.gwynder.central.client.routes

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_routes.*
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.routes.fragments.RoutesMainFragment
import pl.net.gwynder.central.client.routes.recording.LocationRecordingService
import pl.net.gwynder.central.client.routes.recording.LocationRecordingServiceConnection
import pl.net.gwynder.central.client.utils.NavigationSupport
import pl.net.gwynder.central.client.utils.base.BaseActivity

class RoutesActivity : BaseActivity() {

    private val requestPermissionCode = 65438

    private val recordingConnection: LocationRecordingServiceConnection by inject()

    private val navigation: NavigationSupport by inject()

    override fun loadingComponent(): View {
        return loading_container
    }

    override fun errorComponent(): TextView {
        return error_container
    }

    override fun contentComponent(): View {
        return content_container
    }

    override fun contentComponentId(): Int {
        return R.id.content_container
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)
        checkPermissions()
        navigation.show(RoutesMainFragment(), "Routes")
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, LocationRecordingService::class.java),
            recordingConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        if (recordingConnection.bound) {
            unbindService(recordingConnection)
            recordingConnection.bound = false
        }
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == requestPermissionCode) {

        }
    }

    private fun checkPermissions() {
        val permissionStatus = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestPermissionCode
            )
        }
    }
}
