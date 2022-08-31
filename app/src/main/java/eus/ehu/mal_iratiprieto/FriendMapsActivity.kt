
package eus.ehu.mal_iratiprieto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import model.backend.Data

class FriendMapsActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var find_location_btn: Button

    private lateinit var data: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_maps)

        find_location_btn = findViewById(R.id.btn_find_location_friend)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_friend) as SupportMapFragment
        mapFragment.getMapAsync(this)


        data = Data.getInstance()


            find_location_btn.setOnClickListener {
                for (i in data.user.friendLocations){
                    val locationLat = i.latitude
                    val locationLong = i.longitude
                    val date = i.date
                    // check if the latitude and longitude is not null
                    if (locationLat != null && locationLong!= null) {
                        // create a LatLng object from location
                        val latLng = LatLng(locationLat, locationLong)

                        //create a marker at the read location and display it on the map
                        map.addMarker(MarkerOptions().position(latLng)
                            .title(date +": " + locationLat + ", " +locationLong))
                        val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                        //update the camera with the CameraUpdate object
                        map.moveCamera(update)
                    }
                    else {
                        // if location is null , log an error message
                        Log.e(TAG, "No location found")
                    }
                }

            }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        if (googleMap != null) {
            map = googleMap
        }
    }
    companion object {
        private const val TAG = "FriendMapsActivity" // for debugging
    }


}

