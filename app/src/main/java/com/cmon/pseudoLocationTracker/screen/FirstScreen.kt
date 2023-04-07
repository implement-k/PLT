package com.cmon.pseudoLocationTracker.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmon.pseudoLocationTracker.R
import com.cmon.pseudoLocationTracker.data.First
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FirstScreen(
    onClick: () -> Unit
) {
    val items = listOf(
        First(R.drawable.plt_app_icon, R.string.app_name, null),
        First(R.drawable.first1, R.string.first1, null),
        First(R.drawable.first2, R.string.first2, null),
        First(R.drawable.first3, R.string.first3, null),
        First(R.drawable.plt_app_icon, null, R.string.first4),
    )
    val pageState = rememberPagerState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
        .fillMaxSize()
    ) {
        HorizontalPager(
            count = items.size,
            state = pageState,
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) { page ->
            OnBoardingItem(items = items[page],size = items.size, index = pageState.currentPage, onClick = onClick)
        }
        Box(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Indicators(items.size, pageState.currentPage)
        }
    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
            )
    ) {

    }
}

@Composable
fun OnBoardingItem(
    items: First,
    index: Int,
    size: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (items.drawableRes != null){
            Image(
                painter = painterResource(id = items.drawableRes),
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 100.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            if (items.stringRes1 != null) {
                Text(
                    text = stringResource(id = items.stringRes1),
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }
            if (items.stringRes2 != null) {
                Text(
                    text = stringResource(id = items.stringRes2),
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 16.dp)
                )
            }
            if (index == size - 1) {
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(20.dp),
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
                        .fillMaxWidth()
                        .height(60.dp)
                ){
                    Text(
                        text = stringResource(id = R.string.next),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_next_icon),
                        contentDescription = "Next",
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        }
    }
}