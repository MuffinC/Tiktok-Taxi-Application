package com.example.tiktok.myapplication

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebStorage
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.security.KeyStore

class MainActivity : AppCompatActivity() {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            //for your own current location
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return@OnMapReadyCallback
            }

            googleMap.isMyLocationEnabled = true


                //this is where u pass your initial starting position/market
            //this is latitude and longitude
            val location1 = LatLng(1.3521,103.8198)
            googleMap.addMarker(MarkerOptions().position(location1).title("Loc1"))
            //this will snap to the current preset location that u designated above
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,11f))

            val location2 = LatLng(1.3321,103.7743)
            googleMap.addMarker(MarkerOptions().position(location2).title("Loc2"))

        })
    }
    fun getDirectionURL(origin: LatLng,dest:LatLng){
         val result = <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.740024833362!2d103.77215255082663!3d1.3321089619972692!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31da107d8eb4e359%3A0x75d2e7ffdeeb0c43!2sNgee%20Ann%20Polytechnic!5e0!3m2!1sen!2ssg!4v1652795401775!5m2!1sen!2ssg" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>

    }

}