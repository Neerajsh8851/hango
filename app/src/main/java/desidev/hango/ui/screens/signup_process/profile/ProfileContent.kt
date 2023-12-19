package desidev.hango.ui.screens.signup_process.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import desidev.hango.R
import desidev.hango.models.Gender
import desidev.hango.ui.theme.AppTheme
import java.time.LocalDate
import kotlin.math.cos
import kotlin.math.sin


@Preview
@Composable
fun ProfileContentPreview() {
    AppTheme {
        Surface {
            ProfileContent(
                bloc = remember { FakeProfileComponent() }, modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ProfileContent(bloc: ProfileComponent, modifier: Modifier = Modifier) {
    val profilePicModel by bloc.profilePic.collectAsState()

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
            profilePic = profilePicModel,
            onPhotoEditClick = {}
        )

        InputFields(bloc = bloc, modifier = Modifier.constrainAs(inputs) {
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
    modifier: Modifier = Modifier, profilePic: ProfilePicState, onPhotoEditClick: () -> Unit
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
private fun InputFields(modifier: Modifier = Modifier, bloc: ProfileComponent) {
    val name by bloc.name.collectAsState()
    val dob by bloc.dob.collectAsState()
    val gender by bloc.gender.collectAsState()

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        RoundedTextField(
            value = name,
            onValueChange = { bloc.onEvent(Event.UpdateName(it)) },
            label = "Name",
            hint = "your name"
        )

        DOBInput(value = dob, onDateSelect = { bloc.onEvent(Event.UpdateDOB(it)) })

        GenderInput(value = gender, onValueChange = { bloc.onEvent(Event.UpdateGender(it)) })
    }
}


@Composable
fun RoundedTextField(
    value: String, onValueChange: (String) -> Unit, label: String = "", hint: String = ""
) {
    Column {
        Text(
            text = label, style = typography.labelLarge.copy(color = colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.height(12.dp))

        val contentTypo = typography.bodyMedium

        // user name field
        BasicTextField(value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = contentTypo,
            decorationBox = { textContent ->
                Surface(
                    shape = RoundedCornerShape(50.dp), color = colorScheme.surfaceContainer
                ) {
                    Box(
                        modifier = Modifier
                            .size(280.dp, 48.dp)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        textContent()
                        if (value.isEmpty()) {
                            Text(
                                text = hint, style = contentTypo.copy(color = colorScheme.outline)
                            )
                        }
                    }
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DOBInput(value: LocalDate, onDateSelect: (LocalDate) -> Unit) {
    val useCaseState = rememberUseCaseState()


    Column {
        Text(
            text = "Your date of birth",
            style = typography.labelLarge.copy(color = colorScheme.onSurface)
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
                    onClick = { useCaseState.show() },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Choose date icon",
                    )
                }
            }
        }
    }

    val dateRange = (LocalDate.now().minusYears(70))..LocalDate.now().minusYears(18)

    // date picker dialog
    CalendarDialog(
        state = useCaseState,
        header = Header.Default(title = "Date of birth"),
        config = CalendarConfig(
            monthSelection = true, yearSelection = true, boundary = dateRange
        ),
        selection = CalendarSelection.Date { newDate ->
            onDateSelect(newDate)
        }
    )
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

