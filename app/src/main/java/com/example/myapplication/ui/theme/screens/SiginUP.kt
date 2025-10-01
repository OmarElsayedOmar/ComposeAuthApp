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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.UserEntity
import com.example.myapplication.navigation.Routes
import com.example.myapplication.ui.theme.components.CustomTextField
import com.example.myapplication.ui.theme.components.GradientButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun SignUp(navController: NavHostController) {
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_database"
        ).build()
    }
    val userDao = db.userDao()
    var username by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


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
                text = "Hello\nSign Up!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Start)
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
                        value = username,
                        onValueChange = { username = it },
                        label = "Username",
                        leadingIcon = Icons.Default.Person
                    )

                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        leadingIcon = Icons.Default.Email

                    )
                    CustomTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true
                    )

                    CustomTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "Confirm Password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    GradientButton(
                        text = "Sign Up",
                        onClick = {
                            if (password == confirmPassword && email.isNotEmpty() && username.isNotEmpty()) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val user = UserEntity(
                                        email = email.toString(),
                                        password = password.toString()
                                    )
                                    userDao.insertUser(user)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            context,
                                            " Registration completed successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate(Routes.login)
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    " The password does not match or the email & username is empty ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )


                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Text(
                            text = ("You have an account!"),
                            color = Color.Gray
                        )

                        Text(
                            text = "Log in",
                            color = Color(0xFF9A3DA9),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                navController.navigate(Routes.login)
                            }
                        )

                    }
                }
            }
        }
    }
    }





