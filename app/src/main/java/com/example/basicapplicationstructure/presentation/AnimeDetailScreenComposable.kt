package com.example.basicapplicationstructure.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import com.example.basicapplicationstructure.R
import com.example.basicapplicationstructure.presentation.states.AnimeDetailState

@Preview
@Composable
fun AnimeDetailScreenComposable(
    modifier: Modifier = Modifier,
    animeDetailState: AnimeDetailState = AnimeDetailState(),
) {
    var genres by remember { mutableStateOf("") }

    LaunchedEffect(animeDetailState.animeDetailsData?.genres) {
        animeDetailState.animeDetailsData?.genres?.forEach {
            if (genres.isEmpty()) {
                genres = it.name
            } else {
                genres = genres + "," + it.name
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (animeDetailState.isLoading) {
            CircularProgressIndicator()
        } else {
            if (animeDetailState.animeDetailsData?.trailer?.youtube_id.isNullOrEmpty().not()) {
                YoutubePlayer(
                    lifecycleOwner = LocalLifecycleOwner.current,
                    youtubeVideoId = animeDetailState.animeDetailsData.trailer.youtube_id
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = animeDetailState.animeDetailsData?.images?.webp?.image_url,
                    contentDescription = "posterImage",
                    placeholder = painterResource(R.drawable.ic_launcher_foreground)
                )
            }

            Text(
                text = animeDetailState.animeDetailsData?.title ?: "N/A",
                color = Color.Black,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            Text(
                text = animeDetailState.animeDetailsData?.synopsis ?: "",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = "Genres: $genres",
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Members/Cast Count: ${animeDetailState.animeDetailsData?.members ?: 0}",
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "No Of Episodes: ${animeDetailState.animeDetailsData?.episodes ?: 0}",
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Rating: ${animeDetailState.animeDetailsData?.score ?: 0.0}",
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
