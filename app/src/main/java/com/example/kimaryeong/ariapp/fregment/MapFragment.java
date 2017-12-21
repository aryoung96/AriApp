package com.example.kimaryeong.ariapp.fregment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import com.example.kimaryeong.ariapp.MyLocationListener;
import com.example.kimaryeong.ariapp.R;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment {

    protected double latitude, longitude, altitude;  //위도,경도,해발(고도)
    protected Button btCheck, btPlace;
    protected TextView tvLatitude, tvLongitude, tvAltitude;
    protected LocationManager locationManager;
    protected MyLocationListener myLocationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        tvLatitude = (TextView)view.findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) view.findViewById(R.id.tvLongitude);
        tvAltitude = (TextView) view.findViewById(R.id.tvAltitude);
        btCheck = (Button)view.findViewById(R.id.btCheck);
        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = myLocationListener.latitude;
                longitude = myLocationListener.longitude;
                altitude = myLocationListener.altitude;
                tvLatitude.setText(String.format("%g",latitude));
                tvLongitude.setText(String.format("%g",longitude));
                tvAltitude.setText(String.format("%g",altitude));

                String str = String.format("geo:%g,%g?z=15",latitude,longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                startActivity(intent);
            }
        });
        locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        long nMinTime = 1000;  //추적 시간 ,  milli-second
        float minDistance = 0;
        myLocationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return view;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, nMinTime, minDistance, myLocationListener);  //gps_provider : 실외위치 추적,network_provider : 실내위치 추적

        btPlace = (Button)view.findViewById(R.id.btPlace);
        btPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder;
                geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.KOREAN);
                try {       //try catch : error처리 과정, 강제로 넣어버림
                    List<Address> lsAddress;
                    lsAddress = geocoder.getFromLocation(latitude,longitude,1);  //위도,경도 정보를 넣으면 당신이 원하는 정보를 주겠다.
                    Address address = lsAddress.get(0);
                    String placeName = address.getFeatureName();
                    Toast.makeText(getActivity().getApplicationContext(),placeName,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}










