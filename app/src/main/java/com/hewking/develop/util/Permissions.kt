import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// implementation('androidx.appcompat:appcompat:1.3.0-alpha01')
// implementation('androidx.activity:activity-ktx:1.2.0-alpha06')
// implementation 'com.google.android.gms:play-services-location:17.0.0'
// implementation('androidx.fragment:fragment-ktx:1.3.0-alpha06')


// this is a quick demo of one way to use permissions in Compose by moving them out of the Activity
// using the activity result API.

// this code is written against a developer preview of Compose and alpha versions of various libraries, 
// and is likely out of date when you read it

// Note: for a compose-only solution check out https://gist.github.com/adamp/be8cb9c26bb9d198873f9d04d45c9355
//class MainActivity : AppCompatActivity() {
//
//    // these are scoped to the Activity, so you can create a PermissionState from a call like this
//    // in an Activity (you would use this if you wanted to pass it to somewhere other than Compose)
//    val fineLocationInActivity = checkSelfPermissionState(
//        "MainActivity",
//        Manifest.permission.ACCESS_FINE_LOCATION
//    )
//
//    @ExperimentalCoroutinesApi
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val fusedLocationWrapper = fusedLocationWrapper()
//
//        setContent{
//            // or we can instantiate it in compose like this (note: this needs an Activity argument)
//            val fineLocation = checkSelfPermissionState(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//
//            NeedsPermission(fineLocation, fusedLocationWrapper)
//        }
//    }
//}

class PermissionState(
    val permission: String,
    val hasPermission: Flow<Boolean>,
    val shouldShowRationale: Flow<Boolean>,
    private val launcher: ActivityResultLauncher<String>
) {
    fun launchPermissionRequest() = launcher.launch(permission)
}

@ExperimentalCoroutinesApi
private class PermissionResultCall(
    key: String,
    private val activity: AppCompatActivity,
    private val permission: String
) {

    // defer this to allow construction before onCreate
    private val hasPermission =  MutableStateFlow<Boolean?>(null)
    private val showRationale = MutableStateFlow<Boolean?>(null)

    // Don't do this in onCreate because compose setContent may be called in Activity usage before
    // onCreate is dispatched to this lifecycle observer (as a result, need to manually unregister)
    private var call: ActivityResultLauncher<String> = activity.activityResultRegistry.register(
        "LocationPermissions#($key)",
        ActivityResultContracts.RequestPermission()
    ) { result ->
        onPermissionResult(result)
    }

    /**
     * Call this after [Activity.onCreate] to perform the initial permissions checks
     */
    fun initialCheck() {
        hasPermission.value = checkPermission()
        showRationale.value = checkShowRationale()
    }

    fun unregister() {
        call.unregister()
    }

    fun checkSelfPermission(): PermissionState {
        return PermissionState(
            permission,
            hasPermission.filterNotNull(),
            showRationale.filterNotNull(),
            call
        )
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun checkShowRationale(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.shouldShowRequestPermissionRationale(permission)
        } else {
            false
        }
    }

    private fun onPermissionResult(result: Boolean) {
        hasPermission.value = result
        showRationale.value = checkShowRationale()
    }
}

/**
 * Instantiate a LocationPermissions object from inside an Activity.
 *
 * This will automatically register lifecycle hooks to cleanup.
 */
@ExperimentalCoroutinesApi
fun AppCompatActivity.checkSelfPermissionState(
    key: String, permission: String
): PermissionState {
    val caller = PermissionResultCall(key, this, permission)
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            caller.initialCheck()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            caller.unregister()
        }
    })
    return caller.checkSelfPermission()
}

/**
 * Instantiate and manage it in composition like this
 */
//@ExperimentalCoroutinesApi
//@Composable
//fun checkSelfPermissionState(
//    activity: AppCompatActivity,
//    permission: String
//): PermissionState {
//    val key = currentComposer.currentCompoundKeyHash.toString()
//    val call = remember(activity, permission) {
//        PermissionResultCall(key, activity, permission)
//    }
//    // drive initialCheck and unregister from composition lifecycle
//    onCommit(call) {
//        call.initialCheck()
//        onDispose {
//            call.unregister()
//        }
//    }
//    return call.checkSelfPermission()
//}

