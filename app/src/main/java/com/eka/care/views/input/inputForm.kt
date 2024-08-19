package com.eka.care.views.input
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eka.care.data.viewModel.UserViewModel
import com.eka.care.views.input.helper.TextFieldComponent
import com.eka.care.views.userData.UserDetailSavedDialog
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputForm(viewModel: UserViewModel, navController: NavController) {
    val showSuccessDialog by viewModel.showSuccessDialog.collectAsState()

    val name by viewModel.name.collectAsState()
    val age by viewModel.age.collectAsState()
    val dob by viewModel.dob.collectAsState()
    val address by viewModel.address.collectAsState()

    val nameError by viewModel.nameError.collectAsState()
    val ageError by viewModel.ageError.collectAsState()
    val dobError by viewModel.dobError.collectAsState()
    val addressError by viewModel.addressError.collectAsState()

    val context = LocalContext.current
    // State for handling back press


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Add User") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {

                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Gray // Set the background color here
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValues), // Apply padding from Scaffold
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TextFieldComponent for Name
                TextFieldComponent(
                    text = name,
                    label = "Name",
                    onChange = { newName -> viewModel.onNameChanged(newName) },
                    hasError = nameError != null,
                    errorText = nameError
                )
                Spacer(modifier = Modifier.height(8.dp))

                // TextFieldComponent for Age with Number keyboard type
                TextFieldComponent(
                    text = age,
                    label = "Age",
                    onChange = { newAge -> viewModel.onAgeChanged(newAge) },
                    keyboardType = KeyboardType.Number,
                    hasError = ageError != null,
                    errorText = ageError
                )
                Spacer(modifier = Modifier.height(8.dp))

                // TextFieldComponent for Date of Birth with onClick to show DatePicker
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.showDatePicker(context) } // Entire Row is clickable
                        .padding(vertical = 8.dp) // Optional: Adjust padding as needed
                ) {
                    OutlinedTextField(
                        value = dob.ifEmpty { "Select Date of Birth" },
                        onValueChange = { onDOBChange -> viewModel.onDobChanged(onDOBChange) },
                        label = { Text("Date of Birth") },
                        readOnly = true, // Make the field read-only
                        modifier = Modifier
                            .weight(1f) // Take up the remaining space
                            .padding(end = 8.dp), // Space between text field and icon
                        isError = dobError != null,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select DOB",
                                modifier = Modifier.clickable { viewModel.showDatePicker(context) }
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // TextFieldComponent for Address
                TextFieldComponent(
                    text = address,
                    label = "Address",
                    onChange = { newAddress -> viewModel.onAddressChanged(newAddress) },
                    hasError = addressError != null,
                    errorText = addressError
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(onClick = { viewModel.saveUser() }) {
                    Text("Save")
                }

                // View Saved Data Button
                Button(onClick = { navController.navigate("userListScreen") }) {
                    Text("View Saved Data")
                }

                // Show the UserDetailSavedDialog when data is saved successfully
                if (showSuccessDialog) {
                    UserDetailSavedDialog(
                        onDismiss = { viewModel.onSuccessDialogDismissed() }
                    )
                }
            }
        }
    )
}