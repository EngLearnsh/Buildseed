package com.englearnsh.buildseed

import android.os.Bundle
import android.os.StrictMode

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Menu
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

import com.englearnsh.buildseed.ui.BuildseedTheme
import com.englearnsh.buildseed.util.HtmlParse

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force permitting all thread policies (Alpha stage)
        if (android.os.Build.VERSION.SDK_INT > 9) {
            var policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        setContent {
            BuildseedTheme {
                AppComponent()
            }
        }
    }
}

@Composable
fun AppComponent() {
    val (drawerState, onDrawerStateChange) = state { DrawerState.Closed }
    val currentScreen = state { AppScreen.Home }

    ModalDrawerLayout(
            drawerState = drawerState,
            onStateChange = onDrawerStateChange,
            gesturesEnabled = drawerState == DrawerState.Opened,
            drawerContent = {
                DrawerContentComponent(
                        currentScreen = currentScreen,
                        closeDrawer = { onDrawerStateChange(DrawerState.Closed)
                        }
                )
            },
            bodyContent = {
                BodyContentComponent(
                        currentScreen = currentScreen.value,
                        openDrawer = {
                            onDrawerStateChange(DrawerState.Opened)
                        }
                )
            }
    )
}

@Composable
fun DrawerContentComponent(
        currentScreen: MutableState<AppScreen>,
        closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        for (index in AppScreen.values().indices) {
            val screen = getScreenBasedOnIndex(index)
            Box(Modifier.clickable(onClick = {
                currentScreen.value = screen
                closeDrawer()
            }), children = {
                Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = if (currentScreen.value == screen) {
                            MaterialTheme.colors.secondary
                        } else {
                            MaterialTheme.colors.surface
                        }
                ) {
                    Text(text = screen.name, modifier = Modifier.padding(16.dp))
                }
            })
        }
    }
}

/**
 * Return the corresponding screen composable based on user interactive index
 */
fun getScreenBasedOnIndex(index: Int) = when (index) {
    0 -> AppScreen.Home
    else -> AppScreen.Home
}

/**
 * Pass the corresponding screen composable based on current active screen
 */
@Composable
fun BodyContentComponent(
        currentScreen: AppScreen,
        openDrawer: () -> Unit
) {
    when (currentScreen) {
        AppScreen.Home -> HomeComponent(
                openDrawer
        )
    }
}

@Composable
fun HomeComponent(openDrawer: () -> Unit) {

    val version = HtmlParse().parseVersion()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
                title = { Text(text = "Buildseed") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(asset = Icons.Filled.Menu)
                    }
                }
        )
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.weight(1f)) {
            Box(modifier = Modifier.padding(16.dp), gravity = ContentGravity.Center, children = {
                Text(text = "$version")
            })
        }
    }
}

enum class AppScreen {
    Home
}

/**
 * Composable preview directly inside Android Studio
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BuildseedTheme {
        AppComponent()
    }
}