package com.example.helpme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helpme.R

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.yellow), RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 20.dp)
                        .clickable { navController.navigate("TalkingPoints") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_talking),
                        contentDescription = "Talking Points"
                    )
                    Text("Talking Points", fontSize = 22.sp)
                }
                Column(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.yellow), RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 22.dp, vertical = 20.dp)
                        .clickable { navController.navigate("PsychoHelp") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_psycho_help),
                        contentDescription = "Psycho Help"
                    )
                    Text("Psycho Help", fontSize = 22.sp)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.yellow), RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 47.dp, vertical = 20.dp)
                        .clickable { navController.navigate("SOS") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_sos),
                        contentDescription = "SOS"
                    )
                    Text("SOS", fontSize = 22.sp)
                }
                Column(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.yellow), RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 22.dp, vertical = 20.dp)
                        .clickable { navController.navigate("SelfDefence") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_defence),
                        contentDescription = "Self Defence"
                    )
                    Text("Self Defence", fontSize = 22.sp)
                }
            }
        }
    }
}
