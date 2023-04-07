package com.cmon.pseudoLocationTracker

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun SearchResultCard(
    searchInputSize: Int,
    isEnabled: Boolean,
    searchInput: String,
    pseudoList: SnapshotStateList<Pseudo>,
    religionExtended: Boolean,
    onReligionClick: () -> Unit,
    churchExtended: Boolean,
    onChurchClick: () -> Unit,
    rRotationState: Float,
    cRotationState: Float,
    navController: NavController,
    onAddressClick: (LatLng, Float) -> Unit,
    rScrollState: ScrollState,
    cScrollState: ScrollState,
) {
    var isAddressResult by remember { mutableStateOf(false) }
    val selectedAddressCard = mutableListOf<Any>()

    if (isEnabled && searchInputSize >= 2) {
        val pseudoSearchList = mutableListOf<Pseudo>()
        val addressSearchList = mutableListOf<PseudoAddress>()
        val addressPseudo = mutableListOf<String>()

        for (pseudo in pseudoList) {
            if (compareString(pseudo.pseudoName, searchInput))
                if (!pseudoSearchList.contains(pseudo)) pseudoSearchList.add(pseudo)
            if (compareString(pseudo.location, searchInput))
                if (!pseudoSearchList.contains(pseudo)) pseudoSearchList.add(pseudo)
            if (compareString(pseudo.RelativeInstitutions, searchInput))
                if (!pseudoSearchList.contains(pseudo)) pseudoSearchList.add(pseudo)
            if (compareString(pseudo.initiator, searchInput))
                if (!pseudoSearchList.contains(pseudo)) pseudoSearchList.add(pseudo)
            if (compareString(pseudo.representative, searchInput))
                if (!pseudoSearchList.contains(pseudo)) pseudoSearchList.add(pseudo)

            for (loc in pseudo.address) {
                if (compareString(loc.name, searchInput))
                    if (!addressSearchList.contains(loc)) {
                        addressSearchList.add(loc)
                        addressPseudo.add(pseudo.pseudoName)
                    }
                if (compareString(loc.address, searchInput))
                    if (!addressSearchList.contains(loc)) {
                        addressSearchList.add(loc)
                        addressPseudo.add(pseudo.pseudoName)
                    }
            }
        }
        if (pseudoSearchList.size == 0 && addressSearchList.size == 0) {
            Text(
                text = stringResource(id = R.string.no_results),
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
        }else if(pseudoSearchList.size != 0 || addressSearchList.size != 0){
            PseudoSearchResults(pseudoSearchList, religionExtended, churchExtended,
            onReligionClick, rRotationState, rScrollState, navController)
            AddressSearchResults(
                addressSearchList, churchExtended, religionExtended,
                onChurchClick, cRotationState, onAddressClick,
                addressPseudo, cScrollState,
            ) { pa, r ->
                isAddressResult = true
                selectedAddressCard.add(pa)
                selectedAddressCard.add(r)
            }}

    } else if (isEnabled) {
        Text(
            text = stringResource(id = R.string.more_than_two),
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
}

fun compareString(original: String, target: String): Boolean {
    if (original.length > target.length) {
        if (original.contains(target)) return true
    } else if (original.length == target.length) {
        var cnt = 0
        for (i in original.indices) {
            if (original[i] != target[i]) cnt++
        }
        if (cnt <= 1) return true
    } else {
        if (target.contains(original)) return true
    }
    return false
}

@Composable
fun AddressSearchResults(
    locs: MutableList<PseudoAddress>,
    isExtended: Boolean,
    isPseudoExtended: Boolean,
    onExtendClick: () -> Unit,
    rotationState: Float,
    onClick: (LatLng, Float) -> Unit,
    locPseudo: MutableList<String>,
    scrollState: ScrollState,
    onCardClick: (PseudoAddress, String) -> Unit
) {
    Column(
        modifier = Modifier
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
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            if (locs.size > 0 && !isPseudoExtended) {
                Text(
                    text = stringResource(id = R.string.church),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
            if (locs.size >= 3 && !isPseudoExtended) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onExtendClick,modifier = Modifier.rotate(rotationState)) {
                    Icon(
                        painter = painterResource(id = R.drawable.expand_more_icon),
                        contentDescription = stringResource(R.string.expand_button_content_description),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
        if (locs.size > 0 && !isPseudoExtended) Divider(color = MaterialTheme.colors.surface)
        if (isExtended && !isPseudoExtended) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .verticalScroll(scrollState)
            ) {
                for ((ii,i) in locs.withIndex()) {
                    AddressResultCard(i, locPseudo[ii], onClick, onCardClick)
                }
            }
        } else if (!isPseudoExtended) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (locs.size <= 2) {
                    for ((ii, i) in locs.withIndex()) {
                        AddressResultCard(i, locPseudo[ii], onClick, onCardClick)
                    }
                } else {
                    for (i in 0..1) {
                        AddressResultCard(locs[i], locPseudo[i],onClick, onCardClick)
                    }
                }
            }
        }
    }
}

@Composable
fun AddressResultCard(
    pseudo: PseudoAddress,
    religion: String,
    onClick: (LatLng, Float) -> Unit,
    onCardClick: (PseudoAddress, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClick(pseudo.latlng, 15f)
                onCardClick(pseudo, religion)
            })
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
            text = religion,
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
fun PseudoSearchResults(
    pseudo: MutableList<Pseudo>,
    isExtended: Boolean,
    isAddressExtended: Boolean,
    onExtendClick: () -> Unit,
    rotationState: Float,
    scrollState: ScrollState,
    navController: NavController
) {
    Column(
        modifier = Modifier
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
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            if (pseudo.size > 0 && !isAddressExtended) {
                Text(
                    text = stringResource(id = R.string.religion),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
            if (pseudo.size >= 2 && !isAddressExtended) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onExtendClick,modifier = Modifier.rotate(rotationState)) {
                    Icon(
                        painter = painterResource(id = R.drawable.expand_more_icon),
                        contentDescription = stringResource(R.string.expand_button_content_description),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
        if (pseudo.size > 0 && !isAddressExtended) Divider(color = MaterialTheme.colors.surface)
        if (isExtended && !isAddressExtended) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .verticalScroll(scrollState)
            ) {
                for (i in pseudo) {
                    SearchResultCard(pseudo = i, navController = navController)
                }
            }
        } else if (!isAddressExtended) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (pseudo.size <= 2) {
                    for (i in pseudo) {
                        SearchResultCard(i, navController)
                    }
                } else {
                    for (i in 0..1) {
                        SearchResultCard(pseudo[i], navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultCard(
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