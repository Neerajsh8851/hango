package desidev.hango.ui.screens.profile

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.Gender
import desidev.kotlinutils.Option
import java.time.LocalDate



interface ProfileComponent  {
    val name: Value<String>
    val dob: Value<LocalDate>
    val gender: Value<Gender>
    val profilePic: Value<Option<ImageBitmap>>

    fun setName(name: String)
    fun setDob(dob: LocalDate)
    fun setGender(gender: Gender)
    fun onPhotoSelect(profileUri: Uri)
    fun submit()
}

