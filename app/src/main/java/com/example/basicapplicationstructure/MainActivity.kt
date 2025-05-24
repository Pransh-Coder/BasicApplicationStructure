package com.example.basicapplicationstructure

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.basicapplicationstructure.presentation.MoviesData
import com.example.basicapplicationstructure.presentation.MoviesState
import com.example.basicapplicationstructure.presentation.MoviesViewModel
import com.example.basicapplicationstructure.ui.theme.BasicApplicationStructureTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val moviesViewModel by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesViewModel.getMoviesList()

        setContent {
            val state by moviesViewModel.state.collectAsStateWithLifecycle()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                moviesViewModel.error.collectLatest {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }

            BasicApplicationStructureTheme {
                MoviesMainScreenComposable(
                    state = state,
                    onBackPressed = {
                        onBackPressedDispatcher.onBackPressed()
                    }
                )
            }
        }
    }
}

@Composable
private fun MoviesMainScreenComposable(state: MoviesState, onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            Toolbar(
                toolsText = "Movies Data",
                onBackPressed = {
                    onBackPressed.invoke()
                }
            )
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            MoviesListComposable(
                modifier = Modifier.padding(innerPadding),
                moviesList = state.moviesList
            )
        }
    }
}

@Preview
@Composable
fun Toolbar(toolsText:String = "", onBackPressed:() -> Unit = {}) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(5.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(0.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "backArrow",
                modifier = Modifier.clickable{
                    onBackPressed.invoke()
                }
            )
            Text(
                text = toolsText,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}

@Composable
fun MoviesListComposable(modifier: Modifier = Modifier, moviesList: List<MoviesData>? = listOf<MoviesData>()) {

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        /*item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Movies Data",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
            }
        }*/
        items(moviesList?:emptyList<MoviesData>()) {
            MovieItem(it)
        }
    }
}

@Composable
fun MovieItem(movie: MoviesData) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .testTag("movieItem"),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = if (movie.images.isNullOrEmpty()) "" else movie.images.get(0),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "thumbnail",
                modifier = Modifier
                    .size(150.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )

            Text(
                text = "Title: ${movie.title}",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Red,
                modifier = Modifier.testTag("movieItemText")
            )

            Text(
                text = "Year: ${movie.year}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 5.dp)
            )

            Text(
                text = "Released Date: ${movie.released}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 5.dp)
            )

            Text(
                text = "Runtime: ${movie.runtime}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                text = "Actors: ${movie.actors}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            )
            Text(
                text = "Language: ${movie.language}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                text = "ImdbRating: ${movie.imdbRating}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                text = "Movie Type: ${movie.type}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }


}

@Preview
@Composable
private fun PreviewMovieItem() {
    val mockData = MoviesData(
        title = "Avatar",
        year = "2022",
        rated = "PG-13",
        released = "18 Dec 2009",
        runtime = "162 min",
        actors = "Sam Worthington, Zoe Saldana, Sigourney Weaver, Stephen Lang",
        language = "English, Spanish",
        imdbRating = "7.9",
        type = "movie",
        listOf<String>(
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"

        )
    )

    MovieItem(movie = mockData)
}

@Preview
@Composable
private fun PreviewMoviesMainScreenComposable() {

    val mockData = MoviesData(
        title = "Avatar",
        year = "2022",
        rated = "PG-13",
        released = "18 Dec 2009",
        runtime = "162 min",
        actors = "Sam Worthington, Zoe Saldana, Sigourney Weaver, Stephen Lang",
        language = "English, Spanish",
        imdbRating = "7.9",
        type = "movie",
        listOf<String>(
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"

        )
    )
    val list = listOf<MoviesData>(mockData,mockData,mockData,mockData,mockData,mockData,mockData)

    val moviesState = MoviesState(isLoading = false, moviesList = list)

    MoviesMainScreenComposable(state = moviesState, onBackPressed = {})
}