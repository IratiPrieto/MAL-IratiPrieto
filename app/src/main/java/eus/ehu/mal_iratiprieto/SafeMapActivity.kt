
package eus.ehu.mal_iratiprieto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import model.backend.Backend
import model.backend.Data
import model.backend.Locator


class SafeMapActivity: AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private lateinit var map: GoogleMap
    private lateinit var save_location_btn: Button

    private lateinit var data: Data
    private var safeLat: Double = 0.0
    private var safeLong: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safe_map)

        save_location_btn = findViewById(R.id.btn_save_safeLocation)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapSafe) as SupportMapFragment
        mapFragment.getMapAsync(this)

//SET INITIAL MARKER ACCORDING TO DB DATA


//REWRITE DB DATA ACCORDING TO NEW MARKER
        save_location_btn.setOnClickListener {

            data = Data.getInstance()
            data.user.safeLat = safeLat
            data.user.safeLong = safeLong
      //      Log.e(TAG, "safeLat: "+safeLat+"    safeLong: "+safeLong)
            saveAndGoBack()

        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        if (googleMap != null) {
            map = googleMap
        }
        map.setOnMarkerDragListener(this)
        data = Data.getInstance()
        var locationLat = data.user.safeLat
        var locationLong = data.user.safeLong
        // check if the latitude and longitude is not null
        if (locationLat != null && locationLong!= null) {
            // create a LatLng object from location
            val latLng = LatLng(locationLat, locationLong)

            //create a marker at the read location and display it on the map
            map.addMarker(MarkerOptions().position(latLng)
                .title("The user is currently here")
                .draggable(true))
            val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
            //update the camera with the CameraUpdate object
            map.moveCamera(update)
        }
        else {
            // if location is null , log an error message
            Log.e(TAG, "No location found")
        }
    }
    companion object {
        private const val TAG = "MapsActivity" // for debugging
    }

    override fun onMarkerDragStart(marker: Marker?) {
        if (marker != null) {
            safeLat = marker.position.latitude
            safeLong = marker.position.longitude
        }
    }

    override fun onMarkerDragEnd(marker: Marker?) {
        if (marker != null) {
            safeLat = marker.position.latitude
            safeLong = marker.position.longitude
        }

     //   Log.e(TAG, "safeLat: "+safeLat+"    safeLong: "+safeLong)
    }

    override fun onMarkerDrag(marker: Marker) {
        if (marker != null) {
            safeLat = marker.position.latitude
            safeLong = marker.position.longitude
        }
    }
    fun saveAndGoBack(){
        var backend: Backend

        Locator.getBackend().setUserSafeData()
            .addOnCompleteListener { task: Task<DataSnapshot?> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Safe position saved", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                finish()
            }
    }
}

