package org.tnmk.common.utils.http;

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
public final class LogRequestUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogRequestUtils.class);

    private LogRequestUtils(){
        //Utils
    }

    public static Instant logRequestStarting(HttpServletRequest request) {
        Instant startTime = Instant.now();
        StringBuilder sb = new StringBuilder("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
        sb.append("\n").append(toStringRequestURL(request));
        sb.append("\n\tHeaders: ").append(toStringRequestHeaders(request));
        sb.append("\n\tParams: ").append(toStringRequestParams(request));
        sb.append("\n\tStart time: ").append(startTime);
        //TODO should change to debug
        LOGGER.trace(sb.toString());
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

}
