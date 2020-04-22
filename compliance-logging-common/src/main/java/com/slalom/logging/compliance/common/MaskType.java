package com.slalom.logging.compliance.common;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class MaskType {
    public static String JSON_MARKER_NAME = "JSON-COMPLIANCE";
    public static String LOMBOK_MARKER_NAME = "LOMBOK-COMPLIANCE";
    public static Marker JSON = MarkerFactory.getMarker(JSON_MARKER_NAME);
    public static Marker LOMBOK = MarkerFactory.getMarker(LOMBOK_MARKER_NAME);
}
