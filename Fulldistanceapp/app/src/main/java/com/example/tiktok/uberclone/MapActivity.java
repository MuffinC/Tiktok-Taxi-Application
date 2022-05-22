package com.example.tiktok.uberclone;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tiktok.uberclone.databinding.ActivityDriverMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    GoogleMap mMap;
    ImageView imageViewSearch;
    EditText inputLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        imageViewSearch=findViewById(R.id.imageViewSearchButton);
        inputLocation=findViewById(R.id.inputLocation);


        SupportMapFragment supportMapFragment= SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container,supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);

        //Searchbutton
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location =inputLocation.getText().toString();
                if (location==null){
                    Toast.makeText(MapActivity.this,"Type Starting Point",Toast.LENGTH_SHORT).show();
                }else{
                    Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                    try {
                        List<Address>listAddress=geocoder.getFromLocationName(location,1);
                        if(listAddress.size()>0){
                            LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
                            //^will be the user's current position

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.title("Location 1");
                            markerOptions.position(latLng);
                            mMap.addMarker(markerOptions);

                            //Camera update
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17f);
                            mMap.animateCamera(cameraUpdate);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //when the map is ready for use
        LatLng latlng = new LatLng(1.340165306, 103.675497298);
        //^will be the user's current position

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("My Current Location");
        markerOptions.position(latlng);
        mMap.addMarker(markerOptions);

        //Camera update
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 13f);
        mMap.animateCamera(cameraUpdate);

        /*
        //Adding zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Adding compass
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);


        //My Location button
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.noneMap){
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
        if(item.getItemId() == R.id.NormalMap){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        return super.onOptionsItemSelected(item);
    }
}