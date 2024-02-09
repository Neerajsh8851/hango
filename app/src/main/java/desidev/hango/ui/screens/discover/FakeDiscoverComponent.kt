package desidev.hango.ui.screens.discover

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.User
import java.time.LocalDateTime

class FakeDiscoverComponent : DiscoverComponent {
    companion object {
        val dummyUsers = listOf(
            User(
                id = "user1",
                name = "Priya Sharma",
                birthDate = LocalDateTime.of(1995, 10, 25, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/4917824/pexels-photo-4917824.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "priyasharma@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.ACTIVE
            ),
            User(
                id = "user2",
                name = "Anjali Kapoor",
                birthDate = LocalDateTime.of(1990, 5, 12, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/9317127/pexels-photo-9317127.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "anjali.kapoor@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.INACTIVE
            ),
            User(
                id = "user3",
                name = "Tara Singh",
                birthDate = LocalDateTime.of(1988, 7, 7, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/4298629/pexels-photo-4298629.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "tara.singh@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.BUSY
            ),
            User(
                id = "user4",
                name = "Maya Patel",
                birthDate = LocalDateTime.of(1998, 3, 14, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/3444087/pexels-photo-3444087.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "maya.patel@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.ACTIVE
            ),
            User(
                id = "user5",
                name = "Diya Mukherjee",
                birthDate = LocalDateTime.of(1985, 9, 29, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/2613260/pexels-photo-2613260.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "diya.mukherjee@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.INACTIVE
            ),
            User(
                id = "user6",
                name = "Rani Devi",
                birthDate = LocalDateTime.of(2002, 10, 21, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/1130626/pexels-photo-1130626.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "rani.devi@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.INACTIVE
            ),
            User(
                id = "user7",
                name = "Aisha Khan",
                birthDate = LocalDateTime.of(1992, 6, 11, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=600",
                email = "aisha.khan@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.ACTIVE

            ),
            User(
                id = "user8",
                name = "Neha Gupta",
                birthDate = LocalDateTime.of(2000, 8, 4, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "neha.gupta@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.ACTIVE

            ),
            User(
                id = "user9",
                name = "Priya Joshi",
                birthDate = LocalDateTime.of(1987, 4, 18, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/678783/pexels-photo-678783.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "priya.joshi@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.ACTIVE

            ),
            User(
                id = "user10",
                name = "Simran Kaur",
                birthDate = LocalDateTime.of(1993, 12, 26, 0, 0),
                gender = "Female",
                profilePic = "https://images.pexels.com/photos/7176247/pexels-photo-7176247.jpeg?auto=compress&cs=tinysrgb&w=600", // Placeholder image
                email = "simran.kaur@example.com",
                createdAt = LocalDateTime.now(),
                status = User.Status.ACTIVE
            ),
        )
    }

    override val users: Value<List<User>> = MutableValue(dummyUsers)
    override val isLoading: Value<Boolean> = MutableValue(false)
}
