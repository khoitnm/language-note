package tnmk.common.exception;

import tnmk.common.exception.constant.ExceptionConstants;

/**
 * This is a very general exception.
 * We only throw this exception only if there's something wrong and the root cause is totally out of our awareness.<br/>
 * <p>
 * <pre>
 * For example:
 * With the logic of your code, you will have a sorted list. Then you call binarySearch(sortedList).
 * But inside your binarySearch(), you find out that input list was not sorted, throw UnexpectedException.
 * </pre>
 */
public class UnexpectedException extends BaseException {

    private static final long serialVersionUID = -2947099715615663831L;
    private static final String ERROR_CODE = ExceptionConstants.General.UnexpectedError;

    public UnexpectedException(final String message) {
        super(ERROR_CODE, message);
    }

    public UnexpectedException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
