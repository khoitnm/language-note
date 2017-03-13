package tnmk.common.util;

/**
 * @author khoi.tran on 3/13/17.
 */
public class RegularExpressions {
    /**
     * This regular expression can handle many complicated cases:
     * http://blog.ostermiller.org/find-comment
     */
    public static final String BLOCK_COMMENTS = "/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/";
}
