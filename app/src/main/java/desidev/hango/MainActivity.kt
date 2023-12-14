package desidev.hango

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.retainedComponent
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import desidev.hango.ui.navigation.RootComponent
import desidev.hango.ui.navigation.ScreenAComponent
import desidev.hango.ui.navigation.ScreenBComponent
import desidev.hango.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = retainedComponent { RootComponent(it) }

        setContent {
            AppTheme {
                RootContent(rootComponent = root)
            }
        }
    }
}





@Composable
fun RootContent(rootComponent: RootComponent, modifier: Modifier = Modifier) {
    rootComponent.stack.value.backStack
    Children(
        stack = rootComponent.stack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.ScreenA -> ScreenAContent(child.screenAComponent)
            is RootComponent.Child.ScreenB -> ScreenBContent(child.screenBComponent)
        }
    }
}


@Composable
fun ScreenAContent(comp: ScreenAComponent) {
    val text by comp.text.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = text,
                onValueChange = { comp.onEvent(ScreenAComponent.Event.UpdateText(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { comp.onEvent(ScreenAComponent.Event.OnClickButtonA) }) {
                Text(text = "ButtonA")
            }
        }
    }
}


@Composable
fun ScreenBContent(comp: ScreenBComponent) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Message from screen A: ${comp.message}")

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { comp.goBack() } ) {
                Text(text = "Go back!")
            }
        }
    }
}