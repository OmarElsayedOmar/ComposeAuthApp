package com.example.myapplication.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Movie
import com.example.myapplication.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _topRatedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedMovies: StateFlow<List<Movie>> = _topRatedMovies

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies: StateFlow<List<Movie>> = _upcomingMovies

    private val _isLoading=MutableStateFlow(true)
    val isLoading:StateFlow<Boolean> =_isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError



    fun getMovies(apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            try {
                val popular = repository.getPopularMovies(apiKey).results
                val top = repository.getTopRatedMovies(apiKey).results
                val upcoming = repository.getUpcomingMovies(apiKey).results
                _popularMovies.value = popular
                _topRatedMovies.value = top
                _upcomingMovies.value = upcoming

            } catch (e: Exception) {
                e.printStackTrace()
                _isError.value = true
            }finally {
                _isLoading.value=false
            }
        }
    }




}