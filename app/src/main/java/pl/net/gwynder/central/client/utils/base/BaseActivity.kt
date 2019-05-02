package pl.net.gwynder.central.client.utils.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.utils.ActivityContainer

abstract class BaseActivity : AppCompatActivity() {

    val container: ActivityContainer by inject()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        container.started(this)
    }

    override fun onStart() {
        super.onStart()
        container.started(this)
    }

}