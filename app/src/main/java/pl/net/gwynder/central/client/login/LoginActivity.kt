package pl.net.gwynder.central.client.login

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.utils.base.BaseActivity
import pl.net.gwynder.central.client.utils.rest.RestClient

class LoginActivity : BaseActivity() {

    private var calls: Int = 0

    val rest: RestClient by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test_button.setOnClickListener {
            calls++
            test_label.text = calls.toString()
        }
    }
}