/**
 * A quick wrapper to expose fused location as Flow.
 *
 * Could also expose LiveData or State
 */
//@ExperimentalCoroutinesApi
//class FusedLocationWrapper(private val fusedLocation: FusedLocationProviderClient) {
//
//    @RequiresPermission(anyOf = arrayOf(
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION
//    ))
//    fun lastLocation(): Flow<Location> = flow {
//        emit(fusedLocation.lastLocation.await())
//    }
//
//    @RequiresPermission(anyOf = arrayOf(
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION
//    ))
//    fun requestLocationUpdates(
//        context: Context,
//        request: LocationRequest
//    ): Flow<List<Location>> = fusedLocation.locationFlow(request, context.mainLooper)
//
//    @RequiresPermission(anyOf = arrayOf(
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION
//    ))
//    private fun FusedLocationProviderClient.locationFlow(request: LocationRequest, looper: android.os.Looper) = callbackFlow<List<Location>> {
//        // code based on ktx codelab: https://codelabs.developers.google.com/codelabs/building-kotlin-extensions-library
//        val callback = object : LocationCallback() {
//            override fun onLocationResult(result: LocationResult?) {
//                result ?: return
//                try {
//                    offer(result.locations) // pass the locations directly from the API without modification
//                } catch (throwable: Throwable) {
//                    // channel was closed (possibly by cause)
//                }
//            }
//        }
//
//        requestLocationUpdates(
//            request,
//            callback,
//            looper
//        ).addOnFailureListener { e ->
//            close(e) // in case of exception, close the Flow
//        }
//
//        awaitClose {
//            removeLocationUpdates(callback) // clean up when Flow collection ends
//        }
//    }
//}

//@ExperimentalCoroutinesApi
//fun AppCompatActivity.fusedLocationWrapper()
//        = FusedLocationWrapper(LocationServices.getFusedLocationProviderClient(this))
//
//@SuppressLint("MissingPermission")
//@ExperimentalCoroutinesApi
//@Composable
//fun NeedsPermission(
//    fineLocation: PermissionState,
//    fusedLocationWrapper: FusedLocationWrapper
//) {
//
//    // read the current location permission using collectAsState (this will automatically
//    // collect changes and trigger recomposition)
//    val hasLocationPermission = fineLocation.hasPermission.collectAsState().value ?: return
//
//    // this will automatically be recomposed when locationPermissionGranted changes from the result
//    Column {
//        Text("Has location permissions: $hasLocationPermission")
//        if (hasLocationPermission) {
//            // has permission here
//
//            // we don't want a new flow every time this recomposes, so remember it
//            val locationFlow = remember(fusedLocationWrapper) {
//                fusedLocationWrapper.lastLocation()
//            }
//
//            // similarly, remember the flow here
//            val context = ContextAmbient.current
//            val locationUpdateFlow = remember(fusedLocationWrapper, context) {
//                fusedLocationWrapper.requestLocationUpdates(
//                    context,
//                    LocationRequest()
//                )
//            }
//
//            // and use `collectAsState` to read flows safely from composition
//            val lastLocation by locationFlow.collectAsState()
//            val locationUpdates by locationUpdateFlow.collectAsState()
//
//            Text("Thanks for all the permissions")
//            Text("lastLocation: $lastLocation")
//            Text("locationUpdates: $locationUpdates")
//        } else {
//            // user hasn't granted permission
//            fineLocation.shouldShowRationale.collectAsState().value?.let { showPrompt ->
//                if (showPrompt) {
//                    Text("We need location permission because this demo is about location permissions ✔️")
//                    Button(onClick = { fineLocation.launchPermissionRequest() }) {
//                        Text("Give permissions")
//                    }
//                } else {
//                    Text("Need permission, don't need to show rationale")
//                    Text("But, don't automatically prompt from compose (if you want that " +
//                            "–move it out of compose to e.g. the Activity")
//                    Text("If you try to prompt from composition (instead of onClick) you " +
//                            "may create an infinite prompt loop")
//                    Button(onClick = { fineLocation.launchPermissionRequest() }) {
//                        Text("OK")
//                    }
//                    Text("Hint: Try pressing deny")
//                }
//            }
//        }
//    }
//}
