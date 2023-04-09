package com.cmon.pseudoLocationTracker

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cmon.pseudoLocationTracker.data.Pseudo
import com.cmon.pseudoLocationTracker.data.PseudoAddress
import com.cmon.pseudoLocationTracker.screen.*
import com.cmon.pseudoLocationTracker.ui.theme.PseudoLocationTrackerTheme
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

private const val PREF_KEY_FIRST_LAUNCH = "first_launch"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getPreferences(Context.MODE_PRIVATE)
        val isFirstLaunch = preferences.getBoolean(PREF_KEY_FIRST_LAUNCH, true)

        setContent {
            PseudoLocationTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PLTapp(isFirstLaunch, preferences)
                }
            }
        }
    }
}

val NULL_PSEUDO = Pseudo("","","","","","","", relativeInstitutions = "", crime = "",source = "", locsource = "",latlng = LatLng(0.0,0.0),location = "")
var PSEUDO = NULL_PSEUDO

@SuppressLint("UnrememberedMutableState")
@Composable
fun PLTapp(
    isFirstLaunch: Boolean,
    preferences: SharedPreferences
) {
    val navController = rememberNavController()

    val pseudoList = mutableStateListOf<Pseudo>()
    val expandedList = mutableStateListOf<Boolean>()
    val checkedList = mutableStateListOf<Boolean>()
    val context = LocalContext.current
    val db = Firebase.firestore
    val pseudo_location = db.collection("pseudo_location")

    pseudo_location
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val name = document.get("name").toString()
                    val addressList = mutableStateListOf<PseudoAddress>()
                    pseudo_location
                        .document(document.id)
                        .collection("locations")
                        .get()
                        .addOnSuccessListener { docs ->
                            for (doc in docs) {
                                if (doc != null) {
                                    val geoPoint = doc.getGeoPoint("geopoint")
                                    if (geoPoint != null){
                                        val lat = geoPoint.latitude
                                        val long = geoPoint.longitude
                                        addressList.add(PseudoAddress(
                                            pseudoName = name,
                                            name = doc.get("name").toString(),
                                            address = doc.id,
                                            latlng = LatLng(lat, long)
                                        ))
                                    }

                                }
                                else {
                                    Log.d(TAG, "No such document")
                                    Toast.makeText(context, "문서 없음", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error getting documents.", exception)
                            Toast.makeText(context, "종교 위치 불러오기 실패", Toast.LENGTH_SHORT).show()
                        }

                    val gp = document.getGeoPoint("latlng")
                    var lat = 0.0
                    var lng = 0.0
                    if (gp != null){
                        lat = gp.latitude
                        lng = gp.longitude
                    }
                    pseudoList.add(
                        Pseudo(
                            pseudoName = name,
                            representative = document.get("representative").toString(),
                            initiator = document.get("initiator").toString(),
                            site = document.get("site").toString(),
                            organization = document.get("organization").toString().replace("\\n", "\n"),
                            explanation = document.get("detail").toString().replace("\\n", "\n"),
                            pseudo = document.get("pseudo").toString(),
                            address = addressList,
                            relativeInstitutions = document.get("RelatedInstitutions").toString().replace("\\n", "\n"),
                            crime = document.get("crime").toString().replace("\\n", "\n"),
                            source = document.get("source").toString(),
                            locsource = document.get("locsource").toString(),
                            latlng = LatLng(lat, lng),
                            location = document.get("location").toString()
                            ))
                    checkedList.add(true)
                    expandedList.add(false)
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Toast.makeText(context, "불러오기 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "No such document")
                    Toast.makeText(context, "문서 없음", Toast.LENGTH_SHORT).show()
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
            Toast.makeText(context, "종교 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
        }

    val onAllTrueChange = {
        for (i in 0 until checkedList.size)
            if (!checkedList[i]) checkedList[i] = true
    }
    val onAllFalseChange = {
        for (i in 0 until checkedList.size)
            if (checkedList[i]) checkedList[i] = false
    }

    NavHost(navController, startDestination = "home_screen") {
        composable("home_screen") { HomeScreen(navController, db, pseudoList, checkedList) }
        composable("pseudo_checklist_screen") {
            PseudoChecklistScreen(
                navController, pseudoList,
                expandedList, checkedList,
                onAllTrueChange, onAllFalseChange) }
        composable("report_screen") { ReportScreen(navController, db) }
        composable("criteria_screen") { CriteriaScreen(navController) }
        composable("info_screen") { InfoScreen(navController)}
        composable("pseudo_info_screen") { PseudoInfoScreen(navController) }
        composable("first_screen") { FirstScreen() {
            preferences.edit().putBoolean(PREF_KEY_FIRST_LAUNCH, false).apply()
            navController.navigate("home_screen")
        }
        }
    }
    if (isFirstLaunch) {
        navController.navigate("first_screen")
    }
}