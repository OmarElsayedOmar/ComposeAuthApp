package com.example.myapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _topRatedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedMovies: StateFlow<List<Movie>> = _topRatedMovies

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies: StateFlow<List<Movie>> = _upcomingMovies


    fun getMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                _popularMovies.value = RetrofitInstance.api.getPopularMovies(apiKey).results
                _topRatedMovies.value = RetrofitInstance.api.getTopRatedMovies(apiKey).results
                _upcomingMovies.value = RetrofitInstance.api.getUpcomingMovies(apiKey).results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




}