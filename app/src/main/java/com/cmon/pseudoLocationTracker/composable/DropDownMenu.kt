package com.cmon.pseudoLocationTracker.composable

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmon.pseudoLocationTracker.Loc
import com.cmon.pseudoLocationTracker.R
import com.google.firebase.firestore.FirebaseFirestore
import com.cmon.pseudoLocationTracker.DropDownMenuButton
import com.cmon.pseudoLocationTracker.data.UpdateRequestModule

@Composable
fun PseudoDropDownMenuButton(
    isDropDownMenuExtended: Boolean,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
    navController: NavHostController,
    focusManager: FocusManager,
    db: FirebaseFirestore,
    context: Context,
    modifier: Modifier = Modifier
) {
    val isOpenDialog = remember { mutableStateOf(false) }
    val isOpenReCheckDialog = remember { mutableStateOf(false) }
    var requestInput by remember { mutableStateOf("") }

    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 20.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp),
        modifier = modifier
            .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
            .size(height = 55.dp, width = 55.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = stringResource(id = R.string.menu),
            modifier = Modifier
                .size(20.dp)
                .padding(start = 0.dp, end = 0.dp)
        )
    }


    DropdownMenu(
        expanded = isDropDownMenuExtended,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .shadow(elevation = 5.dp, CircleShape)
            ){
                Image(
                    painter = painterResource(id = R.drawable.plt_app_icon),
                    contentDescription = stringResource(id = R.string.app_name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(start = 10.dp)
            )
        }


        DropDownMenuButton(
            onClick = { navController.navigate("report_screen") },
            loc = Loc.TOP, text = R.string.menu_report
        )

        DropDownMenuButton(
            onClick = { isOpenDialog.value = true },
            loc = Loc.MIDDLE, text = R.string.dialog_request_infoModification
        )

        if (isOpenDialog.value){
            RequestInfoDialog(
                onDismissRequest = { isOpenDialog.value = false },
                value = requestInput,
                onValueChange = { requestInput = it },
                keyboardAction = KeyboardActions(
                    onNext = { focusManager.clearFocus()}
                ),
                cancelClick = {
                    isOpenDialog.value = false
                    requestInput = ""},
                okClick = {
                    isOpenDialog.value = false
                    isOpenReCheckDialog.value = true },
                isTextEmpty = requestInput.isNotEmpty()
            )
        }

        if (isOpenReCheckDialog.value) {
            ReCheckDialog(
                onDismissRequest = { isOpenReCheckDialog.value = false },
                text = requestInput,
                editClick = {
                    isOpenReCheckDialog.value = false
                    isOpenDialog.value = true
                },
                okClick = {
                    isOpenReCheckDialog.value = false
                    val request = hashMapOf("request" to requestInput)

                    UpdateRequestModule.UpdateRequest(request)

                    db.collection("information_modification_request")
                        .add(request)
                        .addOnSuccessListener {
                            Toast.makeText(context, "요청 성공", Toast.LENGTH_SHORT).show()
                            requestInput = ""
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "요청 실패", Toast.LENGTH_SHORT).show()
                        }
                }
            )
        }

        DropDownMenuButton(
            onClick = { navController.navigate("criteria_screen") },
            loc = Loc.BOTTOM, text = R.string.menu_standard
        )

        Button(
            onClick = { navController.navigate("info_screen") },
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
            Icon(
                painter = painterResource(id = R.drawable.info_icon),
                contentDescription = stringResource(id = R.string.menu_info),
                modifier = Modifier
                    .size(15.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.menu_info),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp
                )
            )
        }
    }
}

