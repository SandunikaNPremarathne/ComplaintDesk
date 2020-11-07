package com.example.complaintapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;
    String category,dateE,timeE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        if (intent.hasExtra("category")) {
            category = intent.getStringExtra("category");
            dateE=intent.getStringExtra("date");
            timeE=intent.getStringExtra("time");
            //  Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
        }

        mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI=googleMap;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
        else{
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                LatLng home=new LatLng(location.getLatitude(), location.getLongitude());
                mapAPI.addMarker(new MarkerOptions().position(home).title("Home"));
                mapAPI.moveCamera(CameraUpdateFactory.newLatLng(home));

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(GoogleMaps.this, "Not Found", Toast.LENGTH_SHORT).show();
            }
        }

        mapAPI.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapAPI.addMarker(new MarkerOptions().position(latLng).title("Selected location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

hereLocation(latLng.latitude,latLng.longitude);
            }
        });
    }
    private void hereLocation(double lat, double lon){
        String cityName="";
        String districtName="";
        String placeName="";
        Geocoder geocoder=new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try{
            addresses=geocoder.getFromLocation(lat,lon,1);
            if(addresses.size()>0){
                for(Address adr: addresses){
                    if(adr.getLocality()!=null && adr.getLocality().length()>0){
                        cityName=addresses.get(0).getLocality();
                        districtName=addresses.get(0).getSubAdminArea();
                        break;
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        Intent intent = new Intent(GoogleMaps.this, LocationActivity.class);
        intent.putExtra("city", cityName);
        intent.putExtra("district",districtName);
        intent.putExtra("category", category);
        intent.putExtra("date",dateE);
        intent.putExtra("time",timeE);
        startActivity(intent);
        finish();
    }
}
