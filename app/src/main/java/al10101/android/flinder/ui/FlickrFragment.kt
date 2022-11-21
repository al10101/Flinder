package al10101.android.flinder.ui

import al10101.android.flinder.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import al10101.android.flinder.databinding.FragmentFlickrBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import androidx.recyclerview.widget.DefaultItemAnimator
import android.view.animation.LinearInterpolator
import com.yuyakaido.android.cardstackview.SwipeableMethod
import com.yuyakaido.android.cardstackview.StackFrom
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val TAG = "FlickrFragment"

class FlickrFragment : Fragment() {

    private lateinit var binding: FragmentFlickrBinding

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel

    private lateinit var manager: CardStackLayoutManager
    private var adapter: CardStackAdapter? = CardStackAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoGalleryViewModel = ViewModelProvider(this).get(PhotoGalleryViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentFlickrBinding.inflate(inflater, container, false)

        manager = CardStackLayoutManager(context, object: CardStackListener {

            override fun onCardDragging(direction: Direction, ratio: Float) {
                Log.d(TAG, "onCardDragging: d= ${direction.name}  ratio= $ratio")
            }

            override fun onCardSwiped(direction: Direction) {
                Log.d(TAG, "onCardSwiped: p= ${manager.topPosition}  d= ${direction.name}")
            }

            override fun onCardRewound() {
                Log.d(TAG, "onCardRewound: p= ${manager.topPosition}")
            }

            override fun onCardCanceled() {
                Log.d(TAG, "onCardCanceled: p= ${manager.topPosition}")
            }

            override fun onCardAppeared(view: View, position: Int) {
                val tv = view.findViewById(R.id.item_title) as TextView
                Log.d(TAG, "onCardAppeared: $position  name= ${tv.text}")
            }

            override fun onCardDisappeared(view: View, position: Int) {
                val tv = view.findViewById(R.id.item_title) as TextView
                Log.d(TAG, "onCardDisappeared: $position  name= ${tv.text}")
            }

        })

        manager.apply {
            setStackFrom(StackFrom.None)
            setVisibleCount(3)
            setTranslationInterval(8.0f)
            setScaleInterval(0.95f)
            setSwipeThreshold(0.3f)
            setMaxDegree(20.0f)
            setDirections(Direction.FREEDOM)
            setCanScrollHorizontal(true)
            setSwipeableMethod(SwipeableMethod.Manual)
            setOverlayInterpolator(LinearInterpolator())
        }

        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator = DefaultItemAnimator()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner, { galleryItems ->
                Log.d(TAG, "Have gallery items from ViewModel $galleryItems")
                updateUI(galleryItems)
            }
        )

    }

    private fun updateUI(items: List<ItemModel>) {
        adapter = CardStackAdapter(items)
        binding.cardStackView.adapter = adapter
    }

    private inner class CardStackAdapter(var items: List<ItemModel>)
        : RecyclerView.Adapter<CardStackHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_card, parent, false)
            return CardStackHolder(view)
        }

        override fun onBindViewHolder(holder: CardStackHolder, position: Int) {
            val galleryItem = items[position]
            holder.bindDrawable(galleryItem)
        }

        override fun getItemCount() = items.size

    }

    private class CardStackHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageView = itemView.findViewById(R.id.item_image) as ImageView
        private val titleTextView = itemView.findViewById(R.id.item_title) as TextView
        private val ownerTextView = itemView.findViewById(R.id.item_owner) as TextView
        private val urlTextView = itemView.findViewById(R.id.item_url) as TextView

        fun bindDrawable(data: ItemModel) {
            Picasso.get()
                .load(data.url)
                .into(imageView)
            titleTextView.text = data.title
            ownerTextView.text = data.owner
            urlTextView.text = data.url
        }

    }

    companion object {
        fun newInstance() = FlickrFragment()
    }

}