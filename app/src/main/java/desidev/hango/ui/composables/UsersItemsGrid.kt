package desidev.hango.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import desidev.hango.api.model.User

@Composable
fun UserItemsGrid(users: List<User>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users.size) { UserItems(user = users[it]) }
    }
}


@Composable
fun UserItems(modifier: Modifier = Modifier, user: User) {
    val statusColorIndication = when (user.status) {
        User.Status.ACTIVE -> Color(0xFF4CAF50)
        User.Status.INACTIVE -> Color(0xFFFF9800)
        User.Status.BUSY -> Color(0xFFF44336)
    }

    @Composable
    fun StatusIndicator() {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(
                    statusColorIndication,
                    shape = CircleShape
                )
        )
    }

    @Composable
    fun BoxScope.UserInfo() {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = user.name,
                color = Color.White,
            )

            Text(
                text = user.gender,
                color = Color.White,
            )
        }
    }

    Card(modifier) {
        Box {
            AsyncImage(
                model = user.profilePic,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(9 / 16.0f)
                    .drawScrimOverlay()
            )
            StatusIndicator()
            UserInfo()

            // call icon button
            FilledIconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                enabled = user.status == User.Status.ACTIVE
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
fun Modifier.drawScrimOverlay(): Modifier {
    return drawWithContent {
        drawContent()
        // Define the gradient colors
        val topColor = Color.Transparent
        val bottomColor = Color.Black.copy(alpha = 0.5f)

        // Draw the gradient rectangle
        drawRect(
            brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0.4f to topColor,
                    1.0f to bottomColor
                )
            ),
            // Ensure overlay covers entire image
            topLeft = Offset.Zero,
            size = size
        )
    }
}