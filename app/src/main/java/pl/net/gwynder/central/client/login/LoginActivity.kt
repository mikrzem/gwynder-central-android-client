package pl.net.gwynder.central.client.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pl.net.gwynder.central.client.R

class LoginActivity : AppCompatActivity() {

    private var calls: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test_button.setOnClickListener {
            calls++
            test_label.text = calls.toString()
        }
    }
}
