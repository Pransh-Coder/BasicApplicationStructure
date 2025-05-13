package com.example.basicapplicationstructure.data.repository

import com.example.basicapplicationstructure.data.MoviesRepositoryInterface
import com.example.basicapplicationstructure.data.localDataSource.MoviesEntity
import com.example.basicapplicationstructure.data.remoteDataSource.MoviesResponse
import com.example.basicapplicationstructure.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMoviesRepository : MoviesRepositoryInterface {

    private var throwErr: Boolean = false
    private var noInternetConnection = false

    private val fakeMoviesListFromDB = mutableListOf<MoviesEntity>(
        MoviesEntity(
            title = "Avatar",
            year = "2009",
            rated = "PG-13",
            released = "18 Dec 2009",
            runtime = "162 min",
            actors = "Sam Worthington, Zoe Saldana, Sigourney Weaver, Stephen Lang",
            language = "English, Spanish",
            imdbRating = "7.9",
            type = "movie",
            image = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"

        ),
        MoviesEntity(
            title = "I Am Legend",
            year = "2007",
            rated = "PG-13",
            released = "14 Dec 2007",
            runtime = "101 min",
            actors = "Will Smith, Alice Braga, Charlie Tahan, Salli Richardson-Whitfield",
            language = "English",
            imdbRating = "7.2",
            type = "movie",
            image = "https://images-na.ssl-images-amazon.com/images/M/MV5BMTI0NTI4NjE3NV5BMl5BanBnXkFtZTYwMDA0Nzc4._V1_.jpg"

        ),
        MoviesEntity(
            title = "300",
            year = "2006",
            rated = "R",
            released = "09 Mar 2007",
            runtime = "117 min",
            actors = "Gerard Butler, Lena Headey, Dominic West, David Wenham",
            language = "English",
            imdbRating = "7.7",
            type = "movie",
            image = "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMwNTg5MzMwMV5BMl5BanBnXkFtZTcwMzA2NTIyMw@@._V1_SX1777_CR0,0,1777,937_AL_.jpg",
        )
    )

    val mockMovieListFromNetwork = listOf(
        MoviesResponse(
            title = "Avatar",
            year = "2009",
            rated = "PG-13",
            released = "18 Dec 2009",
            runtime = "162 min",
            genre = "Action, Adventure, Fantasy",
            director = "James Cameron",
            writer = "James Cameron",
            actors = "Sam Worthington, Zoe Saldana, Sigourney Weaver, Stephen Lang",
            plot = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
            language = "English, Spanish",
            country = "USA, UK",
            awards = "Won 3 Oscars. Another 80 wins & 121 nominations.",
            poster = "http://ia.media-imdb.com/images/M/MV5BMTYwOTEwNjAzMl5BMl5BanBnXkFtZTcwODc5MTUwMw@@._V1_SX300.jpg",
            metascore = "83",
            imdbRating = "7.9",
            imdbVotes = "890,617",
            imdbID = "tt0499549",
            type = "movie",
            response = true,
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
            )
        ),
        MoviesResponse(
            title = "I Am Legend",
            year = "2007",
            rated = "PG-13",
            released = "14 Dec 2007",
            runtime = "101 min",
            genre = "Drama, Horror, Sci-Fi",
            director = "Francis Lawrence",
            writer = "Mark Protosevich (screenplay), Akiva Goldsman (screenplay), Richard Matheson (novel), John William Corrington, Joyce Hooper Corrington",
            actors = "Will Smith, Alice Braga, Charlie Tahan, Salli Richardson-Whitfield",
            plot = "Years after a plague kills most of humanity and transforms the rest into monsters, the sole survivor in New York City struggles valiantly to find a cure.",
            language = "English",
            country = "USA",
            awards = "9 wins & 21 nominations.",
            poster = "http://ia.media-imdb.com/images/M/MV5BMTU4NzMyNDk1OV5BMl5BanBnXkFtZTcwOTEwMzU1MQ@@._V1_SX300.jpg",
            metascore = "65",
            imdbRating = "7.2",
            imdbVotes = "533,874",
            imdbID = "tt0480249",
            type = "movie",
            response = false,
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTI0NTI4NjE3NV5BMl5BanBnXkFtZTYwMDA0Nzc4._V1_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTIwMDg2MDU4M15BMl5BanBnXkFtZTYwMTA0Nzc4._V1_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTc5MDM1OTU5OV5BMl5BanBnXkFtZTYwMjA0Nzc4._V1_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTA0MTI2NjMzMzFeQTJeQWpwZ15BbWU2MDMwNDc3OA@@._V1_.jpg"
            )
        ),
        MoviesResponse(
            title = "300",
            year = "2006",
            rated = "R",
            released = "09 Mar 2007",
            runtime = "117 min",
            genre = "Action, Drama, Fantasy",
            director = "Zack Snyder",
            writer = "Zack Snyder (screenplay), Kurt Johnstad (screenplay), Michael Gordon (screenplay), Frank Miller (graphic novel), Lynn Varley (graphic novel)",
            actors = "Gerard Butler, Lena Headey, Dominic West, David Wenham",
            plot = "King Leonidas of Sparta and a force of 300 men fight the Persians at Thermopylae in 480 B.C.",
            language = "English",
            country = "USA",
            awards = "16 wins & 42 nominations.",
            poster = "http://ia.media-imdb.com/images/M/MV5BMjAzNTkzNjcxNl5BMl5BanBnXkFtZTYwNDA4NjE3._V1_SX300.jpg",
            metascore = "52",
            imdbRating = "7.7",
            imdbVotes = "611,046",
            imdbID = "tt0416449",
            type = "movie",
            response = true,
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMwNTg5MzMwMV5BMl5BanBnXkFtZTcwMzA2NTIyMw@@._V1_SX1777_CR0,0,1777,937_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTQwNTgyNTMzNF5BMl5BanBnXkFtZTcwNDA2NTIyMw@@._V1_SX1777_CR0,0,1777,935_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTc0MjQzOTEwMV5BMl5BanBnXkFtZTcwMzE2NTIyMw@@._V1_SX1777_CR0,0,1777,947_AL_.jpg"
            )
        )
    )

    override suspend fun getMoviesListFromNetwork(): Resource<List<MoviesResponse>> {
        if (throwErr){
            return Resource.Error(errorMessage = "Exception in API call")
        }
        else if(noInternetConnection) {
            return Resource.NoInternetException()
        }
        else{
            return Resource.Success(mockMovieListFromNetwork)
        }
    }

    override suspend fun getMoviesListFromLocalDB(): Flow<Resource<List<MoviesEntity>>> {
        return flow {
            if (throwErr){
                emit(Resource.Error(errorMessage = "Excep in DB"))
            }
            else if(noInternetConnection) {
                emit(Resource.NoInternetException())
            }
            else{
                emit(Resource.Success(fakeMoviesListFromDB))
            }
        }
    }

    fun setUnsetError(){
        throwErr = throwErr.not()
    }

    fun setNoInternetErr(){
        noInternetConnection = noInternetConnection.not()
    }

}