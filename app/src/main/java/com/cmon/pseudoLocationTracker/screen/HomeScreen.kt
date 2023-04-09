package com.cmon.pseudoLocationTracker.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmon.pseudoLocationTracker.*
import com.cmon.pseudoLocationTracker.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.cmon.pseudoLocationTracker.data.Pseudo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.*
import com.cmon.pseudoLocationTracker.composable.*

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
                PseudoDropDownMenuButton(
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