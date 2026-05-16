package com.mindmatrix.app1.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mindmatrix.app1.R
import com.mindmatrix.app1.ui.theme.IndiaGreen
import com.mindmatrix.app1.ui.theme.Saffron
import com.mindmatrix.app1.viewmodel.AuthViewModel
import com.mindmatrix.app1.viewmodel.WasteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReporterHomeScreen(
    authViewModel: AuthViewModel,
    wasteViewModel: WasteViewModel,
    onReportWasteClick: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onRewardsClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onViewAllClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val reports by wasteViewModel.reports.collectAsState()
    val userState by authViewModel.userState
    val currentUser = userState?.getOrNull()

    val myReports = remember(reports, currentUser) {
        reports.filter { it.reporterId == currentUser?.uid }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Eco, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.brand_paryavaran), style = MaterialTheme.typography.titleLarge) 
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IndiaGreen,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = { 
            ReporterBottomNavigation(
                onHomeClick = { /* Already here */ },
                onMapClick = onMapClick,
                onRewardsClick = onRewardsClick,
                onProfileClick = onProfileClick
            ) 
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onReportWasteClick,
                containerColor = Saffron,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(64.dp).offset(y = 40.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Report", modifier = Modifier.size(32.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
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
                text = stringResource(R.string.hello_reporter),
                style = MaterialTheme.typography.headlineMedium.copy(color = IndiaGreen)
            )
            Text(
                text = stringResource(R.string.home_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HomeActionCard(
                    title = stringResource(R.string.report_waste_card),
                    subtitle = stringResource(R.string.report_waste_subtitle),
                    icon = Icons.Default.Assignment,
                    iconColor = IndiaGreen,
                    modifier = Modifier.weight(1f).clickable { onReportWasteClick() }
                )
                HomeActionCard(
                    title = stringResource(R.string.my_rewards_card),
                    subtitle = stringResource(R.string.my_rewards_subtitle),
                    icon = Icons.Default.EmojiEvents,
                    iconColor = Saffron,
                    modifier = Modifier.weight(1f).clickable { onRewardsClick() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.recent_reports),
                    style = MaterialTheme.typography.titleLarge.copy(color = IndiaGreen)
                )
                Text(
                    text = stringResource(R.string.view_all),
                    style = MaterialTheme.typography.labelLarge.copy(color = IndiaGreen, fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable { onViewAllClick() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (myReports.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No reports yet", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(myReports.take(5)) { report ->
                        ReportCard(report)
                    }
                }
            }
        }
    }
}

@Composable
fun ReporterBottomNavigation(
    onHomeClick: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onRewardsClick: () -> Unit = {},
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
            onClick = onHomeClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = IndiaGreen,
                selectedTextColor = IndiaGreen,
                indicatorColor = Color(0xFFE8F5E9)
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
            icon = { Icon(Icons.Default.EmojiEvents, contentDescription = "Rewards") },
            label = { Text("Rewards") },
            selected = false,
            onClick = onRewardsClick,
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
