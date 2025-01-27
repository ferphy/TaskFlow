package com.example.diaryapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.diaryapp.screens.details.DetailScreen
import com.example.diaryapp.screens.home.HomeScreen
import com.example.diaryapp.screens.home.HomeViewModel
import com.example.diaryapp.screens.newTask.NewTaskScreen
import com.example.diaryapp.screens.newTask.NewTaskViewModel

@Composable
fun DiaryAppNavigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute
    ){
        composable<HomeScreenRoute>{

            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                homeViewModel = homeViewModel,
                navController = navController
            )
        }

        composable<DetailScreenRoute>{
            val args = it.toRoute<DetailScreenRoute>()
            DetailScreen(
                id = args.id
            )
        }

        composable<NewTaskScreenRoute>{
            val newTaskViewModel: NewTaskViewModel = hiltViewModel()
            NewTaskScreen(
                newTaskViewModel = newTaskViewModel,
                navController = navController
            )
        }

    }
}