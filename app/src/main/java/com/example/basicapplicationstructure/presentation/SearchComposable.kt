package com.example.basicapplicationstructure.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchComposable(state: SearchState, onSearchClicked: (String) -> Unit,onBackClicked: () -> Unit) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "backArrow",
                    modifier = Modifier.clickable{
                        onBackClicked.invoke()
                    }
                )
                Text(
                    "Search",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        value = searchQuery,
                        label = { Text("Search Cities") },
                        onValueChange = {
                            searchQuery = it
                            onSearchClicked.invoke(searchQuery)
                        })
                    Button(modifier = Modifier.padding(vertical = 16.dp), onClick = {
                       // onSearchClicked.invoke(searchQuery)
                    }) {
                        Text("Submit")
                    }

                    HorizontalDivider(modifier = Modifier.padding(top = 20.dp))
                }
            }
            item {
                if (state.isLoading){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(all = 16.dp))
                    }
                }
            }
            itemsIndexed (state.searchResults) { index,item ->
                CityCard(item)
                if(index < state.searchResults.lastIndex){
                    HorizontalDivider(modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

@Composable
fun CityCard(results: SearchResults) {
    Card(modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier
            .fillMaxWidth()) {
            Text(
                text = results.countryName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)

            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = results.cityName,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 5.dp, start = 16.dp)
                )
                Text(text = results.stateName,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 5.dp, start = 16.dp, bottom = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCityCard() {

    val mockData = SearchResults(countryName = "India", cityName = "Mumbai", stateName = "Maharashtra")
    CityCard(mockData)
}