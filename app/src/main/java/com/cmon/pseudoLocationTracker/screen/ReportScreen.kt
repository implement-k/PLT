package com.cmon.pseudoLocationTracker.screen

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cmon.pseudoLocationTracker.R
import com.cmon.pseudoLocationTracker.composable.ReportReCheckDialog
import com.cmon.pseudoLocationTracker.composable.TopBar
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReportScreen(
    navController: NavController,
    db: FirebaseFirestore
) {
    var nameValue by remember { mutableStateOf("") }
    var locValue by remember { mutableStateOf("") }
    var classValue by remember { mutableStateOf("") }
    var contactValue by remember { mutableStateOf("") }
    var detailValue by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(navController = navController)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            Text(
                text = stringResource(id = R.string.menu_report),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 40.sp
                ),
                modifier = Modifier.padding(start = 20.dp, end = 16.dp, top = 10.dp, bottom = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.report_explanation1),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 20.dp, end = 16.dp, bottom = 5.dp)
            )
            Text(
                text = stringResource(id = R.string.report_explanation2),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 20.dp, end = 16.dp, bottom = 10.dp)
            )
            ReportScreenTextField(
                nameValue = nameValue,
                onValueChange = { nameValue = it },
                label = R.string.name_label,
                focusManager = focusManager
            )
            ReportScreenTextField(
                nameValue = locValue,
                onValueChange = { locValue = it },
                label = R.string.location_label,
                focusManager = focusManager
            )
            ReportScreenTextField(
                nameValue = classValue,
                onValueChange = { classValue = it },
                label = R.string.classification_label,
                focusManager = focusManager
            )
            ReportScreenTextField(
                nameValue = contactValue,
                onValueChange = { contactValue = it },
                label = R.string.contact_label,
                focusManager = focusManager
            )
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 16.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.info_icon),
                    contentDescription = stringResource(id = R.string.menu_info),
                    modifier = Modifier
                        .size(15.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.contact_alarm),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp
                    )
                )
            }
            OutlinedTextField(
                value = detailValue,
                onValueChange = { detailValue = it },
                label = { Text( text = stringResource(id = R.string.detail_label)) },
                placeholder = { Text(
                    text = stringResource(id = R.string.detail_placeholder),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                ) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor =  MaterialTheme.colors.surface ,
                    backgroundColor =  MaterialTheme.colors.surface ),
                textStyle = TextStyle(lineHeight = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
                    .height(400.dp)
            )
            val openDialog = remember { mutableStateOf(false) }

            Button(
                enabled = nameValue.isNotEmpty() && locValue.isNotEmpty() && classValue.isNotEmpty() && detailValue.isNotEmpty(),
                onClick = { openDialog.value = true },
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary,
                    disabledContentColor = MaterialTheme.colors.onSurface,
                    disabledBackgroundColor = MaterialTheme.colors.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.done),
                    fontWeight = FontWeight.Bold,
                )
            }
            ReportReCheckDialog(
                openDialog = openDialog.value,
                onDismissRequest = { openDialog.value = false },
                nameValue = nameValue,
                locValue = locValue,
                classValue = classValue,
                contactValue = contactValue,
                detailValue = detailValue,
                editClick = { openDialog.value = false },
                okClick = {
                    navController.navigateUp()
                    val report = hashMapOf(
                        "name" to nameValue,
                        "location" to locValue,
                        "classification" to classValue,
                        "contact" to contactValue,
                        "detail" to detailValue
                    )

                    db.collection("report")
                        .add(report)
                        .addOnSuccessListener { documentReference ->
                            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            Toast.makeText(context, "요청 성공", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error adding document", e)
                            Toast.makeText(context, "요청 실패", Toast.LENGTH_SHORT).show()
                        }})
        }
    }
}

@Composable
fun ReportScreenTextField(
    nameValue: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    focusManager: FocusManager
) {
    OutlinedTextField(
        value = nameValue,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text( text = stringResource(id = label)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor =  MaterialTheme.colors.surface ,
            backgroundColor =  MaterialTheme.colors.surface ),
        textStyle = TextStyle(lineHeight = 14.sp) ,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
    )
}
