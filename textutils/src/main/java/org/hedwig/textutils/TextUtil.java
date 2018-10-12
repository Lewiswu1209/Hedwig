package org.hedwig.textutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A util to operate text.
 * @author Lewis
 * @version 2.0
 */
final public class TextUtil {

	/**
	 * Return a collection that contains the substrings that can be specifies by the given regex.
	 * @param string
	 * @param regex
	 * @return
	 */
	public static Collection<String> getSubstringsByRegex(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);

		Collection<String> subStrSet = new ArrayList<String>();

		while (matcher.find()) {
			String subStr = matcher.group();
			subStrSet.add(subStr);
		}

		return subStrSet;
	}
	
	public static String getSubstringByRegex(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);

		String subStr = null;
		if (matcher.find()) {
			subStr = matcher.group();
		}

		return subStr;
	}
	
	public static void main(String[] argv) {
		System.out.println(TextUtil.getSubstringByRegex("姓名：如斯", "(?<=姓名：).*"));
	}

	/**
	 * Returns the given string if it is non-null; the empty string otherwise.
	 *
	 * @param string
	 *            the string to test and possibly return
	 * @return {@code string} itself if it is non-null; {@code ""} if it is null
	 */
	public static String nullToEmpty(String string) {
		return (string == null) ? "" : string;
	}

	/**
	 * Returns the given string if it is nonempty; {@code null} otherwise.
	 *
	 * @param string
	 *            the string to test and possibly return
	 * @return {@code string} itself if it is nonempty; {@code null} if it is
	 *         empty or null
	 */
	public static String emptyToNull(String string) {
		return TextUtil.isblank(string) ? null : string;
	}

	/**
	* Returns {@code true} if the given string is null or is the empty string.
	*
	* <p>Consider normalizing your string references with {@link #nullToEmpty}.
	* If you do, you can use {@link String#isEmpty()} instead of this
	* method, and you won't need special null-safe forms of methods like {@link
	* String#toUpperCase} either. Or, if you'd like to normalize "in the other
	* direction," converting empty strings to {@code null}, you can use {@link
	* #emptyToNull}.
	*
	* @param string a string reference to check
	* @return {@code true} if the string is null or is the empty string
	*/
	public static boolean isblank(String string) {
		return string == null || string.length() == 0;
	}

	/**
	* Returns {@code true} if the given string is number.
	*
	* @param string a string reference to check
	* @return {@code true} if the string is number
	*/
	public static boolean isNum(String string) {
		return string != null && string.matches("^\\d*$");
	}
	
    /**
     * Returns {@code true} if the given string is date.
     * 
     * @param str
     * @return
     */
    public static boolean isDate(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(str);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

	/**
	 * Returns the longest string {@code prefix} such that
	 * {@code a.toString().startsWith(prefix) && b.toString().startsWith(prefix)}
	 * , taking care not to split surrogate pairs. If {@code a} and {@code b}
	 * have no common prefix, returns the empty string.
	 *
	 * @since 11.0
	 */
	public static String commonPrefix(CharSequence a, CharSequence b) {
		int maxPrefixLength = Math.min(a.length(), b.length());
		int p = 0;
		while (p < maxPrefixLength && a.charAt(p) == b.charAt(p)) {
			p++;
		}
		if (validSurrogatePairAt(a, p - 1) || validSurrogatePairAt(b, p - 1)) {
			p--;
		}
		return a.subSequence(0, p).toString();
	}
	
	/**
	 * Returns the longest string {@code suffix} such that
	 * {@code a.toString().endsWith(suffix) && b.toString().endsWith(suffix)},
	 * taking care not to split surrogate pairs. If {@code a} and {@code b} have
	 * no common suffix, returns the empty string.
	 *
	 * @since 11.0
	 */
	public static String commonSuffix(CharSequence a, CharSequence b) {
		int maxSuffixLength = Math.min(a.length(), b.length());
		int s = 0;
		while (s < maxSuffixLength
				&& a.charAt(a.length() - s - 1) == b.charAt(b.length() - s - 1)) {
			s++;
		}
		if (validSurrogatePairAt(a, a.length() - s - 1)
				|| validSurrogatePairAt(b, b.length() - s - 1)) {
			s--;
		}
		return a.subSequence(a.length() - s, a.length()).toString();
	}

	/**
	 * True when a valid surrogate pair starts at the given {@code index} in the
	 * given {@code string}. Out-of-range indexes return false.
	 */
	static boolean validSurrogatePairAt(CharSequence string, int index) {
		return index >= 0 && index <= (string.length() - 2)
				&& Character.isHighSurrogate(string.charAt(index))
				&& Character.isLowSurrogate(string.charAt(index + 1));
	}

	public static String byteToHexString( byte data[] ) {
		StringBuffer strBuffer = new StringBuffer();
		
		for (int i = 0; i < data.length; i++) {
			strBuffer.append(Integer.toHexString(0xff & data[i]).toUpperCase());
		}
		
		return strBuffer.toString();
	}

	public static String ArrayToString(Object obj) {
		if (obj instanceof Object[]) {
			Object[] tmp_items = (Object[])obj;
			StringBuilder sb = new StringBuilder(tmp_items[0].toString());
			for (int ii = 1; ii < tmp_items.length; ii++) {
				sb.append(", " + tmp_items[ii]);
			}
			return "{" + sb.toString() + "}";
		}
		return obj==null?"":obj.toString();
	}

	private TextUtil(){}
}
