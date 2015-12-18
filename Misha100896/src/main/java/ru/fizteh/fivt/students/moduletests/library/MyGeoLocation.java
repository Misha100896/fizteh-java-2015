package ru.fizteh.fivt.students.moduletests.library;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.Bounds;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.io.*;
import java.util.Scanner;
import static java.lang.Math.*;

public class MyGeoLocation {

    private static final double RADIUS_OF_EARTH = 6371;
    private static final double DEGREES_IN_SEMICIRCLE = 180;

    private GeocodingResult geocodingResults;
    private double radius;

    public void findPlace(String place) {

        String apiKey = null;

        try {
            File keyFile = new File("googlemaps.properties");
            apiKey = new Scanner(keyFile).useDelimiter("\\Z").next();
        } catch (FileNotFoundException exception) {
        }

        GeoApiContext context = new GeoApiContext().setApiKey(apiKey);

        try {
            geocodingResults = GeocodingApi.geocode(context, place).await()[0];
        } catch (Exception exception) {
        }

        radius = calculateRadius();

    }

    private double calculateRadius() {
        LatLng point1 = geocodingResults.geometry.bounds.northeast;
        LatLng point2 = geocodingResults.geometry.bounds.southwest;
        double rad = DEGREES_IN_SEMICIRCLE / PI;

        double x1 = point1.lat / rad;
        double y1 = point1.lng / rad;
        double x2 = point2.lat / rad;
        double y2 = point2.lng / rad;

        double t1 = cos(x1) * cos(y1) * cos(x2) * cos(y2);
        double t2 = cos(x1) * sin(y1) * cos(x2) * sin(y2);
        double t3 = sin(x1) * sin(x2);

        return RADIUS_OF_EARTH * acos(t1 + t2 + t3);
    }

    public LatLng getLocation() {
        return geocodingResults.geometry.location;
    }

    public double getRadius() {
        return radius;
    }

    public final Bounds getBounds() {
        return geocodingResults.geometry.bounds;
    }
}