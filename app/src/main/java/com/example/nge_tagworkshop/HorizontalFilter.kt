package com.example.nge_tagworkshop

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nge_tagworkshop.api.Category

@Composable
fun HorizontalFilter(modifier: Modifier, categories: List<Category>, currentSelection: MutableState<Category>, onSelection: (Category) -> Unit) {

    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        categories.forEach { category ->
            if (category == currentSelection.value) {
                Button(onClick = {
                    onSelection(category)
                },
                    modifier = modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = category.value,
                        modifier = modifier
                    )
                }
            } else {
                FilledTonalButton(onClick = {
                    onSelection(category)
                },
                    modifier = modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = category.value,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HorizontalFilterPreview() {
    val selection = remember { mutableStateOf(Category.Technology) }
    HorizontalFilter(
        modifier = Modifier
            .fillMaxWidth(),
        listOf(Category.Technology, Category.Fashion, Category.Music, Category.ArtDesign),
        selection,
        onSelection = {}
    )
}