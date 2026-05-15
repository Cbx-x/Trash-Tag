package com.mindmatrix.app1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mindmatrix.app1.ui.*
import com.mindmatrix.app1.ui.theme.App1Theme
import com.mindmatrix.app1.ui.theme.AshokaBlue
import com.mindmatrix.app1.ui.theme.IndiaGreen
import com.mindmatrix.app1.viewmodel.AuthViewModel
import com.mindmatrix.app1.viewmodel.WasteViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val wasteViewModel: WasteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App1Theme {
                var currentScreen by remember { mutableStateOf("splash_hold") }
                var userRole by remember { mutableStateOf("") }

                val userState by authViewModel.userState

                LaunchedEffect(Unit) {
                    delay(3000)
                    if (authViewModel.isUserLoggedIn()) {
                        authViewModel.fetchCurrentUserProfile()
                    } else {
                        currentScreen = "login"
                    }
                }

                LaunchedEffect(userState) {
                    userState?.let { result ->
                        result.onSuccess { user ->
                            userRole = user.role
                            if (currentScreen == "splash_hold" || currentScreen == "login" || currentScreen == "register") {
                                currentScreen = if (user.role == "Reporter") "reporter_home" else "cleaner_home"
                            }
                        }.onFailure {
                            if (currentScreen != "register" && currentScreen != "login") {
                                currentScreen = "login"
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        "splash_hold" -> SplashScreenUI()
                        "login" -> LoginScreen(
                            viewModel = authViewModel,
                            onRegisterClick = { currentScreen = "register" },
                            onLoginSuccess = { role ->
                                userRole = role
                                currentScreen = if (role == "Reporter") "reporter_home" else "cleaner_home"
                            }
                        )
                        "register" -> RegisterScreen(
                            viewModel = authViewModel,
                            onLoginClick = { currentScreen = "login" },
                            onRegisterSuccess = { role ->
                                userRole = role
                                currentScreen = if (role == "Reporter") "reporter_home" else "cleaner_home"
                            }
                        )
                        "reporter_home" -> ReporterHomeScreen(
                            authViewModel = authViewModel,
                            wasteViewModel = wasteViewModel,
                            onReportWasteClick = { currentScreen = "report_waste" },
                            onMapClick = { currentScreen = "map" },
                            onRewardsClick = { currentScreen = "rewards" },
                            onNotificationsClick = { currentScreen = "notifications" },
                            onProfileClick = { currentScreen = "profile" },
                            onViewAllClick = { currentScreen = "reports_list" },
                            onLogout = {
                                authViewModel.logout()
                                currentScreen = "login"
                            }
                        )
                        "cleaner_home" -> CleanerHomeScreen(
                            viewModel = wasteViewModel,
                            onMapClick = { currentScreen = "map" },
                            onNotificationsClick = { currentScreen = "notifications" },
                            onProfileClick = { currentScreen = "profile" },
                            onViewAllClick = { currentScreen = "reports_list" },
                            onLogout = {
                                authViewModel.logout()
                                currentScreen = "login"
                            }
                        )
                        "report_waste" -> ReportWasteScreen(
                            viewModel = wasteViewModel,
                            authViewModel = authViewModel,
                            onBackClick = { currentScreen = "reporter_home" },
                            onSuccess = { currentScreen = "thanking" }
                        )
                        "thanking" -> ThankingScreen(
                            onHomeClick = { currentScreen = "reporter_home" }
                        )
                        "map" -> MapScreen(
                            viewModel = wasteViewModel,
                            userRole = userRole,
                            onBackClick = { 
                                currentScreen = if (userRole == "Reporter") "reporter_home" else "cleaner_home" 
                            }
                        )
                        "profile" -> ProfileScreen(
                            viewModel = authViewModel,
                            onBackClick = { 
                                currentScreen = if (userRole == "Reporter") "reporter_home" else "cleaner_home" 
                            },
                            onLogout = {
                                authViewModel.logout()
                                currentScreen = "login"
                            }
                        )
                        "rewards" -> RewardsScreen(
                            viewModel = authViewModel,
                            onBackClick = { currentScreen = "reporter_home" }
                        )
                        "notifications" -> NotificationScreen(
                            onBackClick = { 
                                currentScreen = if (userRole == "Reporter") "reporter_home" else "cleaner_home" 
                            }
                        )
                        "reports_list" -> ReportsListScreen(
                            viewModel = wasteViewModel,
                            userRole = userRole,
                            onBackClick = { 
                                currentScreen = if (userRole == "Reporter") "reporter_home" else "cleaner_home" 
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreenUI() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = IndiaGreen,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "PARYAVARAN",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = AshokaBlue,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
            )
            Text(
                text = "KAVALU",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = IndiaGreen,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Together for a",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Text(
                text = "Cleaner Tomorrow",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(60.dp))

            CircularProgressIndicator(
                color = IndiaGreen,
                strokeWidth = 3.dp,
                modifier = Modifier.size(40.dp)
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(modifier = Modifier.size(120.dp, 60.dp).background(Color(0xFFE8F5E9), RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp)))
            Box(modifier = Modifier.size(150.dp, 80.dp).offset(x=(-30).dp).background(Color(0xFFC8E6C9), RoundedCornerShape(topStart = 120.dp, topEnd = 120.dp)))
        }
    }
}
