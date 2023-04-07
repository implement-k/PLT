package com.cmon.pseudoLocationTracker.screen

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.cmon.pseudoLocationTracker.composable.MultiSelector
import com.cmon.pseudoLocationTracker.data.Pseudo
import com.cmon.pseudoLocationTracker.ui.theme.md_theme_light_surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.cmon.pseudoLocationTracker.Pseudo
import com.cmon.pseudoLocationTracker.composable.LoadingCard
import com.cmon.pseudoLocationTracker.ui.theme.md_theme_light_secondary

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun PseudoChecklistScreen(
    navController: NavController,
    pseudoList: SnapshotStateList<Pseudo>,
    expandedList: SnapshotStateList<Boolean>,
    checkedList: SnapshotStateList<Boolean>,
    onAllTrueChange: () -> Unit,
    onAllFalseChange: () -> Unit
) {
    var pseudo by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            Column() {
                TopBar(navController = navController)
                if (pseudo) {
                    Row(
                        modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 10.dp, bottom = 2.dp)
                    ) {
                        CaptionButton(onAllTrueChange, R.string.all_check)
                        CaptionButton(onAllFalseChange, R.string.all_false_check)
                        Button (
                            onClick = { navController.navigate("standard_screen") },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary
                            ),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp,
                                disabledElevation = 0.dp
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp, end = 12.5.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.info_icon),
                                contentDescription = stringResource(id = R.string.menu_info),
                                modifier = Modifier
                                    .size(18.dp)
                                    .padding(end = 4.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.menu_standard),
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colors.background,
            ) {
                val options1 = listOf("사이비", "이단")
                var selectedOption1 by remember {
                    mutableStateOf(options1.first())
                }

                MultiSelector(
                    options = options1,
                    selectedOption = selectedOption1,
                    onOptionSelect = { option ->
                        selectedOption1 = option
                        pseudo = option == options1.first()
                    },
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxWidth()
                        .height(55.dp)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp, start = 16.dp, bottom = 100.dp, end = 16.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            if(pseudo){
                LazyColumn(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(20.dp)
                        )

                ) {
                    itemsIndexed(pseudoList) { index, item ->
//                        if (item.pseudo.toBoolean() == pseudo){
                            PseudoCheckCard(
                                checked = checkedList[index],
                                onCheckedChange = { checkedList[index] = !checkedList[index]},
                                expanded = expandedList[index],
                                onClick = { expandedList[index] = !expandedList[index] },
                                pseudo = item,
                                index = index,
                                listSize = pseudoList.size,
                                navController = navController
                            )
                            Divider(color = MaterialTheme.colors.background)
//                        }
                    }
                }
            } else {
                LoadingCard()
            }
        }
    }
}


@Composable
fun PseudoCheckCard(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    expanded: Boolean,
    onClick: () -> Unit,
    pseudo: Pseudo,
    index: Int,
    listSize: Int,
    navController: NavController
) {
    val color = remember { Animatable(md_theme_light_surface) }
    LaunchedEffect(expanded) {
        color.animateTo(if (expanded) md_theme_light_secondary else md_theme_light_surface, animationSpec = tween(500))
    }

    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .background(
                color = color.value,
                shape = if (index == 0) RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
                else if (index == listSize - 1) RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
                else RoundedCornerShape(0.dp)
            )
            .padding(10.dp)
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically
                ){
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary,
                )
            )
            Text(
                text = pseudo.pseudoName,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            PseudoItemButton(
                expanded = expanded,
                onClick = onClick
            )
        }
        if (expanded) {
            DetailInfo(pseudo, navController)
        }
    }
}

@Composable
fun DetailInfo(
    pseudo: Pseudo,
    navController: NavController
) {
    Column{
        Row(modifier = Modifier.padding(10.dp)){
            Text(
                text = stringResource(id = R.string.representative),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
            Text(
                text = pseudo.representative,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)){
            Text(
                text = stringResource(id = R.string.num),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
            Text(
                text = pseudo.address.size.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)){
            Text(
                text = stringResource(id = R.string.explanation),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
            Text(
                text = pseudo.explanation,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)){
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("pseudo_info_screen")
                          Pseudo= pseudo},
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.onBackground
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    hoveredElevation = 0.dp,
                    focusedElevation = 0.dp),
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.show_detail),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_next_icon),
                    contentDescription = stringResource(id = R.string.next),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun PseudoItemButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = if (expanded) painterResource(id = R.drawable.expand_less_icon)
            else painterResource(id = R.drawable.expand_more_icon),
            contentDescription = stringResource(R.string.expand_button_content_description)
        )
    }
}

@Composable
fun CaptionButton(
    onClick: () -> Unit,
    @StringRes id: Int
){
    Button (
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        modifier = Modifier
            .padding(top = 10.dp, end = 12.5.dp)
    ) {
        Text(
            text = stringResource(id = id),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp
            ),
        )
    }
}