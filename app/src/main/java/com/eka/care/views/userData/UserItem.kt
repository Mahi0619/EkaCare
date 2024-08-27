package com.eka.care.views.userData


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.eka.care.data.model.User

@Composable
fun UserItem(user: User, isEven: Boolean, onEdit: (User) -> Unit, onDelete: (User) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    val backgroundBrush = if (isEven) {
        Brush.verticalGradient(listOf(Color.LightGray, Color.White))
    } else {
        Brush.verticalGradient(listOf(Color.White, Color.LightGray))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Show the dialog when the item is clicked
                showDialog = true
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(brush = backgroundBrush)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Name: ${user.name}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row {
                        IconButton(onClick = { onEdit(user) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = { onDelete(user) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                Text(text = "Age: ${user.age}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Date of Birth: ${user.dob}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = "Address: ${user.address}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

    // Show the dialog if showDialog is true
    if (showDialog) {
        UserDetailDialog(
            user = user,
            onDismiss = { showDialog = false }
        )
    }
}







/*@Composable
fun UserItem(user: User, isEven: Boolean) {
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val backgroundBrush = if (isEven) {
        Brush.verticalGradient(listOf(Color.LightGray, Color.White))
    } else {
        Brush.verticalGradient(listOf(Color.White, Color.LightGray))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Show the dialog when the item is clicked
                showDialog = true
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(brush = backgroundBrush)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(text = "Name: ${user.name}", style = MaterialTheme.typography.titleMedium)

                Text(text = "Age: ${user.age}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Date of Birth: ${user.dob}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = "Address: ${user.address}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

    // Show the dialog if showDialog is true
    if (showDialog) {
        UserDetailDialog(
            user = user,
            onDismiss = { showDialog = false }
        )
    }
}*/
