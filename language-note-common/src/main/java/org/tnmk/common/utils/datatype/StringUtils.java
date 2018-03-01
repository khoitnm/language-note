package org.tnmk.common.utils.datatype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 8/2/16.
 */
public final class StringUtils {
    private StringUtils(){
        //Utils
    }
    /**
     * This is a convenient method to convert any object into String (usually used in reflection).
     *
     * @param obj
     * @return if the obj is null, return null.
     */
    public static String toString(Object obj) {
        String result = null;
        if (obj != null) {
            if (obj instanceof String) {
                result = (String) obj;
            } else {
                result = String.valueOf(obj);
            }
        }
        return result;
    }

    public static List<String> toStrings(List<?> list) {
        List<String> result = new ArrayList<>();
        for (Object obj : list) {
            result.add(obj.toString());
        }
        return result;
    }

    public static String joinString(Object... strs) {
        StringBuilder sb = new StringBuilder();
        for (Object string : strs) {
            sb.append(string);
        }
        return sb.toString();
    }

    public static String joinStringWithDelimiter(String delimiter, Object... objects) {
        if (org.apache.commons.lang3.StringUtils.isBlank(delimiter)) {
            return joinString(objects);
        }
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(object);
        }
        return sb.toString();
    }

    public static String joinNotBlankStrings(String delimiter, String... strs) {
        return joinNotBlankStringsWithElementWrapper(delimiter, "", "", strs);
    }

    public static String joinNotBlankStringsWithElementWrapper(String delimiter, String prefix, String suffix, String... strs) {
        if (strs == null) {
            return null;
        }
        if (strs.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
                if (sb.length() > 0) {
                    sb.append(delimiter);
                }
                sb.append(prefix).append(str).append(suffix);
            }
        }
        return sb.toString();
    }

    /**
     * Applies the specified mask to the card number.
     *
     * @param string The string in plain format
     * @return The masked card number
     */
    public static String maskString(String string, int numShownPrefixChars, int numShownSuffixChars) {
        if (string == null) {
            return string;
        }
        StringBuilder maskedString = new StringBuilder();
        int length = string.length();
        int lastMaskedIndex = length - numShownSuffixChars;
        for (int i = 0; i < string.length(); i++) {
            if (i < numShownPrefixChars || i >= lastMaskedIndex) {
                maskedString.append(string.charAt(i));
            } else {
                maskedString.append("*");
            }
        }
        return maskedString.toString();
    }

    /**
     * This method is usually used in writing log.
     * We usually dont' want to write email information into logs files (because of security). So this method will help you to put some mask characters.
     *
     * @param email The original email, e.g. "myemail@gmail.com"
     * @return masked email, e.g. "mye********om"
     */
    public static String maskEmail(String email) {
        return maskString(email, 3, 2);
    }

    /**
     * @param text for example: "  Now,   only Trần understand  this cliché!!! "
     * @return an array which includes words ["Now","only", "Trần", "understand", "this", "cliché"]
     */
    public static String[] toWords(String text) {
        String trimmed = text.trim();
        return trimmed.split("\\P{L}+");//Don't use "\\W+", it doesn't work with Unicode characters.
    }
}
