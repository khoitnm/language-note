package tnmk.common.exception.util;

import org.springframework.core.NestedRuntimeException;

/**
 * @author khoi.tran on 12/8/16.
 */
public class ExceptionUtil {
    public static String getDataExceptionRoot(Exception exception) {
        if (exception instanceof NestedRuntimeException) {
            NestedRuntimeException nestedRuntimeException = (NestedRuntimeException) exception;
            return nestedRuntimeException.getRootCause().getMessage();
        }
        return exception.getCause().getMessage();
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
