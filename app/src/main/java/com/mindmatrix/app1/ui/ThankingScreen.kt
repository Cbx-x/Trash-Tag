package com.mindmatrix.app1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindmatrix.app1.ui.theme.IndiaGreen
import com.mindmatrix.app1.ui.theme.Saffron

@Composable
fun ThankingScreen(
    onHomeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success Icon / Illustration
        Box(
            modifier = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            // Green circle with check
            Surface(
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                color = IndiaGreen.copy(alpha = 0.1f)
            ) {}
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = IndiaGreen
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(20.dp)
                )
            }
            
            // Dustbin icon below
            Icon(
                imageVector = Icons.Default.Recycling,
                contentDescription = null,
                tint = IndiaGreen,
                modifier = Modifier.size(40.dp).align(Alignment.BottomCenter).offset(y = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Thank You!",
            style = MaterialTheme.typography.displayMedium.copy(
                color = IndiaGreen,
                fontWeight = FontWeight.Bold
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your report has been\nsubmitted successfully.",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Together, we make our city\ncleaner and greener.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = onHomeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Saffron),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "HOME",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
