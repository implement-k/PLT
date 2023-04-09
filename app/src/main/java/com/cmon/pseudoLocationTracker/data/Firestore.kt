package com.cmon.pseudoLocationTracker.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore

fun LoadingData(
    db: FirebaseFirestore,
    context: Context,
    pseudoList: SnapshotStateList<Pseudo>,
    checkedList: SnapshotStateList<Boolean>,
    expandedList: SnapshotStateList<Boolean>
) {
    val pseudoLocations = db.collection("pseudo_location")

    pseudoLocations
        .get()
        .addOnSuccessListener { pseudoDocuments ->
            for (pseudoDocument in pseudoDocuments) {
                if (pseudoDocument != null) {
                    val name = pseudoDocument.get("name").toString()
                    val addressList = mutableStateListOf<PseudoAddress>()

                    pseudoLocations
                        .document(pseudoDocument.id)
                        .collection("locations")
                        .get()
                        .addOnSuccessListener { addressDocuments ->
                            for (addressDocument in addressDocuments) {
                                if (addressDocument != null) {
                                    val geoPoint = addressDocument.getGeoPoint("geopoint")
                                    if (geoPoint != null){
                                        val lat = geoPoint.latitude
                                        val long = geoPoint.longitude
                                        addressList.add(PseudoAddress(
                                            pseudoName = name,
                                            name = addressDocument.get("name").toString(),
                                            address = addressDocument.id,
                                            latlng = LatLng(lat, long)
                                        ))
                                    }

                                }
                                else {
                                    Toast.makeText(context, "문서 없음", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "종교 위치 불러오기 실패", Toast.LENGTH_SHORT).show()
                        }

                    val gp = pseudoDocument.getGeoPoint("latlng")
                    var lat = 0.0
                    var lng = 0.0
                    if (gp != null){
                        lat = gp.latitude
                        lng = gp.longitude
                    }
                    pseudoList.add(
                        Pseudo(
                            pseudoName = name,
                            representative = pseudoDocument.get("representative").toString(),
                            initiator = pseudoDocument.get("initiator").toString(),
                            site = pseudoDocument.get("site").toString(),
                            organization = pseudoDocument.get("organization").toString().replace("\\n", "\n"),
                            explanation = pseudoDocument.get("detail").toString().replace("\\n", "\n"),
                            pseudo = pseudoDocument.get("pseudo").toString(),
                            address = addressList,
                            relativeInstitutions = pseudoDocument.get("RelatedInstitutions").toString().replace("\\n", "\n"),
                            crime = pseudoDocument.get("crime").toString().replace("\\n", "\n"),
                            source = pseudoDocument.get("source").toString(),
                            locsource = pseudoDocument.get("locsource").toString(),
                            latlng = LatLng(lat, lng),
                            location = pseudoDocument.get("location").toString()
                        ))
                    checkedList.add(true)
                    expandedList.add(false)
                    Toast.makeText(context, "불러오기 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "문서 없음", Toast.LENGTH_SHORT).show()
                }
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "종교 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
        }
}