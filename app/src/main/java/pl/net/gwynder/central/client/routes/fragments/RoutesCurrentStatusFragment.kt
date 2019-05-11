package pl.net.gwynder.central.client.routes.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_routes_current_status.*
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.routes.recording.LocationRecordingServiceConnection
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.base.BaseFragment

class RoutesCurrentStatusFragment : BaseFragment() {

    private val configuration: CentralConfiguration by inject()

    private val connection: LocationRecordingServiceConnection by inject()

    private var subscription: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes_current_status, container, false)
    }

    override fun onStart() {
        super.onStart()
        updateStatus(configuration.requestingLocationUpdates)
        subscription = configuration.observeRequestingLocationUpdates.subscribe { status ->
            updateStatus(status)
        }
        start_recording.setOnClickListener {
            connection.service?.requestLocationUpdates()
        }
        stop_recording.setOnClickListener {
            connection.service?.removeLocationUpdates()
        }
    }

    override fun onStop() {
        super.onStop()
        subscription?.dispose()
    }

    private fun updateStatus(recording: Boolean) {
        recording_in_progress.visibility = boolToVisibility(recording)
        stop_recording.visibility = boolToVisibility(recording)
        start_recording.visibility = boolToVisibility(!recording)
    }

    private fun boolToVisibility(boolean: Boolean): Int {
        return if (boolean) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}
