package desidev.hango.ui.screens.photocrop

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

interface PhotoCropComponent {
    val imageUri: Uri
    fun onCropImageResult(image: ImageBitmap)
}