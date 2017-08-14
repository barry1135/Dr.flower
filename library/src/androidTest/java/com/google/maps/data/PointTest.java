package com.google.maps.data;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.Point;

import junit.framework.TestCase;

public class PointTest extends TestCase {

    Point p;

    public void testGetGeometryType() throws Exception {
        p = new Point(new LatLng(0, 50));
        assertEquals("Point", p.getGeometryType());
    }

    public void testGetGeometryObject() throws Exception {
        p = new Point(new LatLng(0, 50));
        assertEquals(new LatLng(0, 50), p.getGeometryObject());
        try {
            p = new Point(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Coordinates cannot be null", e.getMessage());
        }
    }

}
