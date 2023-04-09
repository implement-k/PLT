package com.cmon.pseudoLocationTracker.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cmon.pseudoLocationTracker.NULL_PSEUDO
import com.cmon.pseudoLocationTracker.PSEUDO
import com.cmon.pseudoLocationTracker.R

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
                PSEUDO = NULL_PSEUDO
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