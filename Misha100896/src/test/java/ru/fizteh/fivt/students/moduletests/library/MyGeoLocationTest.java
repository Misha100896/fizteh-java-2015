package ru.fizteh.fivt.students.moduletests.library;

import com.google.maps.model.LatLng;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Math.abs;

@RunWith(DataProviderRunner.class)
public class MyGeoLocationTest extends TestCase {

    private static final double EPS = 1e-9;

    MyGeoLocation myGeoLocation = new MyGeoLocation();

    @DataProvider
    public static Object[][] locationLatLng() {
        return new Object[][] {
                {
                        "Rybinsk",
                        new LatLng(58.001714, 38.6495059),
                        new LatLng(58.1202071, 38.974287),
                        23.207723459954384
                }
        };
    }

    @Test
    @UseDataProvider("locationLatLng")
    public void testLocation(String city, LatLng p1, LatLng p2, double radius) {
        myGeoLocation.findPlace(city);

        assert((myGeoLocation.getBounds().southwest.lat - p1.lat)
                * (myGeoLocation.getBounds().southwest.lat - p1.lat)
                + (myGeoLocation.getBounds().southwest.lng - p1.lng)
                * (myGeoLocation.getBounds().southwest.lng - p1.lng) < EPS);

        assert((myGeoLocation.getBounds().northeast.lat - p2.lat)
                * (myGeoLocation.getBounds().northeast.lat - p2.lat)
                + (myGeoLocation.getBounds().northeast.lng - p2.lng)
                * (myGeoLocation.getBounds().northeast.lng - p2.lng) < EPS);
        
        assert(abs(myGeoLocation.getRadius() - radius) < EPS);
    }
}