package com.cmon.pseudoLocationTracker.screen

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmon.pseudoLocationTracker.*
import com.cmon.pseudoLocationTracker.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.cmon.pseudoLocationTracker.composable.ReCheckDialog
import com.cmon.pseudoLocationTracker.composable.RequestInfoDialog
import com.cmon.pseudoLocationTracker.composable.bitmapDescriptorFromVector
import com.cmon.pseudoLocationTracker.data.Pseudo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.*

var point = LatLng(36.34, 127.77)
var zoom = 7f


@Composable
fun HomeScreen(
    navController: NavHostController,
    db: FirebaseFirestore,
    pseudoList: SnapshotStateList<Pseudo>,
    checkedList: SnapshotStateList<Boolean>
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(point, zoom)
    }
    val focusManager = LocalFocusManager.current
    var searchInput by remember { mutableStateOf("") }

    var isDropDownMenuExtended by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val options = GoogleMapOptions()
    options.compassEnabled(false)
        .tiltGesturesEnabled(false)
        .liteMode(true)

    var searchReligionExtended by remember { mutableStateOf(false) }
    var searchChurchExtended by remember { mutableStateOf(false) }
    val rRotationState by animateFloatAsState(
        targetValue = if (searchReligionExtended) 180f else 0f
    )
    val cRotaionState by animateFloatAsState(
        targetValue = if (searchChurchExtended) 180f else 0f
    )
    val pseudoScrollState = rememberScrollState()
    val addressScrollSate = rememberScrollState()

    var isMoved by remember {
        mutableStateOf(false)
    }

    Box{
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isBuildingEnabled = true,
                isIndoorEnabled = false,
                minZoomPreference = 7f
            ),
            uiSettings = MapUiSettings(
                tiltGesturesEnabled = false
            ),

        ) {
            for ((i, pseudo) in pseudoList.withIndex()){
                if (checkedList[i]) {
                    for (address in pseudo.address) {
                        MarkerInfoWindow(
                            state = MarkerState(position = address.latlng),
                            icon = bitmapDescriptorFromVector(
                                context, R.drawable.default_marker
                            ),
                        ){
                            Column(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colors.background,
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .border(
                                        color = MaterialTheme.colors.surface,
                                        width = 1.9.dp,
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = address.name,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    ),
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                                Text(
                                    text = pseudo.pseudoName,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 5.dp)
                                )
                                Text(
                                    text = address.address,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp
                                    ),
                                )
                            }
                        }
                    }
                    MarkerInfoWindow(
                        state = MarkerState(position = pseudo.latlng),
                        icon = bitmapDescriptorFromVector(
                            context, R.drawable.marker_home
                        ),
                        onInfoWindowClick = {

                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colors.background,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .border(
                                    color = MaterialTheme.colors.surface,
                                    width = 1.9.dp,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .padding(15.dp)
                        ) {
                            Text(
                                text = pseudo.pseudoName,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier
                                    .padding(bottom = 5.dp)
                            )
                            Text(
                                text = pseudo.location,
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row {
                Spacer (modifier = Modifier.weight(1f))
                PseudoCheckListButton(navController)
                DropDownMenuButton(
                    isDropDownMenuExtended = isDropDownMenuExtended,
                    onClick = { isDropDownMenuExtended = true },
                    onDismissRequest = { isDropDownMenuExtended = false },
                    navController = navController,
                    focusManager = focusManager,
                    db = db,
                    context = context
                )
            }
            Column() {
                Column() {
                    if (isMoved) {
                        LaunchedEffect(key1 = true) {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newCameraPosition(
                                    CameraPosition(point, zoom, 0f, 0f)
                                ),
                                durationMs = 1000
                            )
                            if (!cameraPositionState.isMoving)  isMoved = false
                        }
                    }
                    SearchResultCard(
                        searchInput.length, searchInput.isNotEmpty(), searchInput, pseudoList,
                        searchReligionExtended, {
                            searchReligionExtended = !searchReligionExtended
                            focusManager.clearFocus()
                                                },
                        searchChurchExtended, {
                            searchChurchExtended = !searchChurchExtended
                            focusManager.clearFocus()
                                              },
                        rRotationState, cRotaionState, navController,{ p, z -> point = p
                            zoom = z
                            isMoved = true
                            focusManager.clearFocus()
                            searchInput = ""},pseudoScrollState,addressScrollSate)}
                SearchBar(
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    value = searchInput,
                    onValueChange = { searchInput = it }
                )
            }
        }
    }
}



@Composable
fun PseudoCheckListButton(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { navController.navigate("pseudo_checklist_screen") },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 20.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        modifier = modifier
            .padding(10.dp)
            .height(55.dp)
    ) {
        Text(
            text = stringResource(id = R.string.choose_pseudo),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .align(Alignment.CenterVertically)
        )
    }
}



@Composable
fun DropDownMenuButton(
    isDropDownMenuExtended: Boolean,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
    navController: NavHostController,
    focusManager: FocusManager,
    db: FirebaseFirestore,
    context: Context,
    modifier: Modifier = Modifier
) {
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

        val openDialog = remember { mutableStateOf(false) }
        val openReCheckDialog = remember { mutableStateOf(false) }
        var text by remember { mutableStateOf("") }

        DropDownMenuButton(
            onClick = { openDialog.value = true },
            loc = Loc.MIDDLE, text = R.string.request_modify_info
        )
        RequestInfoDialog(
            openDialog = openDialog.value,
            onDismissRequest = { openDialog.value = false },
            value = text,
            onValueChange = { text = it },
            keyboardAction = KeyboardActions(
                onNext = { focusManager.clearFocus()},
                onDone = {  /*위치검색기능*/}
            ),
            cancelClick = {
                openDialog.value = false
                text = ""},
            okClick = {
                openDialog.value = false
                openReCheckDialog.value = true },
            isTextEmpty =  text.isNotEmpty()
        )
        ReCheckDialog(
            openDialog = openReCheckDialog.value,
            onDismissRequest = { openReCheckDialog.value = false },
            text = text,
            editClick = {
                openReCheckDialog.value = false
                openDialog.value = true
            },
            okClick = {
                openReCheckDialog.value = false
                val request = hashMapOf(
                    "request" to text
                )

                db.collection("information_modification_request")
                    .add(request)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        Toast.makeText(context, "요청 성공", Toast.LENGTH_SHORT).show()
                        text = ""
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(context, "요청 실패", Toast.LENGTH_SHORT).show()
                    }

            }
        )

        DropDownMenuButton(
            onClick = { navController.navigate("standard_screen") },
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


@Composable
fun SearchBar(
    keyboardActions: KeyboardActions,
    value: String,
    onValueChange: (String) -> Unit
) {
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colors.secondary,
        unfocusedBorderColor =  MaterialTheme.colors.surface ,
        backgroundColor =  MaterialTheme.colors.surface )


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
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 10.dp)
            .height(60.dp)
            .shadow(elevation = 20.dp, RoundedCornerShape(20.dp)),
        )
}
