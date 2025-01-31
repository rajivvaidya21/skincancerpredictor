package com.bca.cancercure.ui.places;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.bca.cancercure.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.model.PlacesSearchResult;
import static com.bca.cancercure.ui.places.Constants.API_KEY;

import java.util.Arrays;
import java.util.List;


public class NearbyPlaces extends Fragment implements OnMapReadyCallback {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mgoogMap;
    boolean gpsEnbl = false;
    boolean netwEnbl = false;
    LocationManager locManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        checkLocationPermission();
      //  checkGps();
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //Toast.makeText(getActivity(), location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        try {
            gpsEnbl = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            netwEnbl = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Places.initialize(getContext(), API_KEY);
        PlacesClient placesClient = Places.createClient(getActivity());

        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        //FetchPlaceRequest detailsRequest = FetchPlaceRequest.builder(placeId, fields).build();

        FindCurrentPlaceRequest currentPlaceRequest = FindCurrentPlaceRequest.builder(fields).build();



//        NearbyPlaces.getMapAsync(this);

        /*System.out.println(mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude());
        System.out.println(mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());*/
       /* mLocationManager = (LocationManager)  getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);*/

      /*  nearbyPlacesModel =
                ViewModelProviders.of(this).get(NearbyPlacesModel.class);*/
        View root = inflater.inflate(R.layout.fragment_places, container, false);


            if( locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                        .findFragmentById(R.id.map2);
                mapFragment.getMapAsync(this);

            }
        else{
                checkGps();
            }
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mgoogMap.setMyLocationEnabled(true);



        PlacesSearchResult[] placesSearchResults = new NearbySearch().run(locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude(),
                locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude()).results;

       /* Log.e("response1Tag", placesSearchResults[0].toString());
        Log.e("response2Tag", placesSearchResults[1].toString());*/

        double lat1 = placesSearchResults[0].geometry.location.lat;
        double lng1 = placesSearchResults[0].geometry.location.lng;

        double lat2 = placesSearchResults[1].geometry.location.lat;
        double lng2 = placesSearchResults[1].geometry.location.lng;

        mgoogMap.addMarker(new MarkerOptions().position(new LatLng(lat1, lng1)));
        mgoogMap.addMarker(new MarkerOptions().position(new LatLng(lat2, lng2)));

        mgoogMap.setMinZoomPreference(14.0f);
        mgoogMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat1, lng1)));
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Allow Location Access")
                        .setMessage("Enable Location Access to see places")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                    }

                } else {
                }
                return;
            }

        }
    }

    public void checkGps(){
        if (!gpsEnbl && !netwEnbl) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Enable GPS")
                    .setPositiveButton("Open Setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }
}