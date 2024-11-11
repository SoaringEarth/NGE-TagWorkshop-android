package com.example.nge_tagworkshop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
        viewModel.fetchEventAndImage(viewModel.event.value?.id.toString())
    }

    val event = viewModel.event.collectAsState().value
    val image = viewModel.eventPhoto.collectAsState().value

    // Main content of the screen in a column
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (image != null) {
            BannerImage(image)
        }

        // Event Title
        Text(
            text = "Event Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        HorizontalDivider(modifier = Modifier)

        // Event Title
        Text(
            text = viewModel.eventTitle(),
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

        Row {
            WeatherIcon(viewModel.weather.first())
        }

        // Event Price
        Text(
            text = "Price: $${event?.price}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
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
fun BannerImage(photo: Photo?) {
    val imageModifier = Modifier
        .padding(bottom = 8.dp)
    if (photo == null) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    } else {
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
}
