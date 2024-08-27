package com.eka.care.views.userData

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eka.care.data.model.User
import com.eka.care.data.viewModel.UserViewModel




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserViewModel, navController: NavController) {
    val users by viewModel.allUsers.collectAsState(emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Users List") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Gray // Set the background color here
                )
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                if (users.isNullOrEmpty()) {
                    // Show message and button when no users are found
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Data not found", style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { navController.navigate("inputForm") }) {
                            Text(text = "Add Person")
                        }
                    }
                } else {
                    LazyColumn {
                        items(users.withIndex().toList()) { (index, user) ->
                            UserItem(
                                user = user,
                                isEven = index % 2 == 0,
                                onEdit = { userToEdit ->
                                    // Navigate to UpdateFormScreen with userId
                                    navController.navigate("updateFormScreen/${userToEdit.id}")
                                },
                                onDelete = { userToDelete ->
                                    // Handle delete action
                                    viewModel.deleteUser(userToDelete)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}



/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserViewModel, navController: NavController) {
    val users by viewModel.allUsers.collectAsState(emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Users List") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Gray // Set the background color here
                )
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                if (users.isNullOrEmpty()) {
                    // Show message and button when no users are found
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Data not found", style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { navController.navigate("inputForm") }) {
                            Text(text = "Add Person")
                        }
                    }
                } else {
                    // Show list of users if the list is not empty
                    */
/*LazyColumn {
                        items(users.withIndex().toList()) { (index, user) ->
                            UserItem(
                                user = user,
                                isEven = index % 2 == 0,
                                //onDelete = { userToDelete ->
                                   // viewModel.deleteUser(userToDelete) // Call delete function in ViewModel
                                //}
                            )
                        }
                    }*//*


                    LazyColumn {
                        items(users.withIndex().toList()) { (index, user) ->
                            UserItem(
                                user = user,
                                isEven = index % 2 == 0,
                                onEdit = { userToEdit ->
                                    // Handle edit action
                                    // e.g., navigate to an edit screen with user details
                                   // viewModel.updateUser(user)
                                },
                                onDelete = { userToDelete ->
                                    // Handle delete action
                                    viewModel.deleteUser(userToDelete)
                                }
                            )
                        }
                    }

                }
            }
        }
    )
}
*/



