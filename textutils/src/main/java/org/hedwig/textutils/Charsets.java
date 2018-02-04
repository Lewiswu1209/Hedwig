package org.hedwig.textutils;

import java.nio.charset.Charset;

/**
 * Contains constant definitions for the six standard {@link Charset} instances, which are
 * guaranteed to be supported by all Java platform implementations.
 */
public final class Charsets {

	private Charsets() {}

	/**
	 * US-ASCII: seven-bit ASCII, the Basic Latin block of the Unicode character
	 * set (ISO646-US).
	 */
	public static final Charset US_ASCII = Charset.forName("US-ASCII");

	/**
	 * ISO-8859-1: ISO Latin Alphabet Number 1 (ISO-LATIN-1).
	 */
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

	/**
	 * UTF-8: eight-bit UCS Transformation Format.
	 */
	public static final Charset UTF_8 = Charset.forName("UTF-8");

	/**
	 * UTF-16BE: sixteen-bit UCS Transformation Format, big-endian byte order.
	 */
	public static final Charset UTF_16BE = Charset.forName("UTF-16BE");

	/**
	 * UTF-16LE: sixteen-bit UCS Transformation Format, little-endian byte
	 * order.
	 */
	public static final Charset UTF_16LE = Charset.forName("UTF-16LE");

	/**
	 * UTF-16: sixteen-bit UCS Transformation Format, byte order identified by
	 * an optional byte-order mark.
	 */
	public static final Charset UTF_16 = Charset.forName("UTF-16");
	
	/**
	 * GBK: an extension of the GB2312 character set for simplified Chinese characters,
	 * used in the People's Republic of China.
	 */
	public static final Charset GBK = Charset.forName("GBK");
	
	/**
	 * GB2312 is the registered internet name for a key official character set of the People's Republic of China, used for simplified Chinese characters.
	 */
	public static final Charset GB2312 = Charset.forName("GB2312");
	
	/**
	 * GB18030 is a Chinese government standard describing the required language and character support necessary for software in China. In addition to the "GB18030 code page" this standard contains requirements about which scripts must be supported, font support, etc.
	 */
	public static final Charset GB18030 = Charset.forName("GB18030");

	/*
	 * Please do not add new Charset references to this class, unless those
	 * character encodings are part of the set required to be supported by all
	 * Java platform implementations! Any Charsets initialized here may cause
	 * unexpected delays when this class is loaded. See the Charset Javadocs for
	 * the list of built-in character encodings.
	 */
}