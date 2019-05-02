package pl.net.gwynder.central.client.utils

import pl.net.gwynder.central.client.utils.base.BaseActivity

class ActivityContainer {

    private var activity: BaseActivity? = null

    fun started(activity: BaseActivity) {
        this.activity = activity
    }

    val current: BaseActivity
        get() {
            return activity ?: throw RuntimeException("Missing current activity")
        }

}