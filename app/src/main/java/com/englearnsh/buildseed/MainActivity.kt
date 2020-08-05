package com.englearnsh.buildseed

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.englearnsh.buildseed.ui.BuildseedTheme
import com.englearnsh.buildseed.util.HtmlParse

class MainActivity : AppCompatActivity() {

    val TAG = "Buildseed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force permitting all thread policies (Alpha stage)
        if (android.os.Build.VERSION.SDK_INT > 9) {
            var policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        setContent {
            BuildseedTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val version = HtmlParse().parseVersion()
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "$version")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BuildseedTheme {
        Greeting()
    }
}