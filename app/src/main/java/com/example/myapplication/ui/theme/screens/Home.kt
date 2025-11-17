package com.example.myapplication.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var searchQuery by remember { mutableStateOf("") }
    val popular by viewModel.popularMovies.collectAsState()
    val topRated by viewModel.topRatedMovies.collectAsState()
    val upcoming by viewModel.upcomingMovies.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getMovies("ed0579c5972d5b789fec9a33235fcf3f")
    }

    LazyColumn {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                placeholder = { Text("Search movies...") },
                        shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF1976D2)
                        ))
        }
        item {
            val filteredPopular = if (searchQuery.isEmpty()) popular
            else popular.filter { it.title.contains(searchQuery, ignoreCase = true) }
            MovieSection(title = "Popular", movies = filteredPopular)
        }
        item {
            val filteredTopRated = if (searchQuery.isEmpty()) topRated
            else topRated.filter { it.title.contains(searchQuery, ignoreCase = true) }
            MovieSection(title = "Top Rated", movies = filteredTopRated)
        }
        item {
            val filteredUpcoming = if (searchQuery.isEmpty()) upcoming
            else upcoming.filter { it.title.contains(searchQuery, ignoreCase = true) }
            MovieSection(title = "Upcoming", movies = filteredUpcoming)
        }
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