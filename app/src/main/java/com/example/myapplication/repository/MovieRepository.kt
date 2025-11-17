package com.example.myapplication.repository

import com.example.myapplication.data.remote.MovieApi

class MovieRepository (private val api: MovieApi) {
    suspend fun getPopularMovies(apiKey: String) =
        api.getPopularMovies(apiKey)

    suspend fun getTopRatedMovies(apiKey: String) =
        api.getTopRatedMovies(apiKey)

    suspend fun getUpcomingMovies(apiKey: String) =
        api.getUpcomingMovies(apiKey)

}