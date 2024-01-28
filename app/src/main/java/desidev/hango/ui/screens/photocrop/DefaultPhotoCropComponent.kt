package desidev.hango.ui.screens.photocrop

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext

typealias OnImageCropped = (bitmap: ImageBitmap) -> Unit

class DefaultPhotoCropComponent(
    componentContext: ComponentContext,
    override val imageUri: Uri,
    val onPhotoCrop: OnImageCropped
): PhotoCropComponent, ComponentContext by componentContext {
    override fun onCropImageResult(image: ImageBitmap) {
        onPhotoCrop(image)
    }
}