package al10101.android.flinder.ui

import al10101.android.flinder.CardStackAdapter
import al10101.android.flinder.CardStackCallback
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
import al10101.android.flinder.ItemModel
import al10101.android.flinder.R
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil

private const val TAG = "FlickrFragment"

class FlickrFragment : Fragment() {

    private lateinit var binding: FragmentFlickrBinding

    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter

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
                val text = when (direction) {
                    Direction.Right -> "right"
                    Direction.Left -> "left"
                    Direction.Bottom -> "bottom"
                    Direction.Top -> "top"
                }
                Toast.makeText(context, "Direction $text", Toast.LENGTH_SHORT).show()
                // Paginating
                if (manager.topPosition == adapter.itemCount - 5) {
                    paginate()
                }
            }

            override fun onCardRewound() {
                Log.d(TAG, "onCardRewound: p= ${manager.topPosition}")
            }

            override fun onCardCanceled() {
                Log.d(TAG, "onCardCanceled: p= ${manager.topPosition}")
            }

            override fun onCardAppeared(view: View, position: Int) {
                val tv = view.findViewById(R.id.item_name) as TextView
                Log.d(TAG, "onCardAppeared: $position  name= ${tv.text}")
            }

            override fun onCardDisappeared(view: View, position: Int) {
                val tv = view.findViewById(R.id.item_name) as TextView
                Log.d(TAG, "onCardDisappeared: $position  name= ${tv.text}")
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.FREEDOM)
        manager.setCanScrollHorizontal(true)
        manager.setSwipeableMethod(SwipeableMethod.Manual)
        manager.setOverlayInterpolator(LinearInterpolator())

        adapter = CardStackAdapter(addList())

        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator = DefaultItemAnimator()

    }

    private fun paginate() {
        val old = adapter.items
        val new: List<ItemModel> = ArrayList(addList())
        val callback = CardStackCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.items = new
        result.dispatchUpdatesTo(adapter)
    }

    private fun addList(): List<ItemModel> {
        val items: MutableList<ItemModel> = ArrayList()
        items.add(ItemModel(R.drawable.sample1, "Markonah", "24", "Jember"))
        items.add(ItemModel(R.drawable.sample1, "Marpuah", "20", "Malang"))
        items.add(ItemModel(R.drawable.sample1, "Sukijah", "27", "Jonggol"))
        items.add(ItemModel(R.drawable.sample1, "Markobar", "19", "Bandung"))
        items.add(ItemModel(R.drawable.sample1, "Marmut", "25", "Hutan"))
        items.add(ItemModel(R.drawable.sample1, "Markonah", "24", "Jember"))
        items.add(ItemModel(R.drawable.sample1, "Marpuah", "20", "Malang"))
        items.add(ItemModel(R.drawable.sample1, "Sukijah", "27", "Jonggol"))
        items.add(ItemModel(R.drawable.sample1, "Markobar", "19", "Bandung"))
        items.add(ItemModel(R.drawable.sample1, "Marmut", "25", "Hutan"))
        return items
    }

    companion object {
        fun newInstance() = FlickrFragment()
    }

}