package al10101.android.flinder

import com.google.gson.annotations.SerializedName

data class ItemModel(
    var title: String = "",
    var id: String = "",
    var owner: String = "",
    @SerializedName("url_s") var url: String = "",
)