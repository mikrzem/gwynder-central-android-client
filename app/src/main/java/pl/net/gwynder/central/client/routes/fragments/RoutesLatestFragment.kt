package pl.net.gwynder.central.client.routes.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_routes_latest.*
import org.koin.android.ext.android.inject
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.routes.storage.access.StoredEventStorage
import pl.net.gwynder.central.client.utils.base.BaseFragment

class RoutesLatestFragment : BaseFragment() {

    private val storage: StoredEventStorage by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes_latest, container, false)
    }

    override fun onStart() {
        super.onStart()
        val adapter = RouteItemAdapter(this.context!!)
        eventList.adapter = adapter
        eventList.layoutManager = LinearLayoutManager(this.context)
        storage.selectLatest().observe(this, Observer { events ->
            adapter.submitList(events)
            adapter.notifyDataSetChanged()
        })
    }

}
