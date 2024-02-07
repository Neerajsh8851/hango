/*
 * Copyright © 2024 desidev (desidev.online)
 * site: https://desidev.online
 */

package desidev.hango

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import desidev.hango.ui.screens.main.DefaultMainComponent
import desidev.hango.ui.screens.main.MainContent
import desidev.hango.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e(TAG, "Uncaught exception", e)
            finish()
        }

        
        val main = retainedComponent {
            DefaultMainComponent(componentContext = it)
        }

        setContent {
            AppTheme {
                MainContent(component = main)
            }
        }
    }
}




