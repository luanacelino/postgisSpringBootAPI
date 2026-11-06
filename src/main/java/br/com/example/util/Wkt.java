package br.com.example.util;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;


public final class Wkt {

    private Wkt() {
    }

    public static Geometry geometria(Object wkt) {
        if (wkt == null) {
            return null;
        }
        try {
            return new WKTReader().read((String) wkt);
        } catch (ParseException e) {
            throw new IllegalStateException("WKT inválido: " + e.getMessage(), e);
        }
    }
}
