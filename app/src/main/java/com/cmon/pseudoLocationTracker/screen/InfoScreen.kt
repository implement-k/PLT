package com.cmon.pseudoLocationTracker.screen

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.cmon.pseudoLocationTracker.R
import com.cmon.pseudoLocationTracker.composable.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InfoScreen(navController: NavController) {
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
                        text = "1.0.1",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                InfoBottomText(stringRes = R.string.info_mail, isTop = true)
                InfoBottomText(stringRes = R.string.madyBy, isTop = false)
            }
        }
    }
}

@Composable
fun InfoBottomText(
    @StringRes stringRes: Int,
    isTop: Boolean
) {
    Text(
        text = stringResource(id = stringRes),
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = if (isTop) 2.dp else 16.dp, top = if (isTop) 16.dp else 2.dp)
    )
}