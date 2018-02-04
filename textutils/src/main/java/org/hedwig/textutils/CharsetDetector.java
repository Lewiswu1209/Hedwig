/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.hedwig.textutils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharsetDetector {

    //��Nutch�������ҳ���������
    private static final int CHUNK_SIZE = 2000;

    private static Pattern metaPattern = Pattern.compile(
            "<meta\\s+([^>]*http-equiv=(\"|')?content-type(\"|')?[^>]*)>",
            Pattern.CASE_INSENSITIVE);
    private static Pattern charsetPattern = Pattern.compile(
            "charset=\\s*([a-z][_\\-0-9a-z]*)", Pattern.CASE_INSENSITIVE);
    private static Pattern charsetPatternHTML5 = Pattern.compile(
            "<meta\\s+charset\\s*=\\s*[\"']?([a-z][_\\-0-9a-z]*)[^>]*>",
            Pattern.CASE_INSENSITIVE);

    //��Nutch�������ҳ���������
    public static String guessEncoding(byte[] content) {
        int length = Math.min(content.length, CHUNK_SIZE);

        String str = "";
        try {
            str = new String(content, "ascii");
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        Matcher metaMatcher = metaPattern.matcher(str);
        String encoding = null;
        if (metaMatcher.find()) {
            Matcher charsetMatcher = charsetPattern.matcher(metaMatcher.group(1));
            if (charsetMatcher.find()) {
                encoding = new String(charsetMatcher.group(1));
            }
        }
        if (encoding == null) {
            metaMatcher = charsetPatternHTML5.matcher(str);
            if (metaMatcher.find()) {
                encoding = new String(metaMatcher.group(1));
            }
        }
        if (encoding == null) {
            if (length >= 3 && content[0] == (byte) 0xEF
                    && content[1] == (byte) 0xBB && content[2] == (byte) 0xBF) {
                encoding = "UTF-8";
            } else if (length >= 2) {
                if (content[0] == (byte) 0xFF && content[1] == (byte) 0xFE) {
                    encoding = "UTF-16LE";
                } else if (content[0] == (byte) 0xFE
                        && content[1] == (byte) 0xFF) {
                    encoding = "UTF-16BE";
                }
            }
        }

        return encoding;
    }

}
