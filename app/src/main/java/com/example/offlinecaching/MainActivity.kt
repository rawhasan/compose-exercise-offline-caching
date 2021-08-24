package com.example.offlinecaching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.offlinecaching.ui.theme.OfflineCachingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfflineCachingTheme {
                Surface(color = MaterialTheme.colors.background) {
                    OfflineCachingApp()
                }
            }
        }
    }
}

@Composable
fun OfflineCachingApp() {
    Text("Offline Caching")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OfflineCachingTheme {
        OfflineCachingApp()
    }
}