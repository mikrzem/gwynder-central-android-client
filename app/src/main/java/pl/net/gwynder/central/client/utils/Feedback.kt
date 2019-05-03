package pl.net.gwynder.central.client.utils

import android.content.Context
import android.view.View
import android.widget.Toast

class Feedback(
    private val context: Context,
    private val activity: ActivityContainer
) {

    fun error(message: String) {
        activity.current.contentComponent().visibility = View.GONE
        activity.current.loadingComponent().visibility = View.GONE
        activity.current.errorComponent().visibility = View.VISIBLE
        activity.current.errorComponent().text = message
    }

    fun loadingStarted() {
        activity.current.contentComponent().visibility = View.GONE
        activity.current.errorComponent().visibility = View.GONE
        activity.current.loadingComponent().visibility = View.VISIBLE
    }

    fun loadingFinished() {
        activity.current.loadingComponent().visibility = View.GONE
        activity.current.errorComponent().visibility = View.GONE
        activity.current.contentComponent().visibility = View.VISIBLE
    }

    fun info(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}