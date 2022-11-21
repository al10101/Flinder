package al10101.android.flinder.api

import al10101.android.flinder.ItemModel
import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo") lateinit var galleryItems: List<ItemModel>
}