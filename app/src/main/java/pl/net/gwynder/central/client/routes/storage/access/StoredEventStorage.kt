package pl.net.gwynder.central.client.routes.storage.access

import pl.net.gwynder.central.client.routes.storage.entities.StoredEvent
import pl.net.gwynder.central.client.routes.storage.entities.StoredEventPoint
import pl.net.gwynder.central.client.utils.CentralConfiguration
import pl.net.gwynder.central.client.utils.database.CentralDatabase
import java.util.*

class StoredEventStorage(
    database: CentralDatabase,
    private val configuration: CentralConfiguration
) {

    private val events: StoredEventDao = database.storedEvents()
    private val points: StoredEventPointDao = database.storedEventPoints()

    private val subscription = configuration.observeRequestingLocationUpdates.subscribe { recording ->
        if (!recording) {
            currentId = null
        }
    }

    private var currentId: Long? = null

    private fun current(): Long {
        return currentId ?: events.insert(StoredEvent(Date()))
    }

    fun savePoint(latitude: Double, longitude: Double, altitude: Double, precision: Double) {
        val point = StoredEventPoint(
            Date(),
            latitude,
            longitude,
            altitude,
            precision,
            current()
        )
        points.insert(point)
    }

}