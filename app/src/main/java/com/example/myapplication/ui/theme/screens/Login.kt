package com.example.myapplication.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.components.CustomTextField
import com.example.myapplication.ui.theme.components.GradientButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable

fun LoginPage(navController: NavHostController) {
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_database"
        ).build()
    }
    val userDao = db.userDao()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE53935), Color(0xFF8E24AA))
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello\nSign in!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
               modifier = Modifier .align(Alignment.Start)
                   .padding(start = 20.dp, top = 90.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Card(
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                modifier = Modifier.fillMaxSize(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 80.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        leadingIcon = Icons.Default.Email
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    GradientButton(
                        text = "Login",
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                val user = userDao.login(
                                    email = email,
                                    password = password
                                )
                                if (user != null) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(
                                            context,
                                            "Login successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(
                                            context,
                                            "Please enter your email or password",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Row {
                        Text(
                            text = ("Don't have an account?"),
                            color = Color.Gray
                        )

                        Text(
                            text = "Sign up",
                            color = Color(0xFFB146C2),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                navController.navigate(Routes.signUp)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Or login with",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(40.dp),
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        // Google
                        IconButton(
                            onClick = { /* TODO: Google Login */ },
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.White, shape = RoundedCornerShape(50))
                                .shadow(4.dp, shape = RoundedCornerShape(50))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = "Google Login",
                                tint = Color.Unspecified
                            )
                        }
                        IconButton(
                            onClick = { /* TODO: Facebook Login */ },
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.White, shape = RoundedCornerShape(50))
                                .shadow(4.dp, shape = RoundedCornerShape(50))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.facebook),
                                contentDescription = "Facebook Login",
                                tint = Color.Unspecified
                            )
                        }
                        IconButton(
                            onClick = { /* TODO: Twitter Login */ },
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.White, shape = RoundedCornerShape(50))
                                .shadow(4.dp, shape = RoundedCornerShape(50))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.linkedin),
                                contentDescription = "Twitter Login",
                                tint = Color.Unspecified
                            )
                        }

                    }
                }
            }
        }
    }
}
