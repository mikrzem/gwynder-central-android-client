package pl.net.gwynder.central.client.utils.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.utils.ActivityContainer

abstract class BaseActivity : FragmentActivity() {

    val container: ActivityContainer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        container.started(this)
    }

    override fun onStart() {
        super.onStart()
        container.started(this)
    }

    abstract fun loadingComponent(): View

    abstract fun errorComponent(): TextView

    abstract fun contentComponent(): View

    abstract fun contentComponentId(): Int

}