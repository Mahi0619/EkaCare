package com.eka.care.views.input.helper

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eka.care.data.viewModel.UserViewModel
import com.eka.care.views.input.InputForm
import com.eka.care.views.input.UpdateFormScreen
import com.eka.care.views.splash.SplashScreen
import com.eka.care.views.userData.UserListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(viewModel = viewModel, navController = navController)
        }

        composable("inputForm") {
            InputForm(viewModel = viewModel, navController = navController)
        }

        composable("userListScreen") {
            UserListScreen(viewModel = viewModel, navController = navController)
        }

        composable(
            route = "updateFormScreen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            UpdateFormScreen(viewModel = viewModel, navController = navController, userId = userId)
        }
    }
}
