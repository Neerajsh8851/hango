package desidev.hango.ui.screens.signup_process.profile

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import desidev.hango.R
import desidev.hango.models.Gender
import desidev.hango.ui.theme.AppTheme
import java.time.LocalDate


@Preview
@Composable
fun ProfileContentPreview() {
    AppTheme {
        Surface {
            ProfileContent(
                bloc = remember { FakeProfileComponent() },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun ProfileContent(bloc: ProfileComponent, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        val (headingTxt, inputs, saveBtn, profilePic) = createRefs()
        Text(
            text = "Create Profile", modifier = Modifier
                .height(56.dp)
                .wrapContentSize()
                .constrainAs(headingTxt) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                },
            style = typography.headlineMedium.copy(color = colorScheme.onSurface)
        )

        ProfilePic(modifier = Modifier.constrainAs(profilePic) {
            centerHorizontallyTo(parent)
            top.linkTo(headingTxt.bottom)
            bottom.linkTo(inputs.top)
        })

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
fun ProfilePic(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(90.dp)
            .background(color = colorScheme.surfaceContainer, shape = RoundedCornerShape(50.dp))
    ) {
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.BottomEnd)) {
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

        DOBInput(value = dob, onValueChange = { bloc.onEvent(Event.UpdateDOB(it)) })

        GenderInput(value = gender, onValueChange = { bloc.onEvent(Event.UpdateGender(it)) })
    }
}


@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    hint: String = ""
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.height(12.dp))

        val contentTypo = MaterialTheme.typography.bodyMedium

        // user name field
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = contentTypo,
            decorationBox = { textContent ->
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = colorScheme.surfaceContainer
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
                                text = hint,
                                style = contentTypo.copy(color = colorScheme.outline)
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun DOBInput(value: LocalDate, onValueChange: (LocalDate) -> Unit) {
    Column {
        Text(
            text = "Your date of birth",
            style = typography.labelLarge.copy(color = colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Surface(shape = RoundedCornerShape(50.dp), color = colorScheme.surfaceContainer) {
            Box(
                modifier = Modifier
                    .size(280.dp, 48.dp),
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
                    onClick = { /*TODO*/ },
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
}


@Composable
fun GenderInput(value: Gender, onValueChange: (Gender) -> Unit) {
    Column {
        Text(
            text = "Gender",
            style = typography.labelLarge.copy(color = colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Surface(shape = RoundedCornerShape(50.dp), color = colorScheme.surfaceContainer) {
            Box(
                modifier = Modifier
                    .size(280.dp, 48.dp),
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
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Choose date icon",
                    )
                }
            }
        }
    }
}

