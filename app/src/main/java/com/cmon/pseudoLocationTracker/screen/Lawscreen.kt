package com.cmon.pseudoLocationTracker.screen

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun LawScreen(
    navController: NavController
) {
    val scrollableState = rememberScrollState()
    Scaffold(
        topBar = {
            TopBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface)
                .verticalScroll(scrollableState)
        ) {
            Text(
                text = stringResource(id = R.string.law0),
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 40.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(
                            topEnd = 0.dp,
                            topStart = 0.dp,
                            bottomEnd = 20.dp,
                            bottomStart = 20.dp
                        )
                    )
                    .padding(top = 40.dp, bottom = 60.dp, start = 20.dp, end = 20.dp)
            )
            LawCard(1,R.string.law11)
            LawCard(2, R.string.law12)
            LawCard(3, R.string.law13)
            LawCard(4, R.string.law14)
            LawCard(5, R.string.law15)
        }
    }
}

@Composable
fun LawCard(
    num: Int,
    @StringRes stringRes: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 16.dp, top = 20.dp, start = 16.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondary,shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ){
        Text(
            text = num.toString(),
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
            ),
            modifier = Modifier.padding(end = 10.dp)
            )
        Text(
            text = stringResource(id = stringRes),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSecondary
            ),
        )
    }
}