package tnmk.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 8/29/16.
 */
public class LogUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);
    private static final int MAX_ELEMENTS_IN_AN_ARRAY = 7;

    public static Instant logRuntime(Instant startTime, String msg) {
        Instant now = Instant.now();
        long runTimeMilli = now.toEpochMilli() - startTime.toEpochMilli();
        double runTimeSeconds = (double) runTimeMilli / 1000;
        //TODO should change to debug
        LOGGER.info(String.format("\n%s\n\tStart time: %s, End time: %s\n\tRuntime: %s ms ~ %.2f s", msg, startTime, now, runTimeMilli, runTimeSeconds));
        return now;
    }

    public static Instant logRequestStarting(HttpServletRequest request) {
        Instant startTime = Instant.now();
        StringBuilder sb = new StringBuilder("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
        sb.append("\n").append(toStringRequestURL(request));
        sb.append("\n\tHeaders: ").append(toStringRequestHeaders(request));
        sb.append("\n\tParams: ").append(toStringRequestParams(request));
        sb.append("\n\tStart time: ").append(startTime);
        //TODO should change to debug
        LOGGER.info(sb.toString());
        return startTime;
    }

    public static String toStringRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> headerStrings = new ArrayList<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            String headerString = String.format("{%s:%s}", headerName, headerValue);
            headerStrings.add(headerString);
        }
        String result = headerStrings.stream().collect(Collectors.joining(","));
        return result;
    }

    public static String toStringRequestParams(HttpServletRequest request) {
        return request.getParameterMap().toString();
    }

    public static String toStringRequestURL(HttpServletRequest request) {
        return String.format("%s %s", request.getMethod(), request.getRequestURL());
    }

    public static String toString(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        } else if (object.getClass().isArray()) {
            return toStringOfArray((Object[]) object);
        } else {
            return String.valueOf(object);
        }
    }

    private static String toStringOfArray(Object[] arr) {
        StringBuilder result = new StringBuilder("[");
        int i = 0;
        for (Object element : arr) {
            if (result.length() > 1) {
                result.append(",");
            }
            if (i >= MAX_ELEMENTS_IN_AN_ARRAY) {
                result.append("...");
                break;
            }
            result.append(toString(element));
            i++;
        }
        result.append("]");
        return result.toString();
    }
}
