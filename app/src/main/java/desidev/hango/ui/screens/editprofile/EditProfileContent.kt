package desidev.hango.ui.screens.editprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import desidev.hango.R
import desidev.hango.ui.composables.ProfilePicture
import desidev.hango.ui.screens.profile.BasicInfoFormFields
import desidev.hango.ui.theme.AppTheme
import desidev.kotlinutils.ifNone
import desidev.kotlinutils.ifSome

@Preview(apiLevel = 33)
@Composable
fun EditProfilePreview() {
    AppTheme {
        EditProfileContent(component = FakeEditProfileComponent())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(component: EditProfileComponent) {
    val model = component.model
    val name by model.name.subscribeAsState()
    val gender by model.gender.subscribeAsState()
    val birthDate by model.birthDate.subscribeAsState()
    val aboutMe by model.aboutMe.subscribeAsState()
    val profilePicture by model.profilePic.subscribeAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Navigation"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save Changes"
                        )
                    }
                }
            )
        }
    ) { scaffoldPaddings ->
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(scaffoldPaddings)
                .fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                profilePicture.ifSome {
                    ProfilePicture(imageUrl = it)
                }
                profilePicture.ifNone {
                    Box(
                        modifier = Modifier
                            .size(166.dp)
                            .aspectRatio(9 / 16f)
                    )
                }

                TextButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_file_upload_24),
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Upload New")
                }
            }


            BasicInfoFormFields(
                name = name,
                gender = gender,
                dob = birthDate,
                onNameValueChange = component::setName,
                onGenderValueSelect = component::setGender,
                onBirthDateChange = component::setDateOfBirth
            )

            OutlinedTextField(
                label = { Text("About me") },
                value = aboutMe,
                onValueChange = {},
                readOnly = true,
                minLines = 6
            )
        }
    }

}