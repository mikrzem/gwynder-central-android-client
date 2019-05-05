package pl.net.gwynder.central.client.utils.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.net.gwynder.central.client.routes.storage.access.StoredEventDao
import pl.net.gwynder.central.client.routes.storage.access.StoredEventPointDao
import pl.net.gwynder.central.client.routes.storage.entities.StoredEvent
import pl.net.gwynder.central.client.routes.storage.entities.StoredEventPoint

@Database(entities = [StoredEvent::class, StoredEventPoint::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class CentralDatabase : RoomDatabase() {

    abstract fun storedEvents(): StoredEventDao

    abstract fun storedEventPoints(): StoredEventPointDao

}