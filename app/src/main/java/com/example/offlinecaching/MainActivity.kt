package com.example.offlinecaching

import android.icu.text.CaseMap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.offlinecaching.network.Quake
import com.example.offlinecaching.ui.theme.OfflineCachingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfflineCachingTheme {
                Surface(color = MaterialTheme.colors.primaryVariant) {
                    OfflineCachingApp()
                }
            }
        }
    }
}

@Composable
fun OfflineCachingApp() {
    val usgsViewModel: UsgsViewModel = viewModel()
    val quakes = usgsViewModel.quakes.observeAsState(Quake(listOf()))
    val quakeList = quakes.value.features

    Column() {
        TopAppBar {
            Text(text = "Bangladesh Quake Report", modifier = Modifier.padding(horizontal = 16.dp))
        }

//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Text(quakeList.toString(), modifier = Modifier.fillMaxSize())
//        }


        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(quakeList) { quake ->
                //QuakeItemLayout()
                Text(quake.properties.place)
            }
        }
    }
}

@Composable
fun QuakeItemLayout() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(55.dp)
                .clip(CircleShape)
                .background(Color.Red.copy(alpha = 0.7f))

        ) {
            Text(
                text = "6.2", style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "4 km N of", style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
            )
            Text(
                text = "Dhekiajuli, India",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "Jul 12, 2021",
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "7:18 PM",
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OfflineCachingTheme {
        OfflineCachingApp()
    }
}