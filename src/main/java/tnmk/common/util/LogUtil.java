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
//        Enumeration<String> names = request.getParameterNames();
//        List<String> paramStrings = new ArrayList<>();
//        while (names.hasMoreElements()) {
//            String paramName = names.nextElement();
//            String paramValue = request.getParameterMap(paramName);
//            String paramString = String.format("{%s:%s}", paramName, paramValue);
//            paramStrings.add(paramString);
//        }
//        String result = paramStrings.stream().collect(Collectors.joining("\n,"));
//        return result;
    }

    public static String toStringRequestURL(HttpServletRequest request) {
        return String.format("%s %s", request.getMethod(), request.getRequestURL());
    }

}
