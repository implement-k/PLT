package com.cmon.pseudoLocationTracker.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmon.pseudoLocationTracker.R

@Composable
fun SearchBar(
    keyboardActions: KeyboardActions,
    value: String,
    onValueChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    isSearch: Boolean
) {
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colors.secondary,
        unfocusedBorderColor =  MaterialTheme.colors.surface ,
        backgroundColor =  MaterialTheme.colors.surface )
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 10.dp)
            .height(60.dp)
            .shadow(elevation = 20.dp, RoundedCornerShape(20.dp)),
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(
                text = stringResource(id = R.string.search_label),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                ),
            ) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = keyboardActions,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = stringResource(id = R.string.search),
                    modifier = Modifier
                        .size(20.dp)
                )
            },
            shape = RoundedCornerShape(50),
            colors = textFieldColors,
            textStyle = TextStyle(lineHeight = 10.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
        if (isSearch) {
            IconButton(
                onClick = onCancelClick,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = null,
                )
            }
        }
    }
}