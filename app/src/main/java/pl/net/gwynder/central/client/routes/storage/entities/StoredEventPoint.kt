package pl.net.gwynder.central.client.routes.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = StoredEvent::class,
            parentColumns = ["id"],
            childColumns = ["event_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class StoredEventPoint(
    val pointTime: Date,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val precision: Double,
    @ColumnInfo(name = "event_id") val eventId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}