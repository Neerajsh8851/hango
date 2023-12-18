package desidev.hango.ui.screens.signup_process.profile

import com.arkivanov.decompose.ComponentContext
import desidev.hango.models.Gender
import desidev.hango.ui.screens.Events
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate


sealed interface Event {
    data class UpdateName(val value: String) : Event
    class UpdateDOB(val dob: LocalDate) : Event
    class UpdateGender(val gender: Gender) : Event
}

interface ProfileComponent : Events<Event> {
    val name: StateFlow<String>
    val dob: StateFlow<LocalDate>
    val gender: StateFlow<Gender>
}


class DefaultProfileComponent(context: ComponentContext) : ProfileComponent,
    ComponentContext by context {

    private val _name = MutableStateFlow("")
    override val name: StateFlow<String> = _name

    private val _dob = MutableStateFlow(LocalDate.now().minusYears(18))
    override val dob: StateFlow<LocalDate> = _dob

    private val _gender = MutableStateFlow(Gender.Male)
    override val gender: StateFlow<Gender> = _gender

    override fun onEvent(event: Event) {
        when(event) {
            is Event.UpdateName -> _name.value = event.value
            is Event.UpdateDOB -> _dob.value = event.dob
            is Event.UpdateGender -> _gender.value = event.gender
        }
    }
}

class FakeProfileComponent : ProfileComponent {

    private val _name = MutableStateFlow("")
    override val name: StateFlow<String> = _name

    private val _dob = MutableStateFlow(LocalDate.now().minusYears(18))
    override val dob: StateFlow<LocalDate> = _dob

    private val _gender = MutableStateFlow(Gender.Male)
    override val gender: StateFlow<Gender> = _gender

    override fun onEvent(event: Event) {
        when(event) {
            is Event.UpdateName -> _name.value = event.value
            else -> {}
        }
    }
}