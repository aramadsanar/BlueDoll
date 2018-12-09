package com.armadanasar.bluedoll;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddDollLocationMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doll_location_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="http://api.myjson.com/bins/g616s";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Log.d("hasil download", response);
                            JSONObject root = new JSONObject(response);

                            JSONArray markersArray = root.getJSONArray("markers");

                            for (int i = 0; i < markersArray.length(); i++) {
                                JSONObject item = markersArray.getJSONObject(i);

                                String locationName = item.getString("name");
                                JSONObject locationDetail = item.getJSONObject("location");
                                double locationLat = locationDetail.getDouble("lat");
                                double locationLng = locationDetail.getDouble("lng");

                                LatLng locationMarker = new LatLng(locationLat, locationLng);
                                mMap.addMarker(new MarkerOptions().position(locationMarker).title(locationName));
                            }
                        }
                        catch (Exception ex) {
                            Toast.makeText(AddDollLocationMap.this, "ga bisa load json", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddDollLocationMap.this, "Request failed", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.200865, 106.783346), 15.0f));
    }
}
