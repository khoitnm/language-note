package org.tnmk.common.exception.util;

import org.springframework.core.NestedRuntimeException;

/**
 * @author khoi.tran on 12/8/16.
 */
public final class ExceptionUtils {
    private ExceptionUtils() {
        //Utils
    }

    /**
     * This method is usually helpful when you want to show the root cause message of DataExceptions.
     * @param exception
     * @return
     */
    public static String getExceptionRoot(Exception exception) {
        Throwable rootCause = exception;
        if (exception instanceof NestedRuntimeException) {
            NestedRuntimeException nestedRuntimeException = (NestedRuntimeException) exception;
            if (nestedRuntimeException.getRootCause() != null) {
                rootCause = nestedRuntimeException.getRootCause();
            }
        } else if (exception.getCause() != null) {
            rootCause = exception.getCause();
        }
        return rootCause.getMessage();
    }

    public static String getNullPointerExceptionRoot(Exception exception) {
        return getErrorLine(exception, 0);
    }

    public static String getErrorLine(Exception exception, int stackTraceIndex) {
        StackTraceElement first = exception.getStackTrace()[stackTraceIndex];
        String fileName = first.getFileName();
        String methodName = first.getMethodName();
        int lineNumber = first.getLineNumber();
        String errorRootCause = String.format("%s#%s():%s", fileName, methodName, lineNumber);
        return "Root: " + errorRootCause;
    }
}
