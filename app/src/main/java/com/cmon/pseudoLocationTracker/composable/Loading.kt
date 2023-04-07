package com.cmon.pseudoLocationTracker.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cmon.pseudoLocationTracker.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer

@SuppressLint("InvalidColorHexValue")
@Composable
fun LoadingCard() {
    Column(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(0.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter,
        ){
            Surface(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFD3EDD9),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 42.dp, end = 42.dp, bottom = 90.dp)
                    .aspectRatio(1.58f)
            ){}
            Surface(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFF5DBDD),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 60.dp)
                    .aspectRatio(1.58f)
            ){}
            Surface (
                elevation = 15.dp,
                shape = RoundedCornerShape(20.dp)
            ){
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFE6F6F6),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
                        .aspectRatio(1.58f)
                ){
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .shimmer()
                            .background(
                                color = Color(0xFFCFCFCF),
                                shape = RoundedCornerShape(0.dp)
                            )
                            .fillMaxWidth(0.4f)
                            .height(28.dp)

                    )
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .shimmer()
                            .background(
                                color = Color(0xFFFCFCFCF),
                                shape = RoundedCornerShape(0.dp)
                            )
                            .fillMaxWidth(1f)
                            .height(18.dp)
                    )
                    Box(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                            .shimmer()
                            .background(
                                color = Color(0xFFFCFCFCF),
                                shape = RoundedCornerShape(0.dp)
                            )
                            .fillMaxWidth(0.6f)
                            .height(18.dp)
                    )
                }
            }
        }
        Text(
            text = stringResource(id = R.string.next_data),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = Color(0xFFFCFCFCF)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

