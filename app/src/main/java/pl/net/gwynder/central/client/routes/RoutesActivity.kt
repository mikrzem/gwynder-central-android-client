package pl.net.gwynder.central.client.routes

import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_routes.*
import pl.net.gwynder.central.client.R
import pl.net.gwynder.central.client.utils.base.BaseActivity

class RoutesActivity : BaseActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)
    }
}
