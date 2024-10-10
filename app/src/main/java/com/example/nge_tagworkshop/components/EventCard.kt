package com.example.nge_tagworkshop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.nge_tagworkshop.api.Photo
import com.example.nge_tagworkshop.api.WeatherData
import com.example.nge_tagworkshop.models.Category
import com.example.nge_tagworkshop.models.Event
import kotlin.math.roundToInt

@Composable
fun EventCard(
    modifier: Modifier,
    viewModel: EventCardViewModel
) {
    Card(
        modifier = modifier
            .width(320.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            BannerImage(viewModel.eventPhoto)
            Text(
                text = viewModel.event.title,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                maxLines = Int.MAX_VALUE
            )
            Text(
                text = "${viewModel.event.location} | ${viewModel.parseCustomUtcString()}",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(
                modifier = modifier
                    .padding(top = 8.dp)
            )
            Text(
                text = viewModel.event.description,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(
                modifier = modifier
                    .padding(top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
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

                if (viewModel.weatherData.value !== null) {
                    Spacer(
                        modifier = modifier
                            .padding(start = 8.dp)
                    )
                    WeatherIcon(viewModel.weatherData.value!!)
                }

            }
        }
    }
}

@Composable
fun WeatherIcon(value: WeatherData) {
    Box(
        modifier = Modifier
            .background(
                color = Color.Blue.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = "${value.temperature.roundToInt()}°",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
            )
            AsyncImage(
                model = value.icon, // Use any available size URL from the Photo object
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

    }
}

@Composable
fun BannerImage(photo: MutableState<Photo?>) {
    val imageModifier = Modifier
        .heightIn(max = 180.dp)
        .padding(bottom = 8.dp)
    if (photo.value == null) {
        Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    } else {
        SubcomposeAsyncImage(
            model = photo.value!!.src.medium, // Use any available size URL from the Photo object
            contentDescription = "Photo by ${photo.value!!.photographer}",
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

@Preview()
@Composable
fun EventCardPreview() {
    EventCard(
        modifier = Modifier,
        viewModel = EventCardViewModel(
            event = Event(
                id = 1,
                title = "Event Title",
                description = "Event Description",
                location = "Event Location",
                time = "2023-11-17T16:23:45Z",
                image = "https://picsum.photos/200/300",
                price = 10,
                category = Category.Technology
            )
        )
    )
}