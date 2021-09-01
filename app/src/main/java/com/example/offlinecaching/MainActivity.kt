package com.example.offlinecaching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.offlinecaching.database.DatabaseQuake
import com.example.offlinecaching.ui.theme.OfflineCachingTheme
import com.example.offlinecaching.viewmodel.QuakeViewModel
import com.example.offlinecaching.viewmodel.QuakeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val quakeViewModel by viewModels<QuakeViewModel> {
            QuakeViewModelFactory((application as QuakeApplication).repository)
        }

        // replace splash screen theme with the app theme
        setTheme(R.style.Theme_OfflineCaching_NoActionBar)

        setContent {
            OfflineCachingTheme {
                Surface(
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.fillMaxSize()
                ) {
                    OfflineCachingApp(quakeViewModel)
                }
            }
        }
    }
}

@Composable
fun OfflineCachingApp(quakeViewModel: QuakeViewModel) {
    val quakes: List<DatabaseQuake> by quakeViewModel.quakes.observeAsState(listOf())
    val status = quakeViewModel.status

    Column {
        TopAppBar {
            Text(
                text = "Bangladesh Earthquake Report",
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        // FIXME: Image vertical alignment, show feedback based on status
        // Show a loading animation while database is empty
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (quakes.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Image(
                            painter = rememberImagePainter(data = R.drawable.loading_animation),
                            contentDescription = null,
                            modifier = Modifier.size(300.dp)
                        )
                    }
                }
            } else {
                items(quakes) { quake ->
                    QuakeItemLayout(quake)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant)
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "${quakes.size} Earthquakes in 400 km radius from Dhaka last year",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.65f)
            )
        }
    }
}


@Composable
fun QuakeItemLayout(quake: DatabaseQuake) {
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
                .background(getMagColor(quake.mag))

        ) {
            Text(
                text = quake.mag.toString(),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)
            )
        }

        // place and distance
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = (quake.near + " of").uppercase(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
            )
            Text(
                text = quake.place,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f),
                fontSize = 18.sp
            )
        }

        // date and time
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = quake.date,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = quake.time,
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f),
                style = MaterialTheme.typography.body2
            )
        }
    }
}