package org.hedwig.security.MessageDigest;

import java.io.UnsupportedEncodingException;

/**
 * This is a util class to get the checksum of special information.
 * @author Lewis
 * @version 2.0
 */

public final class CRC32 {
	
	private CRC32(){}

	public static String getCRC32(String message,String encoding) throws UnsupportedEncodingException {

		byte[] data = message.getBytes(encoding);
		
		java.util.zip.CRC32 checksum = new java.util.zip.CRC32();
		checksum.update(data);
		long resultData = checksum.getValue();

		return Long.toHexString(resultData);
	}

}
