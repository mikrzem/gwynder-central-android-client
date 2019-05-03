package pl.net.gwynder.central.client.login.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_authorization_confirmed.*
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.login.authorization.TokenExchange
import pl.net.gwynder.central.client.utils.base.BaseFragment

private const val RETURN_CODE = "return_code"

class AuthorizationConfirmedFragment : BaseFragment() {

    private var returnCode: String = ""

    private val exchange: TokenExchange by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            returnCode = it.getString(RETURN_CODE) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_authorization_confirmed, container, false)
    }

    override fun onStart() {
        super.onStart()
        response_code.text = returnCode
        continue_to_application.setOnClickListener {
            exchange.validateAuthorization(context!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(returnCode: String) =
            AuthorizationConfirmedFragment().apply {
                arguments = Bundle().apply {
                    putString(RETURN_CODE, returnCode)
                }
            }
    }
}
