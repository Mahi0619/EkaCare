package com.eka.care.views.userData


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.eka.care.data.model.User
@Composable
fun UserDetailDialog(
    user: User?,
    onDismiss: () -> Unit
) {
    if (user == null) return

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "User Details") },
        text = {
            Column {
                Text(text = "Name: ${user.name}")
                Text(text = "Age: ${user.age}")
                Text(text = "Date of Birth: ${user.dob}")
                Text(text = "Address: ${user.address}")
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                Button(onClick = { onDismiss() }) {
                    Text("OK")
                }
            }
        }
    )
}




@Composable
fun UserDetailSavedDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Success") },
        text = {
            Column {
                Text(text = "User data saved successfully!")
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { onDismiss() }) {
                    Text("OK")
                }
            }
        }
    )
}
