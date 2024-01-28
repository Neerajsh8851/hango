package desidev.hango.ui.screens.photocrop

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import desidev.hango.ui.composables.CropImage
import desidev.hango.ui.composables.rememberValueAsState

@Composable
fun PhotoCropContent(modifier: Modifier = Modifier, component: PhotoCropComponent) {
    var crop by rememberValueAsState(value = false)
    val localContext = LocalContext.current
    val imageBitmap = remember {
        localContext.contentResolver.openInputStream(component.imageUri).use {
            BitmapFactory.decodeStream(it).asImageBitmap()
        }
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = { crop = true }) {
                Icon(imageVector = Icons.Rounded.Done, contentDescription = "Crop")
            }
        },
        content = { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                CropImage(
                    isCrop = crop,
                    imageBitmap = imageBitmap,
                    onCropImageResult = {
                        component.onCropImageResult(it)
                    }
                )
            }
        }
    )
}