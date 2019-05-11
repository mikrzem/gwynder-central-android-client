package pl.net.gwynder.central.client.routes.storage.access

import androidx.room.Dao
import androidx.room.Insert
import pl.net.gwynder.central.client.routes.storage.entities.StoredEventPoint

@Dao
interface StoredEventPointDao {

    @Insert
    fun insert(point: StoredEventPoint): Long

}