package desidev.hango.ui.screens.signup_process.profile

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import desidev.hango.R
import desidev.hango.model.Gender
import desidev.hango.ui.composables.DateOfBirthInput
import desidev.hango.ui.theme.AppTheme
import java.io.ByteArrayOutputStream
import kotlin.math.cos
import kotlin.math.sin


@Preview
@Composable
fun ProfileContentPreview() {
    AppTheme {
        Surface {
            ProfileContent(
                component = remember { FakeProfileComponent() }, modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ProfileContent(component: ProfileComponent, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val profilePicState by component.profilePic.subscribeAsState()
    var openPhotoPicker by remember { mutableStateOf(false) }

    if (openPhotoPicker) {
        PhotoPickerResult(
            onPhotoSelected = { uri: Uri ->
                Log.d("ProfileContent", "Photo selected${uri.path}")
                val imageData = ByteArrayOutputStream(1024)
                context.contentResolver.openInputStream(uri)?.use { stream ->
//                    val buffer = ByteArray(1024)
//                    var len = stream.read(buffer)
//                    while (len > 0) {
//                        imageData.write(buffer, 0, len)
//                        len = stream.read(buffer)
//                    }
                    val bitmap = BitmapFactory.decodeStream(stream)
                }

            }, onDismiss = {
                openPhotoPicker = false
            }
        )
    }

    ConstraintLayout(
        modifier = modifier,
    ) {
        val (headingTxt, inputs, saveBtn, profilePic) = createRefs()
        Text(
            text = "Create Profile",
            modifier = Modifier
                .height(56.dp)
                .wrapContentSize()
                .constrainAs(headingTxt) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                },
            style = typography.headlineMedium.copy(color = colorScheme.onSurface)
        )

        ProfilePic(
            modifier = Modifier.constrainAs(profilePic) {
                centerHorizontallyTo(parent)
                top.linkTo(headingTxt.bottom)
                bottom.linkTo(inputs.top)
            },
            profilePic = profilePicState,
            onPhotoEditClick = { openPhotoPicker = true }
        )

        InputFields(component = component, modifier = Modifier.constrainAs(inputs) {
            centerHorizontallyTo(parent)
            centerVerticallyTo(parent)
        })

        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .width(280.dp)
            .constrainAs(saveBtn) {
                centerHorizontallyTo(parent)
                top.linkTo(inputs.bottom, 48.dp)
            }) {
            Text(text = "Save")

        }
    }
}


@Composable
fun ProfilePic(
    modifier: Modifier = Modifier, profilePic: ProfilePicState, onPhotoEditClick: () -> Unit,
) {
    @Composable
    fun ProfileLayout(modifier: Modifier, content: @Composable () -> Unit) {
        Layout(content = content, modifier = modifier) { measurables, constraints ->
            val picPlaceable = measurables.first { it.layoutId == "picture" }.measure(constraints)
            val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)

            val rx = picPlaceable.width / 2f
            val ry = picPlaceable.height / 2f

            val icx = rx * cos(Math.toRadians(45.0)) + rx - iconPlaceable.width / 2f
            val icy = ry * sin(Math.toRadians(45.0)) + ry - iconPlaceable.height / 2f

            layout(picPlaceable.width, picPlaceable.height) {
                picPlaceable.place(0, 0)
                iconPlaceable.place(icx.toInt(), icy.toInt())
            }
        }
    }


    ProfileLayout(modifier) {
        when (profilePic) {
            is ProfilePicState.NoPicture -> {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .layoutId("picture")
                        .background(color = colorScheme.surfaceContainer, shape = CircleShape)
                )
            }

            ProfilePicState.Uploading -> {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .layoutId("picture")
                        .background(color = colorScheme.surfaceContainer, shape = CircleShape)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is ProfilePicState.UploadingDone -> {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(profilePic.url)
                        .crossfade(true).build(),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(100.dp)
                        .layoutId("picture")
                        .background(color = colorScheme.surfaceContainer, shape = CircleShape)
                )
            }
        }


        FilledIconButton(
            onClick = onPhotoEditClick, modifier = Modifier.layoutId("icon")
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                contentDescription = "change photo"
            )
        }
    }
}


@Composable
private fun InputFields(modifier: Modifier = Modifier, component: ProfileComponent) {
    val name by component.name.subscribeAsState()
    val dob by component.dob.subscribeAsState()
    val gender by component.gender.subscribeAsState()

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { component.sendEvent(Event.UpdateName(it)) },
            label = { Text(text = "Name") },
            singleLine = true
        )

        DateOfBirthInput(selectedDate = dob, onDateSelected = { component.sendEvent(Event.UpdateDOB(it)) })

        OutlinedTextField(value = dob.toString(), onValueChange = {

        })

        GenderInput(value = gender, onValueChange = { component.sendEvent(Event.UpdateGender(it)) })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderInput(value: Gender, onValueChange: (Gender) -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }

    fun closeMenu() {
        menuExpanded = false
    }

    fun selectGender(gender: Gender) {
        onValueChange(gender)
        closeMenu()
    }

    Column {
        Text(
            text = "Gender", style = typography.labelLarge.copy(color = colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Surface(shape = RoundedCornerShape(50.dp), color = colorScheme.surfaceContainer) {
            Box(
                modifier = Modifier.size(280.dp, 48.dp),
            ) {
                Text(
                    text = value.toString(),
                    style = typography.bodyMedium,
                    modifier = Modifier
                        .align(
                            Alignment.CenterStart
                        )
                        .padding(start = 16.dp)
                )

                IconButton(
                    onClick = { menuExpanded = true },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    /* Icon(
                         imageVector = Icons.Default.ArrowDropDown,
                         contentDescription = "Choose date icon",
                     )*/

                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded)
                }
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        ) {
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Male") },
                    onClick = { selectGender(Gender.Male) })
                DropdownMenuItem(
                    text = { Text(text = "Female") },
                    onClick = { selectGender(Gender.Female) })
                DropdownMenuItem(
                    text = { Text(text = "Other") },
                    onClick = { selectGender(Gender.Other) })
            }
        }
    }
}


@Composable
fun PhotoPickerResult(
    onPhotoSelected: (Uri) -> Unit,
    onDismiss: () -> Unit,
) {
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { onPhotoSelected(uri) }
            onDismiss()
        }
    )
    LaunchedEffect(Unit) {
        singlePhotoPicker.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}
