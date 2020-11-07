package com.example.complaintapp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class LocationActivity extends Activity{
    private TextView cityview;
    private TextView districtview;
    private EditText other;
    Button button,map,next,manual;
    String[] options2;
    String city,dis;
    private String category,dateE,timeE;
    Spinner spinner2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        cityview=(TextView) findViewById(R.id.city);
        districtview=(TextView) findViewById(R.id.district);
        button=(Button)findViewById(R.id.button);
        map=(Button)findViewById(R.id.btnLocation);
        next=(Button)findViewById(R.id.btnnext);
        manual=(Button)findViewById(R.id.btnmanual);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        Intent intent = getIntent();
        if (intent.hasExtra("city")) {
            category = intent.getStringExtra("category");
            dateE=intent.getStringExtra("date");
            timeE=intent.getStringExtra("time");
            city=intent.getStringExtra("city");
            dis= intent.getStringExtra("district");
            cityview.setText(city);
            districtview.setText(dis);
            setInstitute();
        }else {
            category = intent.getStringExtra("category");
            dateE=intent.getStringExtra("date");
            timeE=intent.getStringExtra("time");
            //  Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
        }

button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
        else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                hereLocation(location.getLatitude(), location.getLongitude());
                    setInstitute();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LocationActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
});

            //set spinner


            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LocationActivity.this, GoogleMaps.class);
                    intent.putExtra("category", category);
                    intent.putExtra("date",dateE);
                    intent.putExtra("time",timeE);
                    startActivity(intent);
                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String spinI=spinner2.getSelectedItem().toString();
                    if(spinI!=null) {
                        if(spinI.equals("Select Institute")){
                            spinI=other.getText().toString();
                        }
                        Intent intent = new Intent(LocationActivity.this, ComplaintActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("date", dateE);
                        intent.putExtra("time", timeE);
                        intent.putExtra("district", districtview.getText());
                        intent.putExtra("city", cityview.getText());
                        intent.putExtra("insti", spinI);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        districtview.setError("Not Found");
                        districtview.setFocusable(true);
                    }
                }
            });
            manual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LocationActivity.this, ManualActivity.class);
                    intent.putExtra("category", category);
                    intent.putExtra("date", dateE);
                    intent.putExtra("time", timeE);
                    startActivity(intent);
                    finish();
                }
            });


    }

    private void setInstitute() {

        if(districtview.getText().equals("Ampara")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Ampara1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Ampara1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Anuradhapura")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Anuradhapura1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Anuradhapura1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Badulla")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Badulla1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Badulla1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Batticaloa")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Batticaloa1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Batticaloa1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Colombo")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Colombo1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Colombo1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Galle")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Galle1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Galle1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Gampaha")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Gampaha1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Gampaha1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Hambantota")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Hambanthota1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Hambanthota1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Jaffna")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Jaffna1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Jaffna1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Kalutara")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kaluthara1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Kaluthara1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Kandy")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kandy1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Kandy1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Kegalle")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kegalle1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Kegalle1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Kilinochchi")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kilinochchi1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Kilinochchi1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Kurunegala")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Kurunegala1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Kurunegala1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Mannar")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Mannar1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Mannar1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Matara")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Matara1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Matara1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Monaragala")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Monaragala1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Monaragala1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Mullaitivu")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Mullative1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Mullative1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Nuwara_Eliya")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Nuwara_Eliya1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Nuwara_Eliya1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Polonnaruwa")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Polonnaruwa1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Polonnaruwa1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Puttalam")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Puttalam1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Puttalam1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Ratnapura")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Ratnapura1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Ratnapura1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Trincomalee")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Trincomalee1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Trincomalee1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
        }
        else if(districtview.getText().equals("Vavuniya")){
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.Vavuniya1, android.R.layout.simple_spinner_item);
            options2 = LocationActivity.this.getResources().getStringArray(R.array.Vavuniya1);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        hereLocation(location.getLatitude(), location.getLongitude());
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(LocationActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Perission Not Granted!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

        private void hereLocation(double lat, double lon){
            String cityName="";
            String districtName="";
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

            cityview.setText(cityName);
            districtview.setText(districtName);
        }


    }