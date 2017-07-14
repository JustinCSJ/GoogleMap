package com.applicationdev.a15017573.googlemap;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    Button btnNorth, btnCentral, btnEast;
    Spinner spn;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNorth = (Button)findViewById(R.id.btnNorth);
        btnCentral = (Button)findViewById(R.id.btnCentral);
        btnEast = (Button)findViewById(R.id.btnEast);
        spn = (Spinner)findViewById(R.id.spinner);

        FragmentManager fm = getSupportFragmentManager();
        final SupportMapFragment mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                UiSettings uiZoom = map.getUiSettings();
                uiZoom.setZoomControlsEnabled(true);

                LatLng singapore = new LatLng(1.3521, 103.8198);
                final LatLng northHQ = new LatLng(1.450294, 103.815451);
                final LatLng centralHQ = new LatLng(1.298167, 103.847398);
                final LatLng eastHQ = new LatLng(1.367471, 103.927978);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 10));

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);

                    }
                    Log.e("GMap - Permission", "GPS access has not been granted");
                }

                final Marker north = map.addMarker(new MarkerOptions()
                        .position(northHQ)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                final Marker central = map.addMarker(new MarkerOptions()
                        .position(centralHQ)
                        .title("HQ - Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                final Marker east = map.addMarker(new MarkerOptions()
                        .position(eastHQ)
                        .title("HQ - East")
                        .snippet("Block 555, Tampines Ave 3, 287788")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));



                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(marker.equals(north)){
                            Toast.makeText(getApplicationContext(), north.getTitle(), Toast.LENGTH_LONG).show();
                        } else if(marker.equals(central)){
                            Toast.makeText(getApplicationContext(), central.getTitle(), Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(getApplicationContext(), east.getTitle(), Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });


                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(northHQ, 12));
                        }

                    }
                });

                btnCentral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(centralHQ, 12));
                        }

                    }
                });

                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(eastHQ, 12));
                        }

                    }
                });

                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(spn.getSelectedItemPosition() == 0){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(northHQ, 12));
                        } else if(spn.getSelectedItemPosition() == 1){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(centralHQ, 12));
                        } else {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(eastHQ, 12));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }
        });
    }
}
