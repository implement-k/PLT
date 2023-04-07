package com.cmon.pseudoLocationTracker.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cmon.pseudoLocationTracker.R

@Composable
fun RequestInfoDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardAction: KeyboardActions,
    cancelClick: () -> Unit,
    okClick: () -> Unit,
    isTextEmpty: Boolean
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.surface,
        focusedIndicatorColor =  Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent)

    if (openDialog) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Card(
                // modifier = modifier.size(280.dp, 240.dp)
                elevation = 8.dp
            ) {
                Column(
                    Modifier
                        .background(MaterialTheme.colors.background)
                ) {
                    Text(
                        text = stringResource(id = R.string.request_modify_info),
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 20.dp)
                    )
                    TextField(
                        value = value, onValueChange = onValueChange,
                        placeholder = { Text(
                            text = stringResource(id = R.string.request_info_dialog),
                            style = MaterialTheme.typography.body2,
                        ) },
                        keyboardActions = keyboardAction,
                        shape = RoundedCornerShape(0.dp),
                        colors = textFieldColors,
                        textStyle = TextStyle(lineHeight = 14.sp) ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    ) {
                        Button(
                            onClick = cancelClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.surface,
                                contentColor = MaterialTheme.colors.onSurface
                            ),
                            shape = RoundedCornerShape(0.dp),
                            elevation = ButtonDefaults.elevation(0.dp),
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                        Button(
                            enabled = isTextEmpty,
                            onClick = okClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary,
                                disabledBackgroundColor = MaterialTheme.colors.surface,
                                disabledContentColor = MaterialTheme.colors.onSurface
                            ),
                            shape = RoundedCornerShape(0.dp),
                            elevation = ButtonDefaults.elevation(0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.request),
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReCheckDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    text: String,
    editClick: () -> Unit,
    okClick: () -> Unit
) {
    if (openDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Card(
                // modifier = modifier.size(280.dp, 240.dp)
                elevation = 8.dp,
            ) {
                Column(
                    modifier = Modifier.background(color = MaterialTheme.colors.background)
                ) {
                    Text(
                        text = stringResource(id = R.string.recheck_text),
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 20.dp)
                    )
                    Text(
                        text = text,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colors.primary
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 20.dp, start = 20.dp)
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    ) {
                        Button(
                            onClick = editClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary
                            ),
                            shape = RoundedCornerShape(0.dp),
                            elevation = ButtonDefaults.elevation(0.dp),
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.edit),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                        Button(
                            onClick = okClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary
                            ),
                            shape = RoundedCornerShape(0.dp),
                            elevation = ButtonDefaults.elevation(0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.request),
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ReportReCheckDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    nameValue: String,
    locValue: String,
    classValue: String,
    contactValue: String,
    detailValue: String,
    editClick: () -> Unit,
    okClick: () -> Unit
) {
    if (openDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Card(
                // modifier = modifier.size(280.dp, 240.dp)
                elevation = 8.dp,
            ) {
                Column(
                    modifier = Modifier.background(color = MaterialTheme.colors.background)
                ) {
                    Text(
                        text = stringResource(id = R.string.recheck_text),
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 20.dp)
                    )
                    RecheckDialogText(R.string.name_label, nameValue)
                    RecheckDialogText(R.string.location_label, locValue)
                    RecheckDialogText(R.string.classification_label, classValue)
                    RecheckDialogText(R.string.contact_label, contactValue)
                    RecheckDialogText(R.string.detail_label, detailValue)
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Button(
                            onClick = editClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary
                            ),
                            shape = RoundedCornerShape(0.dp),
                            elevation = ButtonDefaults.elevation(0.dp),
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.edit),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                        Button(
                            onClick = okClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onSecondary
                            ),
                            shape = RoundedCornerShape(0.dp),
                            elevation = ButtonDefaults.elevation(0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.request),
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecheckDialogText(
    @StringRes label: Int,
    detail: String
    ){
    Row{
        Text(
            text = stringResource(id = label),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                color = MaterialTheme.colors.onSurface
            ),
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, start = 20.dp)
        )
        Text(
            text = detail,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 20.dp)
        )
    }
}
