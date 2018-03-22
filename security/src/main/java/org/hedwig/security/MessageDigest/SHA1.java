package org.hedwig.security.MessageDigest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

public class SHA1 extends MessageDigestSupport {

	public static String getSHA1(String str, Charset encoding)
			throws UnsupportedEncodingException {
		String md5Str = null;
		
		try {
			md5Str = getMessageDigest(str, "SHA1", encoding);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return md5Str;
	}
}
