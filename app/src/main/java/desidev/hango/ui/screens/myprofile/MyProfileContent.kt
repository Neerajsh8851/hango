package desidev.hango.ui.screens.myprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import desidev.hango.api.model.Gender
import desidev.hango.ui.composables.ProfilePicture
import desidev.kotlinutils.ifNone
import desidev.kotlinutils.ifSome


@Preview(apiLevel = 33)
@Composable
fun MyProfilePreview() {
    Surface {
        MyProfileContent(component = FakeMyProfileComponent())
    }
}


@Composable
fun NumberLabel(
    modifier: Modifier = Modifier,
    number: Number,
    label: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}


@Composable
fun MyInformation(
    name: String,
    email: String,
    age: Int,
    gender: Gender,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = buildAnnotatedString {
            withStyle(style = MaterialTheme.typography.titleLarge.toSpainStyle()) {
                append(name)
            }

            withStyle(style = MaterialTheme.typography.labelSmall.toSpainStyle()) {
                append(", ${gender.name.first().uppercaseChar()} $age")
            }
        })

        Spacer(modifier = Modifier.height(4.dp))

        Row {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = email)
        }
    }

}


fun TextStyle.toSpainStyle(): SpanStyle {
    // Extract relevant properties from TextStyle
    val fontSize = fontSize
    val color = color
    val fontWeight = fontWeight
    val fontStyle = fontStyle
    val letterSpacing = letterSpacing
    val textDecoration = textDecoration

    // Create and configure SpanStyle with extracted properties
    return SpanStyle(
        fontSize = fontSize,
        color = color,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration
    )
}


@Composable
fun MyProfileContent(component: MyProfileComponent) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val roundedBackground = createRef()
        val profilePic = createRef()
        val myInformation = createRef()

        // background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .background(
                    color = colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(topStart = 100.dp)
                )
                .constrainAs(roundedBackground) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                }
        )

        // my profile picture

        component.model.profilePic.ifSome {
            ProfilePicture(
                imageUrl = it,
                modifier = Modifier.constrainAs(profilePic) {
                    top.linkTo(roundedBackground.top, 16.dp)
                    start.linkTo(roundedBackground.start, 24.dp)
                    bottom.linkTo(roundedBackground.top, 16.dp)
                }
            )
        }

        component.model.profilePic.ifNone {
            Box(
                modifier = Modifier
                    .size(166.dp)
                    .aspectRatio(9 / 16f)
                    .background(color = colorScheme.surfaceContainerLow)
                    .constrainAs(profilePic) {
                        top.linkTo(roundedBackground.top, 16.dp)
                        start.linkTo(roundedBackground.start, 24.dp)
                        bottom.linkTo(roundedBackground.top, 16.dp)
                    }
            )
        }


        MyInformation(
            name = component.model.name,
            email = component.model.email,
            age = component.model.age,
            gender = component.model.gender,
            modifier = Modifier.constrainAs(myInformation) {
                top.linkTo(roundedBackground.top)
                start.linkTo(profilePic.end, 16.dp)
                bottom.linkTo(profilePic.bottom)
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .constrainAs(createRef()) {
                    centerHorizontallyTo(roundedBackground)
                    top.linkTo(profilePic.bottom, 16.dp)
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                NumberLabel(label = "Rating", number = component.model.rating)
                NumberLabel(label = "Followers", number = component.model.followers)
                NumberLabel(label = "Following", number = component.model.following)
            }

            Button(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(text = "Edit Profile")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("About me") },
                value = component.model.aboutMe,
                onValueChange = {},
                readOnly = true,
                minLines = 6
            )
        }
    }
}

