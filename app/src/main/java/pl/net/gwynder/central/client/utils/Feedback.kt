package pl.net.gwynder.central.client.utils

import android.content.Context
import android.widget.Toast

class Feedback(
    private val context: Context
) {

    fun error(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}