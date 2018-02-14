package org.tnmk.common.util.http;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public final class RequestUtils {
    private RequestUtils(){
        //Utils
    }
    public static Cookie getCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(cookieName)){
                return cookie;
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName){
        Cookie cookie = getCookie(request, cookieName);
        if (cookie == null) return null;
        return cookie.getValue();
    }
}
