package com.example.zothikes
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

//marker with address


class NearbyMap : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        intent.getStringExtra("latitudes0")?.let { Log.d("Element 0", it) }
//        intent.getStringExtra("longitudes0")?.let { Log.d("Element 0", it) }
//        intent.getStringExtra("0")?.let { Log.d("Element 0", it) }
//        intent.getStringExtra("latitudes1")?.let { Log.d("Element 1", it) }
//        intent.getStringExtra("longitudes1")?.let { Log.d("Element 1", it) }
//        intent.getStringExtra("1")?.let { Log.d("Element 1", it) }
//        intent.getStringExtra("latitudes2")?.let { Log.d("Element 2", it) }
//        intent.getStringExtra("longitudes2")?.let { Log.d("Element 2", it) }
//        intent.getStringExtra("2")?.let { Log.d("Element 2", it) }
//        intent.getStringExtra("latitudes3")?.let { Log.d("Element 3", it) }
//        intent.getStringExtra("longitudes3")?.let { Log.d("Element 3", it) }
//        intent.getStringExtra("3")?.let { Log.d("Element 3", it) }
//        intent.getStringExtra("latitudes4")?.let { Log.d("Element 4", it) }
//        intent.getStringExtra("longitudes4")?.let { Log.d("Element 4", it) }
//        intent.getStringExtra("4")?.let { Log.d("Element 4", it) }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?) = false

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)

                intent.getStringExtra("latitudes0")?.let { placeMarkerOnMap2(it, intent.getStringExtra("longitudes0")!!, intent.getStringExtra("0")) }
                intent.getStringExtra("latitudes1")?.let { placeMarkerOnMap2(it, intent.getStringExtra("longitudes1")!!, intent.getStringExtra("1")) }
                intent.getStringExtra("latitudes2")?.let { placeMarkerOnMap2(it, intent.getStringExtra("longitudes2")!!, intent.getStringExtra("2")) }
                intent.getStringExtra("latitudes3")?.let { placeMarkerOnMap2(it, intent.getStringExtra("longitudes3")!!, intent.getStringExtra("3")) }
                intent.getStringExtra("latitudes4")?.let { placeMarkerOnMap2(it, intent.getStringExtra("longitudes4")!!, intent.getStringExtra("4")) }
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    private fun placeMarkerOnMap2(lat:String, long:String, name: String?) {
        val curLatLng = LatLng(lat.toDouble(),long.toDouble())
        val markerOptions = MarkerOptions().position(curLatLng)

        markerOptions.title(name)

        map.addMarker(markerOptions)
    }
    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)

        map.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

/**
 * this code is to print all the of the details in the address from geocoder
            var counter = 0
            var swag = ""
            while(counter < addresses.size)
            {
                swag += addresses[counter]
                counter++
            }
            Toast.makeText(this, "address contents: $swag", Toast.LENGTH_SHORT).show()
            */


            // 3

            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                /**
                Toast.makeText(this, "City: " + addresses[0].locality, Toast.LENGTH_SHORT).show()
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
                Toast.makeText(this, "end result: $addressText", Toast.LENGTH_SHORT).show()
                */
                addressText = addresses[0].locality //city only temporarily
            }

        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

}



