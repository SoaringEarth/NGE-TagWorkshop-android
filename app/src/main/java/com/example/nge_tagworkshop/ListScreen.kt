package com.example.nge_tagworkshop

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nge_tagworkshop.components.EventCard
import com.example.nge_tagworkshop.components.HorizontalFilter
import com.example.nge_tagworkshop.models.Category

@Composable
fun ListScreen(modifier: Modifier, navController: NavController, viewModel: ListViewModel = ListViewModel()) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp)
    ) {
        FeaturedEventsSection(modifier = modifier)
        PopularEventsSection(
            modifier = modifier,
            viewModel = viewModel)
        UpcomingEventsSection(
            modifier = modifier,
            eventList = viewModel.reposList
        )
    }
}

@Composable
fun FeaturedEventsSection(modifier: Modifier) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .heightIn(max = 180.dp)
            .padding(start = 10.dp, end = 10.dp)
    ) {
        for (i in 1..3) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = modifier
                    .padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
fun PopularEventsSection(modifier: Modifier, viewModel: ListViewModel) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        Text(
            modifier = modifier
            .padding(start = 16.dp),
        text = "Popular Categories"
        )
        HorizontalFilter(
            modifier = modifier,
            categories = viewModel.popularCategories,
            currentSelection = viewModel.selectedCategory,
            onSelection = {
                if (it == viewModel.selectedCategory.value) {
                    viewModel.selectedCategory.value = Category.All
                } else {
                    viewModel.selectedCategory.value = it
                }
            }
        )
        LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(viewModel.getEvents(viewModel.selectedCategory.value)) { event ->
                EventCard(
                    event = event
                )
            }
        }
    }

}

@Composable
fun UpcomingEventsSection(modifier: Modifier, eventList: List<String>) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Upcoming Events",
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            maxLines = Int.MAX_VALUE,
            modifier = Modifier
                .padding(start=20.dp)
        )
//        LazyRow {
//            items(eventList) { event ->
//                EventCard(
//                    event = event
//                )
//            }
//        }
    }
}

fun onButtonTap(navController: NavController) {
    Log.d("listScreen", "Tapped Button")
    navController.navigate(Screens.DetailScreen.path)
}
