package br.edu.computacaoifg.Carga
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
class LocationProvider(private val mContext: Context, private val mLocationCallback: LocationCallback) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private val mGoogleApiClient: GoogleApiClient
    private val mLocationRequest: LocationRequest

    interface LocationCallback {
        fun handleNewLocation(location: Location)
    }

    init {
        mGoogleApiClient = GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval((10 * 1000).toLong())        // 10 seconds, in milliseconds
                .setFastestInterval((1 * 1000).toLong()) // 1 second, in milliseconds
    }

    fun connect() {
        mGoogleApiClient.connect()
    }

    fun disconnect() {
        if (mGoogleApiClient.isConnected) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
            mGoogleApiClient.disconnect()
        }
    }

    override fun onConnected(bundle: Bundle?) {
        Log.i(TAG, "Location services connected.")

        val location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        } else {
            mLocationCallback.handleNewLocation(location)
        }
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution() && mContext is Activity) {
            try {
// Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(mContext, CONNECTION_FAILURE_RESOLUTION_REQUEST)
                /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
            } catch (e: IntentSender.SendIntentException) {
                // Log the error
                e.printStackTrace()
            }

        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.errorCode)
        }
    }

    override fun onLocationChanged(location: Location) {
        mLocationCallback.handleNewLocation(location)
    }

    companion object {

        val TAG = LocationProvider::class.java.simpleName

        /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
        private val CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000
    }
}
