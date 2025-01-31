package com.bca.cancercure.ui.places;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.RankBy;
import static com.bca.cancercure.ui.places.Constants.API_KEY;


import java.io.IOException;

public class NearbySearch {

    public PlacesSearchResponse run(Double lat, Double lon){
        PlacesSearchResponse request = new PlacesSearchResponse();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        LatLng location = new LatLng(lat, lon);
       // LatLng location = new LatLng(-33.8670522, 151.1957362);

        try {
            request = PlacesApi.nearbySearchQuery(context, location)
                    .radius(50000)
                    .rankby(RankBy.PROMINENCE)
                    .keyword("hospital")
                    .language("en")
                    .type(PlaceType.HOSPITAL)
                    .await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            //System.out.println(request.toString());
            System.out.println(request.results);
            return request;
        }
    }
}