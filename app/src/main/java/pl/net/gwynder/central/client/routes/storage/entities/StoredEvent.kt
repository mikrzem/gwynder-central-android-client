package pl.net.gwynder.central.client.routes.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class StoredEvent(
    val createdAt: Date
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}