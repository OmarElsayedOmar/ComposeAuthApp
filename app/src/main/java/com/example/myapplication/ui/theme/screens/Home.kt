package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.model.Movie
import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.ui.theme.viewmodel.MovieViewModel
import com.example.myapplication.ui.theme.viewmodel.MovieViewModelFactory
import kotlin.collections.filter
import kotlin.text.contains
import kotlin.text.isEmpty


@Composable
fun HomeScreen() {


    val viewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(
            MovieRepository(RetrofitInstance.api)
        )
    )

    var searchQuery by rememberSaveable { mutableStateOf("") }
    val popular by viewModel.popularMovies.collectAsState()
    val topRated by viewModel.topRatedMovies.collectAsState()
    val upcoming by viewModel.upcomingMovies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getMovies("ed0579c5972d5b789fec9a33235fcf3f")
    }
    when {
        isLoading -> LoadingView()
        isError -> ErrorView(onRetry = {
            viewModel.getMovies("ed0579c5972d5b789fec9a33235fcf3f")
        })

        else -> MoviesContent(
            searchQuery = searchQuery,
            popular = popular,
            topRated = topRated,
            upcoming = upcoming,
            onSearchChange = { searchQuery = it }
        )
    }

}

@Composable
fun MovieSection(title: String, movies: List<Movie>) {
    Column(modifier = Modifier
        .statusBarsPadding()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow {
            items(movies) { movie ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(120.dp)
                ) {
                    val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
                    Image(
                        painter = rememberAsyncImagePainter(posterUrl),
                        contentDescription = movie.title,
                        modifier = Modifier
                            .size(150.dp)
                    )
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFF7C1C3E))
    }
}
@Composable
fun ErrorView(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("No Internet Connection", color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
            androidx.compose.material3.Button(onClick ={ onRetry() }){
                Text("Retry")
            }
        }
    }
}
@Composable
fun MoviesContent(
    searchQuery: String,
    popular: List<Movie>,
    topRated: List<Movie>,
    upcoming: List<Movie>,
    onSearchChange: (String) -> Unit
) {
    LazyColumn {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .statusBarsPadding(),
                placeholder = { Text("Search movies...") },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF812245),
                    unfocusedBorderColor = Color(0xFFB22050),
                    cursorColor = Color(0xFFD0255E)
                )
            )
        }

        item {
            val filteredPopular =
                if (searchQuery.isEmpty()) popular
                else popular.filter { it.title.contains(searchQuery, ignoreCase = true) }

            MovieSection("Popular", filteredPopular)
        }

        item {
            val filteredTopRated =
                if (searchQuery.isEmpty()) topRated
                else topRated.filter { it.title.contains(searchQuery, ignoreCase = true) }

            MovieSection("Top Rated", filteredTopRated)
        }

        item {
            val filteredUpcoming =
                if (searchQuery.isEmpty()) upcoming
                else upcoming.filter { it.title.contains(searchQuery, ignoreCase = true) }

            MovieSection("Upcoming", filteredUpcoming)
        }
    }
}



