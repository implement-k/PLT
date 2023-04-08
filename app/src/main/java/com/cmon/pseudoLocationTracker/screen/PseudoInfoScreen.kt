package com.cmon.pseudoLocationTracker.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.cmon.pseudoLocationTracker.R
import com.cmon.pseudoLocationTracker.data.Pseudo

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PseudoInfoScreen(
    navController: NavController,
    pseudo: Pseudo
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = pseudo.pseudoName,
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 60.sp
                ),
                modifier = Modifier.padding(16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = stringResource(id = R.string.initiator),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)
                )
                Text(
                    text = pseudo.initiator,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.padding(end = 6.dp, bottom = 10.dp)
                )
                Text(
                    text = stringResource(id = R.string.representative),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.padding(start = 6.dp, end = 3.dp, bottom = 10.dp)
                )
                Text(
                    text = pseudo.representative,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.padding(end = 16.dp, bottom = 10.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.site),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.padding(start = 20.dp, end = 3.dp)
                )
                Text(
                    text = pseudo.site,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier.clickable(
                        onClick = {
                            val url = pseudo.site

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)

                            ContextCompat.startActivity(context, intent, null)
                        }
                    )
                )
            }
            Text(
                text = stringResource(id = R.string.detail),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                ),
                modifier = Modifier.padding(start = 20.dp, top = 60.dp, end = 16.dp)
            )
            Text(
                text = pseudo.explanation,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                ),
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp)
            )
            Text(
                text = stringResource(id = R.string.organization),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                ),
                modifier = Modifier.padding(start = 20.dp, top = 16.dp, end = 16.dp)
            )
            Text(
                text = pseudo.organization,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                ),
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp)
            )
            Text(
                text = stringResource(id = R.string.crime),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                ),
                modifier = Modifier.padding(start = 20.dp, top = 16.dp, end = 16.dp)
            )
            Text(
                text = pseudo.crime,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                ),
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp)
            )
            if (pseudo.RelativeInstitutions != null) {
                Text(
                    text = stringResource(id = R.string.RelativeInstitutions),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    ),
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp, end = 16.dp)
                )
                Text(
                    text = pseudo.RelativeInstitutions,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                    ),
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(10.dp)
                )
            }
            Column(
                modifier = Modifier.padding(start = 20.dp,end = 20.dp, top = 10.dp, bottom = 5.dp)
            ){
                Text(
                    text = stringResource(id = R.string.source),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                    )
                )
                Text(
                    text = pseudo.source,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                    ),
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = stringResource(id = R.string.locsource),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                    )
                )
                Text(
                    text = pseudo.locsource,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                    )
                )
            }
        }
    }
}