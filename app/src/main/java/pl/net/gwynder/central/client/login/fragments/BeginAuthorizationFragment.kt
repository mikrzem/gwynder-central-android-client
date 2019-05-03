package pl.net.gwynder.central.client.login.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_begin_authorization.*
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.login.authorization.TokenExchange
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.NavigationSupport
import pl.net.gwynder.central.client.utils.base.BaseFragment

class BeginAuthorizationFragment : BaseFragment() {

    private val exchange: TokenExchange by inject()

    private val navigation: NavigationSupport by inject()

    private val configuration: CentralConfiguration by inject()

    override fun onStart() {
        super.onStart()
        confirm_code.setOnClickListener {
            exchange.confirmCode(request_code.text.toString()) { confirmation ->
                configuration.authorizationToken = confirmation.authorizationToken
                navigation.show(AuthorizationConfirmedFragment.newInstance(confirmation.code))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_begin_authorization, container, false)
    }
}
