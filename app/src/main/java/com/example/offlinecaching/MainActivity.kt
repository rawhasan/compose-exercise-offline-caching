package com.example.offlinecaching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.offlinecaching.network.Feature
import com.example.offlinecaching.network.NetworkQuake
import com.example.offlinecaching.ui.theme.OfflineCachingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfflineCachingTheme {
                Surface(
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.fillMaxSize()
                ) {
                    OfflineCachingApp()
                }
            }
        }
    }
}

@Composable
fun OfflineCachingApp() {
    val usgsViewModel: UsgsViewModel = viewModel()
    val quakes = usgsViewModel.quakes.observeAsState(NetworkQuake(listOf()))
    val quakeList = quakes.value.features

    Column(modifier = Modifier.background(MaterialTheme.colors.primaryVariant)) {
        TopAppBar {
            Text(
                text = "Bangladesh Earthquake Report",
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(quakeList) { quake ->
                QuakeItemLayout(quake)
            }
        }
    }
}

@Composable
fun QuakeItemLayout(quake: Feature) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // magnitude in circle
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(55.dp)
                .clip(CircleShape)
                .background(getMagColor(quake.properties.mag))

        ) {
            Text(
                text = quake.properties.mag.toString(),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)
            )
        }

        // place and distance
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = (getPlace(quake.properties.place)[0] + " of").uppercase(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
            )
            Text(
                text = getPlace(quake.properties.place)[1],
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f),
                fontSize = 18.sp
            )
        }

        // date and time
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = getDate(quake.properties.time),
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = getTime(quake.properties.time),
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f),
                style = MaterialTheme.typography.body2
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