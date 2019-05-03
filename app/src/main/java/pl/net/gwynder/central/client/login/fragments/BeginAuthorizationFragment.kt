package pl.net.gwynder.central.client.login.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_begin_authorization.*
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.login.authorization.TokenExchange
import pl.net.gwynder.central.client.utils.NavigationSupport
import pl.net.gwynder.central.client.utils.base.BaseFragment

class BeginAuthorizationFragment : BaseFragment() {

    val exchange: TokenExchange by inject()

    val navigation: NavigationSupport by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirm_code.setOnClickListener {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_begin_authorization, container, false)
    }
}
