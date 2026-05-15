package com.mindmatrix.app1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindmatrix.app1.ui.theme.AshokaBlue
import com.mindmatrix.app1.ui.theme.IndiaGreen
import com.mindmatrix.app1.ui.theme.Saffron

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AshokaBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Today",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                )
            }
            
            items(sampleNotificationsToday) { notification ->
                NotificationItem(notification)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Yesterday",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                )
            }

            items(sampleNotificationsYesterday) { notification ->
                NotificationItem(notification)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Status dot
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(10.dp)
                .background(notification.color, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        
        Text(
            text = notification.time,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}

data class NotificationData(
    val title: String,
    val message: String,
    val time: String,
    val color: Color
)

val sampleNotificationsToday = listOf(
    NotificationData(
        "New report assigned to you",
        "MG Road, Belagavi",
        "10:30 AM",
        Saffron
    ),
    NotificationData(
        "Report status updated",
        "Overflowing Bin - In Progress",
        "09:15 AM",
        Color(0xFF1976D2)
    ),
    NotificationData(
        "Report completed successfully",
        "Waste on Footpath",
        "08:45 AM",
        IndiaGreen
    )
)

val sampleNotificationsYesterday = listOf(
    NotificationData(
        "New report assigned to you",
        "Camp Road, Belagavi",
        "05:20 PM",
        Saffron
    ),
    NotificationData(
        "Report completed successfully",
        "Garbage Near Park",
        "04:10 PM",
        IndiaGreen
    )
)
