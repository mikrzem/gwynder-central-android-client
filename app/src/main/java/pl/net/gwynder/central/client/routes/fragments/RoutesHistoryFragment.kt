package pl.net.gwynder.central.client.routes.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.utils.base.BaseFragment

class RoutesHistoryFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes_history, container, false)
    }


}
