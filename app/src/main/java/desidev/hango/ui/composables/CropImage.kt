package desidev.hango.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.cropper.ImageCropper
import com.smarttoolfactory.cropper.model.OutlineType
import com.smarttoolfactory.cropper.model.RectCropShape
import com.smarttoolfactory.cropper.model.aspectRatios
import com.smarttoolfactory.cropper.settings.CropDefaults
import com.smarttoolfactory.cropper.settings.CropOutlineProperty

typealias ImageBitmapCallback = (ImageBitmap) -> Unit


@Composable
fun CropImage(
    isCrop: Boolean,
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap?,
    onCropImageResult: ImageBitmapCallback,
) {
    val handleSize: Float = LocalDensity.current.run { 20.dp.toPx() }

    val cropProperties by remember {
        mutableStateOf(
            CropDefaults.properties(
                contentScale = ContentScale.Inside,
                cropOutlineProperty = CropOutlineProperty(
                    OutlineType.Rect,
                    RectCropShape(0, "Rect"),
                ),
                aspectRatio = aspectRatios[3].aspectRatio,
                fixedAspectRatio = true,
                handleSize = handleSize,
                minDimension = IntSize(512, 512)
            ),
        )
    }
    val cropStyle by remember {
        mutableStateOf(
            CropDefaults.style(drawGrid = false, strokeWidth = 2.dp),
        )
    }

    imageBitmap?.let { image ->
        ImageCropper(
            modifier = modifier,
            imageBitmap = image,
            contentDescription = null,
            cropStyle = cropStyle,
            cropProperties = cropProperties,
            crop = isCrop,
            onCropStart = {},
            onCropSuccess = onCropImageResult,
        )
    }
}