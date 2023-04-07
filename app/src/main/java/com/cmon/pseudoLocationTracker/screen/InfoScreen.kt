package com.cmon.pseudoLocationTracker.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cmon.pseudoLocationTracker.Pseudo
import com.cmon.pseudoLocationTracker.R
import com.cmon.pseudoLocationTracker.nullPseudo

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InfoScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopBar(navController = navController)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Box(
                    modifier = Modifier
                        .shadow(elevation = 15.dp, CircleShape)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.plt_app_icon),
                        contentDescription = stringResource(id = R.string.app_name),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                }
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 25.sp
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Row{
                    Text(
                        text = stringResource(id = R.string.version),
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "1.0.0",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Button(
                    onClick = {/*TODO 오픈소스 정보*/},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.onSurface
                    ),
                    shape = RoundedCornerShape(50),
                    elevation = ButtonDefaults.elevation(0.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.open_source_info),
                        style = MaterialTheme.typography.body1
                    )
                }
                Text(
                    text = stringResource(id = R.string.inquiry),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 2.dp, top = 16.dp)
                )
                Text(
                    text = stringResource(id = R.string.madyBy),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 2.dp)
                )
            }
        }
    }
}

@Composable
fun TopBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Button(
            onClick = { navController.navigateUp()
                      Pseudo = nullPseudo
                      },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp),
            modifier = Modifier
                .padding(16.dp)
                .size(width = 55.dp, height = 55.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_icon),
                contentDescription = stringResource(id = R.string.back),
                modifier = Modifier
                    .size(50.dp)
            )
        }
    }
}
