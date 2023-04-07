package com.cmon.pseudoLocationTracker

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

enum class Loc{
    TOP, MIDDLE, BOTTOM
}

@Composable
fun DropDownMenuButton(
    onClick: () -> Unit,
    loc: Loc,
    @StringRes text: Int
) {
    val roundCornerTop = if (loc == Loc.TOP) 20.dp else 0.dp
    val roundCornerBottom = if (loc == Loc.BOTTOM) 20.dp else 0.dp
    val paddingTop = if (loc == Loc.TOP) 10.dp else 0.dp
    val paddingBottom = if (loc == Loc.BOTTOM) 10.dp else 0.dp

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(
            topStart = roundCornerTop,
            topEnd = roundCornerTop,
            bottomStart = roundCornerBottom,
            bottomEnd = roundCornerBottom
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp),
        modifier = Modifier
            .padding(top = paddingTop, bottom = paddingBottom, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
