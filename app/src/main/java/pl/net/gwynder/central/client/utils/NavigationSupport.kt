package pl.net.gwynder.central.client.utils

import pl.net.gwynder.central.client.utils.base.BaseFragment

class NavigationSupport(
    private val activity: ActivityContainer
) {

    fun show(fragment: BaseFragment, title: String = "") {
        with(activity.current.supportFragmentManager.beginTransaction()) {
            replace(activity.current.contentComponentId(), fragment)
            if (title != "") {
                addToBackStack(title)
            }
            commit()
        }
    }

}