package com.example.nge_tagworkshop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nge_tagworkshop.R
import com.example.nge_tagworkshop.models.Category
import com.example.nge_tagworkshop.models.Event


@Composable
fun EventCard(
    event: Event
) {
    Card(
        modifier = Modifier
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            BannerImage("")
            Text(
                text = event.title,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                maxLines = Int.MAX_VALUE
            )
            Text(
                text = "${event.location} | ${event.time}",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Text(
                text = event.description,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Magenta.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "${event.price}",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

        }
    }
}

@Composable
fun BannerImage(imagePath: String) {
    val imageModifier = Modifier
        .heightIn(max = 180.dp)
        .padding(bottom = 8.dp)
    Image(
        painter = painterResource(id = R.drawable.placeholder),
        contentDescription = "",
        contentScale = ContentScale.FillWidth,
        modifier = imageModifier
            .clip(RoundedCornerShape(16.dp))
    )
}

@Preview()
@Composable
fun EventCardPreview() {
    EventCard(
        Event(
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
}