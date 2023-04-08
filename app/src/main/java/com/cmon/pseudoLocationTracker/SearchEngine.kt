package com.cmon.pseudoLocationTracker

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cmon.pseudoLocationTracker.data.Pseudo
import com.cmon.pseudoLocationTracker.data.PseudoAddress
import com.google.android.gms.maps.model.LatLng

@Composable
fun SearchResult(
    searchInputSize: Int,
    isEnabled: Boolean,
    searchInput: String,
    pseudoList: SnapshotStateList<Pseudo>,
    religionExtended: Boolean,
    onReligionClick: () -> Unit,
    churchExtended: Boolean,
    onChurchClick: () -> Unit,
    religionRotationState: Float,
    churchRotationState: Float,
    navController: NavController,
    onAddressClick: (LatLng, Float) -> Unit,
) {
    val searchResultsModifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .background(
            color = MaterialTheme.colors.background,
            shape = RoundedCornerShape(20.dp)
        )
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

    if (isEnabled && searchInputSize >= 2) {
        val pseudoSearchList = mutableListOf<Pseudo>()
        val addressSearchList = mutableListOf<PseudoAddress>()

        val addResultFunction: (Pseudo) -> Unit = {pseudo ->
            pseudoSearchList.add(pseudo)
            addressSearchList.addAll(pseudo.address)
        }

        for (pseudo in pseudoList) {
            if (!pseudoSearchList.contains(pseudo)) {
                if (compareString(pseudo.pseudoName, searchInput)) addResultFunction(pseudo)
                if (compareString(pseudo.location, searchInput)) addResultFunction(pseudo)
                if (compareString(pseudo.initiator, searchInput)) addResultFunction(pseudo)
                if (compareString(pseudo.representative, searchInput)) addResultFunction(pseudo)
            }

            for (loc in pseudo.address) {
                if (!addressSearchList.contains(loc)) {
                    if (compareString(loc.name, searchInput)) addressSearchList.add(loc)
                    if (compareString(loc.address, searchInput)) addressSearchList.add(loc)
                }
            }
        }//검색 일치하는 것 리스트에 추가

        if (pseudoSearchList.size == 0 && addressSearchList.size == 0)
            FloatingText(stringRes = R.string.no_results)
        else if(pseudoSearchList.size != 0 || addressSearchList.size != 0){
            PseudoSearchResults(
                pseudo = pseudoSearchList,
                isExtended = religionExtended,
                isAddressExtended = churchExtended,
                onExtendClick = onReligionClick,
                rotationState = religionRotationState,
                navController = navController,
                modifier = searchResultsModifier
            )

            ChurchSearchResults(
                addresses = addressSearchList,
                isExtended = churchExtended,
                isPseudoExtended = religionExtended,
                onExtendClick = onChurchClick,
                rotationState = churchRotationState,
                onClick = onAddressClick,
                modifier = searchResultsModifier
            )
        }
    } else if (isEnabled)
        FloatingText(stringRes = R.string.more_than_two)


}

@Composable
fun ChurchSearchResults(
    addresses: MutableList<PseudoAddress>,
    isExtended: Boolean,
    isPseudoExtended: Boolean,
    onExtendClick: () -> Unit,
    rotationState: Float,
    onClick: (LatLng, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SearchResultBar(
            stringRes = R.string.church,
            listSize = addresses.size,
            isOtherExtended = !isPseudoExtended,
            onExtendClick = onExtendClick,
            rotationState = rotationState
        )
        if (isExtended && !isPseudoExtended) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                items(addresses) {
                    ChurchResultCard(it, onClick)
                }
            }
        } else if (!isPseudoExtended) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (addresses.size <= 2)
                    for (address in addresses) ChurchResultCard(address, onClick)
                else
                    for (i in 0..1) ChurchResultCard(addresses[i],onClick)
            }
        }
    }
}

@Composable
fun PseudoSearchResults(
    pseudo: MutableList<Pseudo>,
    isExtended: Boolean,
    isAddressExtended: Boolean,
    onExtendClick: () -> Unit,
    rotationState: Float,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        SearchResultBar(
            stringRes = R.string.religion,
            listSize = pseudo.size,
            isOtherExtended = !isAddressExtended,
            onExtendClick = onExtendClick,
            rotationState = rotationState
        )
        if (!isAddressExtended)
            if (isExtended) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                ) {
                    items(pseudo) {
                        PseudoResultCard(it, navController)
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (pseudo.size <= 2)
                        for (p in pseudo) PseudoResultCard(p, navController)
                    else
                        for (i in 0..1) PseudoResultCard(pseudo[i], navController)
                }
            }
        }
    }


@Composable
fun PseudoResultCard(
    pseudo: Pseudo,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                Pseudo = pseudo
                navController.navigate("pseudo_info_screen")
            })
    ){
        Text(
            text = pseudo.pseudoName,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 3.dp, bottom = 1.dp)
                .fillMaxWidth()
        ){
            Text(
                text = stringResource(id = R.string.representative),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                )
            )
            Text(
                text = pseudo.representative,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                )
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 12.dp)
                .fillMaxWidth()
        ){
            Text(
                text = stringResource(id = R.string.num),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                )
            )
            Text(
                text = pseudo.address.size.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
fun ChurchResultCard(
    pseudo: PseudoAddress,
    onClick: (LatLng, Float) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(pseudo.latlng, 15f) })
    ){
        Text(
            text = pseudo.name,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 1.dp)
        )
        Text(
            text = pseudo.pseudoName,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp
            ),
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 3.dp, bottom = 1.dp)
                .fillMaxWidth()
        )
        Text(
            text = pseudo.address,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp
            ),
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 12.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun FloatingText(
    @StringRes stringRes: Int
) {
    Text(
        text = stringResource(id = stringRes),
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            shadow = Shadow(Color(0xFF000000), blurRadius = 3f),
            color = MaterialTheme.colors.surface
        ),textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(start = 26.dp, end = 26.dp, top = 10.dp, bottom = 26.dp)
            .fillMaxWidth()
    )
}

@Composable
fun SearchResultBar(
    @StringRes stringRes: Int,
    listSize: Int,
    isOtherExtended: Boolean,
    onExtendClick: () -> Unit,
    rotationState: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        if (isOtherExtended) {
            if (listSize > 0) {
                Text(
                    text = stringResource(id = stringRes),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
            if (listSize >= 3) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onExtendClick,modifier = Modifier.rotate(rotationState)) {
                    Icon(
                        painter = painterResource(id = R.drawable.expand_less_icon),
                        contentDescription = stringResource(R.string.expand_button_content_description),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
    if (listSize > 0) Divider(color = MaterialTheme.colors.surface)
}

fun compareString(
    original: String,
    target: String
): Boolean {
    if (original.length > target.length)
        if (original.contains(target)) return true
        else if (original.length == target.length) {
            var cnt = 0
            for (i in original.indices)
                if (original[i] != target[i]) cnt++
            if (cnt <= 1) return true
        } else return target.contains(original)
    return false
}