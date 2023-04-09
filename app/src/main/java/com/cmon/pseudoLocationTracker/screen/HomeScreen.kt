package com.cmon.pseudoLocationTracker.screen

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmon.pseudoLocationTracker.*
import com.cmon.pseudoLocationTracker.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.cmon.pseudoLocationTracker.composable.ReCheckDialog
import com.cmon.pseudoLocationTracker.composable.RequestInfoDialog
import com.cmon.pseudoLocationTracker.composable.SearchBar
import com.cmon.pseudoLocationTracker.composable.bitmapDescriptorFromVector
import com.cmon.pseudoLocationTracker.data.Pseudo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.*

var Point = LatLng(36.34, 127.77)
var Zoom = 7f

@Composable
fun HomeScreen(
    navController: NavHostController,
    db: FirebaseFirestore,
    pseudoList: SnapshotStateList<Pseudo>,
    checkedList: SnapshotStateList<Boolean>
) {
    val context = LocalContext.current
    val focusManager: FocusManager = LocalFocusManager.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(Point, Zoom)
    }


    var searchInput by remember { mutableStateOf("") }
    var searchReligionExtended by remember { mutableStateOf(false) }
    var searchChurchExtended by remember { mutableStateOf(false) }
    val rRotationState by animateFloatAsState(
        targetValue = if (searchReligionExtended) 180f else 0f
    )
    val cRotaionState by animateFloatAsState(
        targetValue = if (searchChurchExtended) 180f else 0f
    )

    var isDropDownMenuExtended by remember { mutableStateOf(false) }

    val options = GoogleMapOptions()
    options.compassEnabled(false)
        .tiltGesturesEnabled(false)
        .liteMode(true)

    var isMoved by remember { mutableStateOf(false) }

    Box{
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isIndoorEnabled = false,
                minZoomPreference = 7f,
                latLngBoundsForCameraTarget = LatLngBounds(LatLng(32.633855539005914, 125.38502187544947),LatLng(39.23754092085378, 131.6586979462642))
            ),
            uiSettings = MapUiSettings(
                tiltGesturesEnabled = false
            )
        ) {
            val markerIcon = bitmapDescriptorFromVector(
                context, R.drawable.maker_default
            )
            val headquartersMarkerIcon = bitmapDescriptorFromVector(
                context, R.drawable.marker_headquarters
            )

            for ((i, pseudo) in pseudoList.withIndex()){
                if (checkedList[i])  {
                    for (address in pseudo.address) {
                        MarkerInfoWindow(
                            state = MarkerState(position = address.latlng),
                            icon = markerIcon,
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
                        icon = headquartersMarkerIcon,
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
                                    CameraPosition(Point, Zoom, 0f, 0f)
                                ),
                                durationMs = 1000
                            )
                            if (!cameraPositionState.isMoving)  isMoved = false
                        }
                    }
                    SearchResult(
                        searchInputSize = searchInput.length,
                        isEnabled = searchInput.isNotEmpty(),
                        searchInput = searchInput,
                        pseudoList = pseudoList,
                        religionExtended = searchReligionExtended,
                        onReligionClick = {
                            searchReligionExtended = !searchReligionExtended
                            focusManager.clearFocus()
                                                },
                        churchExtended = searchChurchExtended,
                        onChurchClick = {
                            searchChurchExtended = !searchChurchExtended
                            focusManager.clearFocus()
                                              },
                        religionRotationState = rRotationState,
                        churchRotationState = cRotaionState,
                        navController = navController,
                        onAddressClick = { latlng, zoom ->
                            Point = latlng
                            Zoom = zoom
                            isMoved = true
                            focusManager.clearFocus()
                            searchInput = ""
                            searchReligionExtended = false
                            searchChurchExtended = false
                        }
                    )
                }
                SearchBar(
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus()
                            searchReligionExtended = false
                            searchChurchExtended = false }
                    ),
                    value = searchInput,
                    onValueChange = { searchInput = it },
                    onCancelClick = {searchInput = ""},
                    isSearch = searchInput.isNotEmpty()
                )
            }
        }
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
                onNext = { focusManager.clearFocus()}
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