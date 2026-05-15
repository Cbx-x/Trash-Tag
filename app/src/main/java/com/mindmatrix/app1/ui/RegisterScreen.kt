package com.mindmatrix.app1.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mindmatrix.app1.R
import com.mindmatrix.app1.model.User
import com.mindmatrix.app1.ui.theme.IndiaGreen
import com.mindmatrix.app1.ui.theme.Saffron
import com.mindmatrix.app1.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onLoginClick: () -> Unit,
    onRegisterSuccess: (String) -> Unit
) {
    var selectedRole by remember { mutableStateOf("Reporter") }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val userState by viewModel.userState
    val loading by viewModel.loading

    LaunchedEffect(userState) {
        userState?.let { result ->
            if (result.isSuccess) {
                onRegisterSuccess(result.getOrNull()?.role ?: "Reporter")
            } else {
                Toast.makeText(context, "Registration Failed: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onLoginClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IndiaGreen,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(R.string.create_account), 
                style = MaterialTheme.typography.headlineMedium.copy(color = IndiaGreen, fontWeight = FontWeight.Bold)
            )
            Text(
                text = stringResource(R.string.register_subtitle), 
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.select_role), 
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                RoleButton(
                    text = stringResource(R.string.role_reporter),
                    icon = Icons.Default.Person,
                    isSelected = selectedRole == "Reporter",
                    onClick = { selectedRole = "Reporter" },
                    modifier = Modifier.weight(1f)
                )
                RoleButton(
                    text = stringResource(R.string.role_cleaner),
                    icon = Icons.Default.Engineering,
                    isSelected = selectedRole == "Cleaner",
                    onClick = { selectedRole = "Cleaner" },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            RegisterField(
                label = stringResource(R.string.full_name_label), 
                value = fullName, 
                onValueChange = { fullName = it }, 
                placeholder = stringResource(R.string.full_name_placeholder), 
                icon = Icons.Default.Person
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            RegisterField(
                label = stringResource(R.string.email_label), 
                value = email, 
                onValueChange = { email = it }, 
                placeholder = stringResource(R.string.email_placeholder), 
                icon = Icons.Default.Email, 
                keyboardType = KeyboardType.Email
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            RegisterField(
                label = stringResource(R.string.phone_label), 
                value = phoneNumber, 
                onValueChange = { phoneNumber = it }, 
                placeholder = stringResource(R.string.phone_placeholder), 
                icon = Icons.Default.Phone, 
                keyboardType = KeyboardType.Phone
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            RegisterField(
                label = stringResource(R.string.password_label), 
                value = password, 
                onValueChange = { password = it }, 
                placeholder = stringResource(R.string.password_placeholder), 
                icon = Icons.Default.Lock, 
                isPassword = true,
                visible = passwordVisible, 
                onToggleVisible = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = Saffron)
            } else {
                Button(
                    onClick = {
                        viewModel.register(
                            User(fullName = fullName, email = email, phoneNumber = phoneNumber, role = selectedRole),
                            password
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Saffron),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.register_link).uppercase(), 
                        style = MaterialTheme.typography.titleLarge, 
                        fontWeight = FontWeight.Bold, 
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(stringResource(R.string.already_have_account), color = Color.Gray)
                Text(
                    stringResource(R.string.login_link), 
                    color = IndiaGreen, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }
        }
    }
}

@Composable
fun RegisterField(
    label: String, value: String, onValueChange: (String) -> Unit, placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector, keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false, visible: Boolean = false, onToggleVisible: () -> Unit = {}
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = Color.Black, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value, onValueChange = onValueChange, modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.Gray) },
            leadingIcon = { Icon(icon, contentDescription = null, tint = IndiaGreen) },
            trailingIcon = if (isPassword) {
                { IconButton(onClick = onToggleVisible) {
                    Icon(if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null, tint = Color.Gray)
                }}
            } else null,
            visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = IndiaGreen, 
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
    }
}
