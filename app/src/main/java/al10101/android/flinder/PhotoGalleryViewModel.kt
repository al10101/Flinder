package al10101.android.flinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PhotoGalleryViewModel: ViewModel() {

    val galleryItemLiveData: LiveData<List<ItemModel>>

    init {
        galleryItemLiveData = FlickrFetcher().fetchPhotos()
    }

}