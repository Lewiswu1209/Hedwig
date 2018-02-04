package org.hedwig.security.MessageDigest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hedwig.textutils.TextUtil;

public abstract class MessageDigestSupport {
	protected static String getMessageDigest(String message, String algorithm,Charset encoding)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		byte[] data = message.getBytes(encoding);
		
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
		messageDigest.update(data);
		byte resultData[] = messageDigest.digest();

		return TextUtil.byteToHexString(resultData);
	}
}
