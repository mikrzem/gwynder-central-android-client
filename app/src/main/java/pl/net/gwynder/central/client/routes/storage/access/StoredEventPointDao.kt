package pl.net.gwynder.central.client.routes.storage.access

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.net.gwynder.central.client.routes.storage.entities.StoredEventPoint

@Dao
interface StoredEventPointDao {

    @Insert
    fun insert(point: StoredEventPoint): Long

    @Query("SELECT * FROM StoredEventPoint WHERE event_id = :eventId ORDER BY pointTime")
    fun select(eventId: Long): LiveData<List<StoredEventPoint>>

    @Query("SELECT COUNT(1) FROM StoredEventPoint WHERE event_id = :eventId")
    fun count(eventId: Long): Long

}