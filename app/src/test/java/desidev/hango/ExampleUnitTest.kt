package desidev.hango

import androidx.compose.runtime.Composable
import desidev.customnavigation.Component
import desidev.customnavigation.viewmodel.viewModelProvider
import desidev.hango.viewmodels.SignUpViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun classMatch() {
        val model = SignUpViewModel()
        assert(SignUpViewModel::class.isInstance(model))
    }

    @Test
    fun viewModelProviderTest(){
        viewModelProvider.registerViewModelBuilder {
            addBuilderFor(SignUpViewModel::class) { SignUpViewModel() }
        }

        val node = object : Component() {
            @Composable
            override fun Content() {
            }
        }

        val viewModel = viewModelProvider.getViewModel(node, SignUpViewModel::class)
        assert(viewModel != null)
    }
}