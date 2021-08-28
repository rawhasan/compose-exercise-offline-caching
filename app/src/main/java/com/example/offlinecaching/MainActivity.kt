package com.example.offlinecaching

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.offlinecaching.database.DatabaseQuake
import com.example.offlinecaching.ui.theme.OfflineCachingTheme
import com.example.offlinecaching.viewmodel.QuakeViewModel
import com.example.offlinecaching.viewmodel.QuakeViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OfflineCachingApp(quakeViewModel: QuakeViewModel) {
    val quakes: List<DatabaseQuake> by quakeViewModel.quakes.observeAsState(listOf())

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
            items(quakes) { quake ->
                QuakeItemLayout(quake)
            }
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