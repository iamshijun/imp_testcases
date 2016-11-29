package com.kibou.util;

public class StringUtils {
	
	//from Spring util
	/**
	 * Trim <i>all</i> whitespace from the given String:
	 * leading, trailing, and inbetween characters.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		int index = 0;
		while (sb.length() > index) {
			if (Character.isWhitespace(sb.charAt(index))) {
				sb.deleteCharAt(index);
			}
			else {
				index++;
			}
		}
		return sb.toString();
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
}
