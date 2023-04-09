package com.cmon.pseudoLocationTracker.data

import com.google.android.gms.maps.model.LatLng

data class Pseudo (
    val pseudoName: String = "",
    val representative: String = "",
    val initiator: String = "",
    val site: String = "",
    val organization: String = "",
    val explanation: String = "",
    val pseudo: String = "",
    val address: MutableList<PseudoAddress> = mutableListOf(),
    val relativeInstitutions: String = "",
    val crime: String = "",
    val source: String = "",
    val locsource: String = "",
    val latlng: LatLng = LatLng(0.0,0.0),
    val location: String = ""
)

data class PseudoAddress (
    val pseudoName: String,
    val name: String,
    val address: String,
    val latlng: LatLng
        )
