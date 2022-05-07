package com.example.hunter_game.objects.TopTen.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hunter_game.R;
import com.example.hunter_game.objects.TopTen.MyLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMap extends Fragment {
    private GoogleMap mMap;
    private MarkerOptions markerOptions;
    private SupportMapFragment supportMapFragment;
    private LatLng latLng;

    public FragmentMap(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragmentMap_googleMaps);
        return view;
    }

    /**
     * Make marker at the coordination of the location and do zoom
     * @param myLocation
     */
    public void updateMap(MyLocation myLocation){
        latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        supportMapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            addMarker(latLng);
            zoomToMarker(latLng);
        });
    }

    public void addMarker(LatLng latLng) {
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        mMap.addMarker(markerOptions);

    }

    public void zoomToMarker(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }
}
