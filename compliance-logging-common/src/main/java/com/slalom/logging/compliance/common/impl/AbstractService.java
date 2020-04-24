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
package com.slalom.logging.compliance.common.impl;

import com.slalom.logging.compliance.common.MaskService;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractService implements MaskService {

  protected static final String DEFAULT_MASK = "***********";

  protected final String fieldRegex;

  protected AbstractService(final List<String> fields) {
    fieldRegex = String.join("|", fields);
  }

  protected String maskMessage(
      final String message, final Pattern pattern, final String replacementRegex) {
    try {
      final StringBuffer buffer = new StringBuffer();
      final Matcher matcher = pattern.matcher(message);
      while (matcher.find()) {
        matcher.appendReplacement(buffer, replacementRegex);
      }
      matcher.appendTail(buffer);
      return buffer.toString();
    } catch (Exception e) {
      return message;
    }
  }
}
