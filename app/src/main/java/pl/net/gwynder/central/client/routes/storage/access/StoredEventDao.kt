package pl.net.gwynder.central.client.routes.storage.access

import androidx.room.Dao
import androidx.room.Insert
import pl.net.gwynder.central.client.routes.storage.entities.StoredEvent

@Dao
interface StoredEventDao {

    @Insert
    fun insert(event: StoredEvent): Long

}