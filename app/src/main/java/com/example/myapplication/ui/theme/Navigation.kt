@file:Suppress("DEPRECATION")
package com.example.myapplication.ui.theme
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable(
            Routes.splash,
            enterTransition = {fadeIn(animationSpec = tween(1000))},
            exitTransition = {fadeOut(animationSpec = tween(1000))}
        ) {
            SplashScreen(navController)
        }
        composable(
            Routes.login,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(1000))
            },
                    exitTransition = {
                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(1000))
            }
        ) {
            LoginPage(navController)
        }

        composable(
            Routes.signUp,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(1000))
            },
                    exitTransition = {
                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(1000))
            }
        ) {
            SignUp(navController)
        }
    }
}
