package com.example.googlemapslocationapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AlertDialog;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // Marker actuel
    private Marker currentMarker;

    // Location Manager
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtenir le fragment map
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Toast.makeText(this, "Map Ready", Toast.LENGTH_SHORT).show();

        // Initialiser LocationManager
        locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Vérifier permission
        boolean permissionGranted =
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED;

        if (permissionGranted) {

            // Demander mises à jour localisation
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000, // 2 secondes
                    0,    // 0 mètres
                    new LocationListener() {

                        @Override
                        public void onLocationChanged(@NonNull Location location) {

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            Toast.makeText(
                                    getApplicationContext(),
                                    latitude + " , " + longitude,
                                    Toast.LENGTH_SHORT
                            ).show();

                            LatLng position =
                                    new LatLng(latitude, longitude);

                            // Premier marker
                            if (currentMarker == null) {

                                currentMarker = mMap.addMarker(
                                        new MarkerOptions()
                                                .position(position)
                                                .title("Ma Position")
                                );

                            } else {

                                // Déplacer marker existant
                                currentMarker.setPosition(position);
                            }

                            // Zoom sur position
                            mMap.animateCamera(
                                    CameraUpdateFactory
                                            .newLatLngZoom(position, 15f)
                            );
                        }

                        @Override
                        public void onProviderEnabled(@NonNull String provider) {

                            Toast.makeText(
                                    getApplicationContext(),
                                    "Provider activé",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                        @Override
                        public void onProviderDisabled(@NonNull String provider) {

                            buildAlertMessageNoGps();
                        }
                    });

        } else {

            // Demander permission runtime
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    200
            );
        }
    }

    // Résultat permission
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == 200) {

            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(
                        this,
                        "Permission accordée",
                        Toast.LENGTH_SHORT
                ).show();

                onMapReady(mMap);

            } else {

                Toast.makeText(
                        this,
                        "Permission refusée",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    // Boîte activation GPS
    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setMessage(
                        "Your GPS seems to be disabled, do you want to enable it?"
                )
                .setCancelable(false)

                .setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(
                                    final DialogInterface dialog,
                                    final int id
                            ) {

                                startActivity(
                                        new Intent(
                                                android.provider.Settings
                                                        .ACTION_LOCATION_SOURCE_SETTINGS
                                        )
                                );
                            }
                        })

                .setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(
                                    final DialogInterface dialog,
                                    final int id
                            ) {

                                dialog.cancel();
                            }
                        });

        final AlertDialog alert = builder.create();
        alert.show();
    }
}