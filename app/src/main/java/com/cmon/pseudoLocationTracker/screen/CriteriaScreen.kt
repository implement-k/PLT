package com.cmon.pseudoLocationTracker.screen

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun CriteriaScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBar(navController = navController)
        },
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.criteria_chart),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(250.dp)
            )
            Column {
                CriteriaHeadText(stringRes = R.string.pseudo)
                CriteriaBodyText(stringRes = R.string.criteria_pseudo)
                CriteriaCaptionText(stringRes = R.string.criteria_pseudo_footnote)

                CriteriaHeadText(stringRes = R.string.heresy)
                CriteriaBodyText(stringRes = R.string.criteria_heresy)
                CriteriaCaptionText(stringRes = R.string.criteria_heresy_footnote)
            }
        }
    }
}

@Composable
fun CriteriaCaptionText(
    @StringRes stringRes: Int
) {
    Text(
        text = stringResource(id = stringRes),
        style = MaterialTheme.typography.caption,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
    )
}

@Composable
fun CriteriaBodyText(
    @StringRes stringRes: Int
) {
    Text(
        text = stringResource(id = R.string.criteria_pseudo),
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    )
}

@Composable
fun CriteriaHeadText(
    @StringRes stringRes: Int
) {
    Text(
        text = stringResource(id = stringRes),
        style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp
        ),
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
    )
}