package com.cmon.pseudoLocationTracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import com.cmon.pseudoLocationTracker.data.LoadingData
import com.cmon.pseudoLocationTracker.data.Pseudo
import com.cmon.pseudoLocationTracker.screen.*
import com.cmon.pseudoLocationTracker.ui.theme.PseudoLocationTrackerTheme
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
                ) { PLTapp(isFirstLaunch, preferences) }
            }
        }
    }
}

val NULL_PSEUDO = Pseudo()
var PSEUDO = NULL_PSEUDO

@SuppressLint("UnrememberedMutableState")
@Composable
fun PLTapp(
    isFirstLaunch: Boolean,
    preferences: SharedPreferences
) {
    var isFirstRun by remember { mutableStateOf(true) }

    val navController = rememberNavController()

    val pseudoList = mutableStateListOf<Pseudo>()
    val expandedList = mutableStateListOf<Boolean>()
    val checkedList = mutableStateListOf<Boolean>()
    val db = Firebase.firestore

    if (isFirstRun) {
        LoadingData(
            db = db,
            context = LocalContext.current,
            pseudoList = pseudoList,
            checkedList = checkedList,
            expandedList = expandedList
        )
        isFirstRun = false
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