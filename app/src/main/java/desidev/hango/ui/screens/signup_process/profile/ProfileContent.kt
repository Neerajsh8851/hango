package desidev.hango.ui.screens.signup_process.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.draw.clip
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
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.ifNone
import desidev.kotlin.utils.ifSome
import kotlin.math.cos
import kotlin.math.sin


@Preview
@Composable
fun ProfileContentPreview() {
    AppTheme {
        Surface {
            ProfileContent(
                component = remember { FakeProfileComponent() }
            )
        }
    }
}

@Composable
fun ProfileContent(component: ProfileComponent) {
    val profilePicState by component.profilePic.subscribeAsState()
    var openPhotoPicker by remember { mutableStateOf(false) }

    if (openPhotoPicker) {
        PhotoPickerResult(
            onPhotoSelected = { uri: Uri ->
                Log.d("ProfileContent", "Photo selected${uri.path}")
            },
            onDismiss = {
                openPhotoPicker = false
            }
        )
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
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

        InputFields(
            component = component,
            modifier = Modifier.constrainAs(inputs) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            }
        )

        Button(onClick = { component.onSubmitClick() }, modifier = Modifier
            .width(280.dp)
            .constrainAs(saveBtn) {
                centerHorizontallyTo(parent)
                top.linkTo(inputs.bottom, 48.dp)
            }
        ) {
            Text(text = "Submit")
        }
    }
}


@Composable
fun ProfilePic(
    modifier: Modifier = Modifier, profilePic: Option<Uri>, onPhotoEditClick: () -> Unit,
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


    val profilePicModifier = Modifier
        .layoutId("picture")
        .size(100.dp)
        .clip(CircleShape)
        .border(color = colorScheme.outline, width = 1.dp, shape = CircleShape)

    ProfileLayout(modifier = modifier) {
        profilePic.ifSome { uri ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(uri)
                    .crossfade(true).build(),
                contentDescription = "Profile picture",
                modifier = profilePicModifier
            )
        }

        profilePic.ifNone {
            Box(
                modifier = profilePicModifier.background(color = colorScheme.tertiaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "DP",
                    color = colorScheme.onTertiaryContainer,
                    style = typography.headlineMedium
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
            onValueChange = { newValue -> component.setName(newValue) },
            singleLine = true
        )
        DateOfBirthInput(selectedDate = dob, onDateSelected = { date -> component.setDob(date) })
        GenderInput(value = gender, onValueChange = { newValue -> component.setGender(newValue) })
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

    ExposedDropdownMenuBox(
        expanded = menuExpanded,
        onExpandedChange = {
            menuExpanded = it
        }
    ) {
        OutlinedTextField(
            value = value.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "Gender") },
            trailingIcon = {
                IconButton(onClick = { menuExpanded = true }) {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded)
                }
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = {
                menuExpanded = false
            },
        ) {
            Gender.entries.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender.toString()) },
                    onClick = {
                        selectGender(gender)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
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
