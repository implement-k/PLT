package com.cmon.pseudoLocationTracker.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.cmon.pseudoLocationTracker.PSEUDO
import com.cmon.pseudoLocationTracker.R
import com.cmon.pseudoLocationTracker.composable.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PseudoInfoScreen(
    navController: NavController,
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
            PseudoInfoRedText(stringRes = R.string.law2)
            Text(
                text = PSEUDO.pseudoName,
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 60.sp
                ),
                modifier = Modifier.padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 3.dp, start = 20.dp, end = 20.dp)
            ){
                PseudoInfoCaptionText(text = stringResource(id = R.string.pseudo_info_initiator))
                PseudoInfoCaptionText(text = PSEUDO.initiator, end = 6.dp)
                PseudoInfoCaptionText(text = stringResource(id = R.string.pseudo_info_representative), start = 6.dp, end = 3.dp)
                PseudoInfoCaptionText(text = PSEUDO.representative)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            ) {
                PseudoInfoCaptionText(text = stringResource(id = R.string.pseudo_info_site), end = 3.dp)
                Text(
                    text = PSEUDO.site,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier.clickable(
                        onClick = {
                            val url = PSEUDO.site

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)

                            ContextCompat.startActivity(context, intent, null)
                        }
                    )
                )
            }
            PseudoInfoHeadText(stringRes = R.string.pseudo_info_detail, top = 60.dp)
            PseudoInfoBodyText(text = PSEUDO.explanation)

            if (PSEUDO.organization != "null") {
                PseudoInfoHeadText(stringRes = R.string.pseudo_info_organization, top = 16.dp)
                PseudoInfoBodyText(text = PSEUDO.organization)
            }

            if (PSEUDO.relativeInstitutions != "null") {
                PseudoInfoHeadText(stringRes = R.string.pseudo_info_relativeInstitutions, top = 16.dp)
                PseudoInfoBodyText(text = PSEUDO.relativeInstitutions)
            }

            PseudoInfoHeadText(stringRes = R.string.pseudo_info_criteria, top = 16.dp)
            PseudoInfoBodyText(text = PSEUDO.criteria)

            Column(
                modifier = Modifier.padding(start = 20.dp,end = 20.dp, top = 10.dp, bottom = 5.dp)
            ) {
                PseudoInfoCaptionText(text = PSEUDO.criteriaReason)
            }

            Column(
                modifier = Modifier.padding(start = 20.dp,end = 20.dp, top = 10.dp, bottom = 5.dp)
            ){
                PseudoInfoCaption2Text(text = stringResource(id = R.string.source))
                PseudoInfoCaption2Text(text = PSEUDO.source)
                PseudoInfoCaption2Text(text = stringResource(id = R.string.locsource))
                PseudoInfoCaption2Text(text = PSEUDO.locsource)
            }
        }
    }
}

@Composable
fun PseudoInfoRedText(
    @StringRes stringRes: Int
) {
    Text(
        text = stringResource(id = stringRes),
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color(0xFFAE3039)
        ),
        lineHeight = 20.sp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(
                color = Color(0xFFF5DBDD),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    )
}


@Composable
fun PseudoInfoCaption2Text(
    text: String
) {
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
        )
    )
}

@Composable
fun PseudoInfoBodyText(
    text: String
) {
    Text(
        text = text,
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
            .padding(16.dp)
    )
}

@Composable
fun PseudoInfoHeadText(
    @StringRes stringRes: Int,
    top: Dp = 0.dp
) {
    Text(
        text = stringResource(id = stringRes),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
        ),
        modifier = Modifier.padding(top = top, start = 20.dp, end = 20.dp)
    )
}

@Composable
fun PseudoInfoCaptionText(
    text: String,
    start: Dp = 0.dp,
    end: Dp = 0.dp
) {
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp
        ),
        modifier = Modifier.padding(start = start, end = end)
    )
}