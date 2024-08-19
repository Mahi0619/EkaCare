package com.eka.care.views.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.eka.care.R
import com.eka.care.data.viewModel.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(viewModel: UserViewModel, navController: NavHostController) {
    // Show a progress indicator and navigate after a delay
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        navController.navigate("inputForm") {
            // Prevent going back to splash screen
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ekacare),
            contentDescription = "Eka Care Logo"
        )
    }
}


