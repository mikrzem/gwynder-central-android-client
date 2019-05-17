package pl.net.gwynder.central.client.routes.fragments

import android.content.Context
import android.text.format.DateFormat
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.net.gwynder.central.client.routes.storage.entities.StoredEvent
import java.util.*

class RouteItemViewHolder(val button: Button) : RecyclerView.ViewHolder(button)

private class StoredEventItemCallback : DiffUtil.ItemCallback<StoredEvent>() {

    override fun areItemsTheSame(oldItem: StoredEvent, newItem: StoredEvent): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: StoredEvent, newItem: StoredEvent): Boolean {
        return Objects.equals(oldItem, newItem)
    }

}

class RouteItemAdapter(
    private val context: Context
) : ListAdapter<StoredEvent, RouteItemViewHolder>(StoredEventItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteItemViewHolder {
        val button = Button(context)
        button.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return RouteItemViewHolder(button)
    }

    override fun onBindViewHolder(holder: RouteItemViewHolder, position: Int) {
        val event = getItem(position)
        holder.button.text = DateFormat.format("dd-MM-yyyy hh:mm:ss", event.createdAt)
        holder.button.setOnClickListener {
            // TODO
        }
    }

}