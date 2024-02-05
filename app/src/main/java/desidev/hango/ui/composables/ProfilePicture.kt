package desidev.hango.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Preview(apiLevel = 33)
@Composable
fun ProfilePicPreview() {
    ProfilePicture(
        imageUrl = "https://avatars.githubusercontent.com/u/1845596?v=4",
        modifier = Modifier.background(color = Color.Gray)
    )
}

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(166.dp)
            .aspectRatio(9 / 16f)
            .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.large)
            .padding(4.dp)
            .clip(MaterialTheme.shapes.large)
            .then(modifier)
    )
}

