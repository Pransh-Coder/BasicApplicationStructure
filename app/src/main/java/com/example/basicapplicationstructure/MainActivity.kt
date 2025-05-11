package com.example.basicapplicationstructure

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.basicapplicationstructure.data.Images
import com.example.basicapplicationstructure.data.Jpg
import com.example.basicapplicationstructure.data.Webp
import com.example.basicapplicationstructure.presentation.AnimeData
import com.example.basicapplicationstructure.presentation.AnimeDetailScreenComposable
import com.example.basicapplicationstructure.presentation.viewModel.AnimeViewModel
import com.example.basicapplicationstructure.ui.theme.BasicApplicationStructureTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AnimeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            val context = LocalContext.current
            val navController = rememberNavController()

            val animeDetailState by viewModel.detailState.collectAsStateWithLifecycle()

            LaunchedEffect(viewModel.errors) {
                viewModel.errors.collectLatest {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }

            LaunchedEffect(viewModel.screenOneTimeEvents) {
                viewModel.screenOneTimeEvents.collectLatest {
                    navController.navigate(ANIME_DETAIL_SCREEN)
                }
            }

            BasicApplicationStructureTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White,
                    topBar = {
                        Toolbar(
                            onBackPress = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        )
                    }) { innerPadding ->
                    if (state.isLoading) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        NavHost(
                            navController = navController,
                            startDestination = ANIME_LIST_SCREEN
                        ) {
                            composable(route = ANIME_LIST_SCREEN) {
                                AnimeListComposable(
                                    modifier = Modifier.padding(innerPadding),
                                    animeList = state.animeList,
                                    onCardClicked = {
                                        viewModel.onAnimeClicked(it)
                                    }
                                )
                            }
                            composable(route = ANIME_DETAIL_SCREEN) {
                                AnimeDetailScreenComposable(
                                    modifier = Modifier.padding(innerPadding),
                                    animeDetailState = animeDetailState
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val ANIME_LIST_SCREEN = "anime_list_screen"
        const val ANIME_DETAIL_SCREEN = "anime_detail_screen"
    }
}

@Preview
@Composable
private fun Toolbar(onBackPress: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .clickable {
                onBackPress.invoke()
            }
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "BackArrow",
                modifier = Modifier.padding(start = 5.dp)
            )
            Text(
                text = "Anime Data",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 23.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            )
        }
    }
}

@Composable
fun AnimeListComposable(
    modifier: Modifier = Modifier,
    animeList: List<AnimeData>,
    onCardClicked: (String) -> Unit,
) {

    LazyColumn(
        modifier = modifier
            .padding(bottom = 16.dp)
            .fillMaxSize()
    ) {

        itemsIndexed(animeList) { index, item ->
            AnimeItem(item, onItemClick = {
                onCardClicked.invoke(it)
            })
            if (index < animeList.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                )
            }
        }
    }
}

@Composable
fun AnimeItem(animeData: AnimeData, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke(animeData.mal_id)
            }) {
        AsyncImage(
            model = animeData.images.webp.image_url,
            contentDescription = "posterImage",
            modifier = Modifier
                .weight(0.3f)
                .padding(top = 16.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .weight(0.7f)
        ) {

            Text(
                text = "Title: ${animeData.title}",
                fontWeight = FontWeight.Medium,
                color = Color.Red,
                fontSize = 18.sp
            )

            Text(
                text = "Episodes: ${animeData.episodes}",
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontSize = 16.sp
            )

            Text(
                text = "Score: ${animeData.score}",
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}


@Preview
@Composable
private fun PreviewAnimeItem() {
    val mockData = AnimeData(
        mal_id = "1",
        title = "Sousou no Frieren",
        episodes = 30,
        score = 9.8,
        images = Images(
            webp = Webp(
                image_url = "https://cdn.myanimelist.net/images/anime/1015/138006.webp",
                small_image_url = "https://cdn.myanimelist.net/images/anime/1015/138006t.webp",
                large_image_url = "https://cdn.myanimelist.net/images/anime/1015/138006l.webp"
            ),
            jpg = Jpg(
                image_url = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg",
                small_image_url = "https://cdn.myanimelist.net/images/anime/1015/138006t.jpg",
                large_image_url = "https://cdn.myanimelist.net/images/anime/1015/138006l.jpg")
        )
    )
    AnimeItem(animeData = mockData, onItemClick = {})
}

