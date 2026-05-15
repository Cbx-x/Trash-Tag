package com.mindmatrix.app1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindmatrix.app1.R
import com.mindmatrix.app1.ui.theme.AshokaBlue
import com.mindmatrix.app1.ui.theme.IndiaGreen
import com.mindmatrix.app1.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AuthViewModel,
    onBackClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val userState by viewModel.userState
    val user = userState?.getOrNull()
    val isReporter = user?.role == "Reporter"
    val themeColor = if (isReporter) IndiaGreen else AshokaBlue

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile_title), style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(2.dp)
            ) {
                Surface(modifier = Modifier.fillMaxSize(), shape = CircleShape, color = themeColor.copy(0.1f)) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = themeColor, modifier = Modifier.padding(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = user?.fullName ?: "User Name", style = MaterialTheme.typography.titleLarge.copy(color = Color.Black))
            Text(text = user?.email ?: "user@example.com", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            
            Surface(
                modifier = Modifier.padding(top = 8.dp),
                shape = RoundedCornerShape(16.dp),
                color = themeColor.copy(0.1f)
            ) {
                Text(
                    text = user?.role ?: "User",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = themeColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stats Row matching images 9 & 12
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat(
                    label = if (isReporter) stringResource(R.string.recent_reports) else stringResource(R.string.reports_done), 
                    value = if (isReporter) "25" else "32", 
                    color = themeColor
                )
                ProfileStat(
                    label = if (isReporter) "Resolved" else stringResource(R.string.points), 
                    value = if (isReporter) "15" else "120", 
                    color = themeColor
                )
                ProfileStat(
                    label = if (isReporter) stringResource(R.string.points) else stringResource(R.string.days_active), 
                    value = if (isReporter) "250" else "15", 
                    color = themeColor
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Role-specific Action List
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                ProfileActionItem(
                    icon = if (isReporter) Icons.Default.Assignment else Icons.Default.History, 
                    label = if (isReporter) stringResource(R.string.my_reports) else stringResource(R.string.my_work_history), 
                    onClick = {}
                )
                ProfileActionItem(
                    icon = if (isReporter) Icons.Default.EmojiEvents else Icons.Default.AccountBalanceWallet, 
                    label = if (isReporter) stringResource(R.string.my_rewards) else stringResource(R.string.my_points), 
                    onClick = {}
                )
                
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(0.4f))
                
                ProfileActionItem(icon = Icons.Default.Edit, label = stringResource(R.string.edit_profile), onClick = {})
                ProfileActionItem(icon = Icons.Default.Lock, label = stringResource(R.string.change_p), onClick = {})
                ProfileActionItem(icon = Icons.Default.HelpCenter, label = stringResource(R.string.h_support), onClick = {})
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ProfileActionItem(
                    icon = Icons.AutoMirrored.Filled.Logout, 
                    label = stringResource(R.string.logout), 
                    color = Color.Red,
                    onClick = onLogout
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ProfileStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp), color = color)
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}

@Composable
fun ProfileActionItem(
    icon: ImageVector,
    label: String,
    color: Color = Color.Black,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = if (color == Color.Red) color else IndiaGreen, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = color, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(20.dp))
    }
}
