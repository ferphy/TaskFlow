package com.example.diaryapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diaryapp.screens.HomeScreen

@Composable
fun DiaryAppNavigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ){
        composable<HomeScreen>{
            HomeScreen()
        }
    }
}