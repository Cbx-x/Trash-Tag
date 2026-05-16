package com.mindmatrix.app1.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindmatrix.app1.R
import com.mindmatrix.app1.model.WasteReport
import com.mindmatrix.app1.ui.theme.AshokaBlue
import com.mindmatrix.app1.ui.theme.IndiaGreen
import com.mindmatrix.app1.ui.theme.Saffron
import com.mindmatrix.app1.viewmodel.WasteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CleanerHomeScreen(
    viewModel: WasteViewModel,
    onMapClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onViewAllClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val reports by viewModel.reports.collectAsState()
    
    val pendingCount = reports.count { it.status == "Pending" }
    val inProgressCount = reports.count { it.status == "In Progress" }
    val resolvedCount = reports.count { it.status == "Resolved" }

    var showMenu by remember { mutableStateOf(false) }

    BackHandler {}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Recycling, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.role_cleaner), style = MaterialTheme.typography.titleLarge) 
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Menu */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = onNotificationsClick) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.logout)) },
                                onClick = {
                                    showMenu = false
                                    onLogout()
                                },
                                leadingIcon = { 
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ExitToApp, 
                                        contentDescription = null 
                                    ) 
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AshokaBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = { 
            CleanerBottomNavigation(
                onMapClick = onMapClick,
                onNotificationsClick = onNotificationsClick,
                onProfileClick = onProfileClick
            ) 
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.hello_cleaner),
                style = MaterialTheme.typography.headlineMedium.copy(color = AshokaBlue)
            )
            Text(
                text = stringResource(R.string.cleaner_home_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatBox(value = reports.size.toString(), label = "Total", modifier = Modifier.weight(1f), color = AshokaBlue)
                StatBox(value = pendingCount.toString(), label = stringResource(R.string.pending_reports).split(" ").first(), modifier = Modifier.weight(1f), color = Saffron)
                StatBox(value = inProgressCount.toString(), label = "Progress", modifier = Modifier.weight(1f), color = Color(0xFF1976D2))
                StatBox(value = resolvedCount.toString(), label = "Done", modifier = Modifier.weight(1f), color = IndiaGreen)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.assigned_reports),
                    style = MaterialTheme.typography.titleLarge.copy(color = AshokaBlue)
                )
                Text(
                    text = stringResource(R.string.view_all),
                    style = MaterialTheme.typography.labelLarge.copy(color = IndiaGreen, fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable { onViewAllClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (reports.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No reports assigned", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(reports) { report ->
                        CleanerReportCard(report)
                    }
                }
            }
        }
    }
}

@Composable
fun StatBox(value: String, label: String, modifier: Modifier = Modifier, color: Color = IndiaGreen) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = value, style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp), color = color)
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CleanerReportCard(report: WasteReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, contentDescription = null, tint = Color.LightGray)
            }
            
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = report.title, style = MaterialTheme.typography.titleMedium, color = Color.Black)
                Text(text = report.locationName, style = MaterialTheme.typography.labelSmall, color = Color.Gray, maxLines = 1)
                Text(text = "1.2 km away", style = MaterialTheme.typography.labelSmall, color = IndiaGreen)
            }

            StatusBadge(report.status)
        }
    }
}

@Composable
fun CleanerBottomNavigation(
    onMapClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(stringResource(R.string.home_btn)) },
            selected = true,
            onClick = { /* Home */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AshokaBlue,
                selectedTextColor = AshokaBlue,
                indicatorColor = Color(0xFFE3F2FD)
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
            label = { Text("Map") },
            selected = false,
            onClick = onMapClick,
            colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifications") },
            label = { Text(stringResource(R.string.notif_title)) },
            selected = false,
            onClick = onNotificationsClick,
            colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text(stringResource(R.string.profile_title)) },
            selected = false,
            onClick = onProfileClick,
            colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray)
        )
    }
}
