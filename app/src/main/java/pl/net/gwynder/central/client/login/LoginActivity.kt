package pl.net.gwynder.central.client.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.login.authorization.TokenExchange
import pl.net.gwynder.central.client.utils.Feedback
import pl.net.gwynder.central.client.utils.base.BaseActivity

class LoginActivity : BaseActivity() {

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

    private val exchange: TokenExchange by inject()

    private val feedback: Feedback by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        feedback.loadingStarted()
        exchange.checkHealth { health ->
            if (health) {
                exchange.validateAuthorization(this)
            } else {
                feedback.error("Cannot connect to central server")
            }
        }
    }
}
