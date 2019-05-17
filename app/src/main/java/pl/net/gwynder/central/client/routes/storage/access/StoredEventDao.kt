package pl.net.gwynder.central.client.routes.storage.access

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.net.gwynder.central.client.routes.storage.entities.StoredEvent

@Dao
interface StoredEventDao {

    @Insert
    fun insert(event: StoredEvent): Long

    @Query("SELECT * FROM StoredEvent ORDER BY createdAt DESC LIMIT 5")
    fun selectLatest(): LiveData<List<StoredEvent>>

    @Query("SELECT * FROM StoredEvent WHERE id = :id LIMIT 1")
    fun get(id: Long): StoredEvent

    @Query("SELECT * FROM StoredEvent ORDER BY createdAt DESC")
    fun selectAll(): LiveData<List<StoredEvent>>

}