package com.example.tiktok.uberclone;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    ImageView imageViewSearch;
    EditText inputLocation;
    com.google.android.gms.location.LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    private Button mlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        checkGooglePlayservices();

        imageViewSearch = findViewById(R.id.imageViewSearchButton);
        inputLocation = findViewById(R.id.inputLocation);


        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);

        //Logout button
        //TODO: The app crashes upon reentering the main activity. User is successfully logged out. Very likley due to gps function or android play services resulting in this. To fix if time allows
        
        mlogout = (Button)findViewById(R.id.logout);
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                //Then go back to main act
                Intent intent = new Intent(MapActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        //Searchbutton
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = inputLocation.getText().toString();
                if (location == null) {
                    Toast.makeText(MapActivity.this, "Type End Point", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                    try {
                        List<Address> listAddress = geocoder.getFromLocationName(location, 1);
                        if (listAddress.size() > 0) {
                            LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
                            //^will be the user's current position

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.title("End point");
                            markerOptions.position(latLng);
                            googleMap.addMarker(markerOptions);

                            //Camera update
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17f);
                            googleMap.animateCamera(cameraUpdate);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private boolean checkGooglePlayservices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(MapActivity.this, "User Cancelled Dialogue", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
        return false;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

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
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                CheckGps();
                return true;
            }
        });





        /*
        //Adding zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Adding compass
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        */


    }

    private void CheckGps() {
        locationRequest = com.google.android.gms.location.LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addAllLocationRequests(Collections.singleton(locationRequest))
                .setAlwaysShow(true);

        Task<LocationSettingsResponse> locationSettingsResponseTask = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        locationSettingsResponseTask.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse request = task.getResult(ApiException.class);
                    GetCurrentLocationUpdate();

                    //To get the location from the device

                } catch (ApiException e) {
                    if (e.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        try {
                            resolvableApiException.startResolutionForResult(MapActivity.this, 101);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }

                    }
                    if (e.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                        Toast.makeText(MapActivity.this, "Settings not available", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }

    private void GetCurrentLocationUpdate() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Toast.makeText(MapActivity.this,"Location "+locationResult.getLastLocation().getLatitude()+": "+locationResult.getLastLocation().getLongitude(),Toast.LENGTH_SHORT).show();
                LatLng newlatlng= new LatLng(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude());

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(newlatlng, 17f);
                googleMap.animateCamera(cameraUpdate);

                //storing the location to geofire
                String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();//gets id of currently logged in user
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("UsersLocation");

                GeoFire geoFire = new GeoFire(ref);
                geoFire.setLocation(userid,new GeoLocation(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude()));

            }
        }, Looper.getMainLooper());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101){
            if(resultCode==RESULT_OK){
                Toast.makeText(MapActivity.this,"Now GPS is enabled",Toast.LENGTH_SHORT).show();
            }if(resultCode==RESULT_CANCELED){
                Toast.makeText(MapActivity.this,"Denied GPS",Toast.LENGTH_SHORT).show();


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.noneMap){
            googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
        if(item.getItemId() == R.id.NormalMap){
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //This is for when the user exits the activity, you remove the user from the database
        //storing the location to geofire
        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();//gets id of currently logged in user
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("UsersLocation");

        GeoFire geoFire = new GeoFire(ref);
        geoFire.removeLocation(userid);

    }
}