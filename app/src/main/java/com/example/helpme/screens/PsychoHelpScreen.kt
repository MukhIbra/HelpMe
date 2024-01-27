package com.example.helpme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helpme.R

@Composable
fun PsychoHelpScreen(navController: NavController) {
    var current by remember { mutableIntStateOf(1) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Psycho Help", fontSize = 37.sp)

        when (current) {
            1 -> Image(
                modifier = Modifier.size(270.dp),
                painter = painterResource(R.drawable.psycho_help_1),
                contentDescription = "Psycho Help"
            )

            2 -> Image(
                modifier = Modifier.size(270.dp),
                painter = painterResource(R.drawable.psycho_help_2),
                contentDescription = "Psycho Help"
            )

            3 -> Image(
                modifier = Modifier.size(270.dp),
                painter = painterResource(R.drawable.psycho_help_4),
                contentDescription = "Psycho Help"
            )

            4 -> Image(
                modifier = Modifier.size(270.dp),
                painter = painterResource(R.drawable.psycho_help_4),
                contentDescription = "Psycho Help"
            )
        }

        when (current) {
            1 -> Text(
                "Take a deep breath and exhale", textAlign = TextAlign.Center, fontSize = 20.sp
            )

            2 -> Text(
                "Close your eyes and imagine the best place",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            3 -> Text(
                "Focus on little thing, pay attention on its color and shape",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            4 -> Text("Good Job. You made it.", textAlign = TextAlign.Center, fontSize = 20.sp)
        }

        when (current) {
            1 -> Button(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
                onClick = { current += 1 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(
                        R.color.yellow
                    ), contentColor = Color.Black
                )
            ) {
                Text("Next", fontSize = 20.sp)
            }

            2 -> Button(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
                onClick = { current += 1 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(
                        R.color.yellow
                    ), contentColor = Color.Black
                )
            ) {
                Text("Next", fontSize = 20.sp)
            }

            3 -> Button(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
                onClick = { current += 1 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(
                        R.color.yellow
                    ), contentColor = Color.Black
                )
            ) {
                Text("Done", fontSize = 20.sp)
            }

            4 -> Button(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
                onClick = { navController.navigate("Home") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(
                        R.color.yellow
                    ), contentColor = Color.Black
                )
            ) {
                Text("Quit", fontSize = 20.sp)
            }
        }
    }
}
