package com.slalom.logging.compliance.log4j2.mask;

import static com.slalom.logging.compliance.common.MaskType.JSON_MARKER_NAME;
import static com.slalom.logging.compliance.common.MaskType.LOMBOK_MARKER_NAME;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaskType {
  public static Marker JSON = MarkerManager.getMarker(JSON_MARKER_NAME);
  public static Marker LOMBOK = MarkerManager.getMarker(LOMBOK_MARKER_NAME);
}
