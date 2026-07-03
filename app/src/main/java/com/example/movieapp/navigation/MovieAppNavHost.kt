package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.favourite.navigation.favouriteGraph
import com.example.movieapp.home.navigation.homeGraph
import com.example.movieapp.moviedetail.navigation.movieDetailGraph
import com.example.movieapp.navigation.favourite.FavouriteRoute
import com.example.movieapp.navigation.home.HomeRoute
import com.example.movieapp.navigation.moviedetail.MovieDetailRoute
import com.example.movieapp.navigation.splash.SplashRoute
import com.example.movieapp.splash.navigation.splashGraph

@Composable
fun MovieAppNavHost(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        modifier = Modifier
    ){
        splashGraph(
            onNavigateToHome = {
                navController.navigate(HomeRoute.Home){
                    popUpTo(SplashRoute){
                        inclusive = true
                    }
                }
            }
        )

        homeGraph(
            onNavigateToDetail = { id, categoryText ->
                navController.navigate(
                    MovieDetailRoute.MovieDetail(movieId = id, category = categoryText)
                )
            },
            onNavigateToFavorite = {
                navController.navigate(FavouriteRoute.Favourite) {
                    launchSingleTop = true
                }
            }
        )

        favouriteGraph(
            onNavigateToDetails = { id, categoryText ->
                navController.navigate(
                    MovieDetailRoute.MovieDetail(movieId = id, category = categoryText)
                )
            },
            onNavigateToHome = {
                navController.popBackStack(HomeRoute.Home, inclusive = false)
            }
        )

        movieDetailGraph(
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }

}