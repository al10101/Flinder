package al10101.android.flinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CardStackAdapter(var items: List<ItemModel>)
    : RecyclerView.Adapter<CardStackAdapter.CardStackHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_card, parent, false)
        return CardStackHolder(view)
    }

    override fun onBindViewHolder(holder: CardStackHolder, position: Int) {
        holder.setData(items[position])
    }

    override fun getItemCount() = items.size

    inner class CardStackHolder(view: View): RecyclerView.ViewHolder(view) {

        private val image = itemView.findViewById(R.id.item_image) as ImageView
        private val nameTextView = itemView.findViewById(R.id.item_name) as TextView
        private val ageTextView = itemView.findViewById(R.id.item_age) as TextView
        private val cityTextView = itemView.findViewById(R.id.item_city) as TextView

        fun setData(data: ItemModel) {

            Picasso.get()
                .load(data.image)
                .fit()
                .centerCrop()
                .into(image)
            nameTextView.text = data.name
            ageTextView.text = data.age
            cityTextView.text = data.city

        }

    }

}