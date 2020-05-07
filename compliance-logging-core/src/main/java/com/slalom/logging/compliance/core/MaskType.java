/**
 * MIT License
 *
 * <p>Copyright (c) 2020 Slalom LLC
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.slalom.logging.compliance.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * This class contains Sl4j markers that should be used by the consuming libraries when using
 * Logback or Slf4j. When using Log4j2 the consuming libraries should use the MaskType declared in
 * compliance-logging-log4j
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaskType {
  public static String JSON_MARKER_NAME = "JSON-COMPLIANCE";
  public static String LOMBOK_MARKER_NAME = "LOMBOK-COMPLIANCE";
  public static Marker JSON = MarkerFactory.getMarker(JSON_MARKER_NAME);
  public static Marker LOMBOK = MarkerFactory.getMarker(LOMBOK_MARKER_NAME);
}
