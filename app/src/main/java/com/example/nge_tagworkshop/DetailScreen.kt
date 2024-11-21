package com.example.nge_tagworkshop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.nge_tagworkshop.api.Photo
import com.example.nge_tagworkshop.components.WeatherIcon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(modifier: Modifier, navController: NavController, viewModel: DetailScreenViewModel) {
    DetailContent(modifier = modifier.padding(), viewModel = viewModel)
}

@Composable
fun DetailContent(modifier: Modifier = Modifier, viewModel: DetailScreenViewModel) {

    LaunchedEffect(viewModel.event) {
        viewModel.fetchEventData(viewModel.event.value?.id.toString())
    }

    val image = viewModel.eventPhoto.collectAsState().value

    // Main content of the screen in a column
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        if (image !== null) {
            BannerImage(image)
        }

        // Event Title
        Text(
            text = "Event Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        // Event Location
        Text(
            text = "${viewModel.eventLocation()} | ${viewModel.eventTime()}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Blue.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = viewModel.getEventCost(),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (viewModel.weather !== null) {
                Spacer(
                    modifier = modifier
                        .padding(start = 8.dp)
                )
                WeatherIcon(viewModel.weather!!)
            }
        }


        // Event Title
        Text(
            text = viewModel.eventTitle(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )


        // Event Description
        Text(
            text = viewModel.eventDescription(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BannerImage(photo: Photo) {
    val imageModifier = Modifier
        .padding(bottom = 8.dp)

    SubcomposeAsyncImage(
        model = photo.src.medium, // Use any available size URL from the Photo object
        contentDescription = "Photo by ${photo.photographer}",
        contentScale = ContentScale.FillWidth,
        loading = {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        },
        modifier = imageModifier
            .aspectRatio(1.5f) // Adjust aspect ratio as needed
            .clip(RoundedCornerShape(16.dp))
    )
}

@Preview
@Composable
fun WeatherIconPreview() {
    DetailContent(modifier = Modifier.padding(), viewModel = DetailScreenViewModel(eventId = 1))
}
