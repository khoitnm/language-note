package org.tnmk.common.util.oauth2.jwt;

import org.springframework.security.crypto.codec.Base64;
import org.tnmk.common.exception.UnexpectedException;

import java.io.UnsupportedEncodingException;

public class JwtTokenUtils {

    /**
     * @param jwtTokenValue The accessToken string. For example:
     * <code>
     * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyX2FwcGxpY2F0aW9uIl0sImFjY2Vzc1Rva2VuVXNlckRldGFpbHMiOnsidXNlcklkIjowLCJ1c2VybmFtZSI6InN1cGVydXNlciIsImNyZWF0ZWREYXRlVGltZSI6MTUxODEyMTI0NDA2MiwidXBkYXRlZERhdGVUaW1lIjoxNTE4MTQ1OTA2NDY0fSwidXNlcl9uYW1lIjoic3VwZXJ1c2VyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTUxODc1MzA3MSwiYXV0aG9yaXRpZXMiOlsiQURNSU4iXSwianRpIjoiYmEyZmM2OTYtZWJiNi00OWNhLWFkYTAtYzE3MmFhN2VlYjIzIiwiY2xpZW50X2lkIjoidHJ1c3RlZC1hcHAifQ.TeZ9CIsaaqgfgxjt7IjAgBEnd9GReJaZvrmX3kSIVXc
     * </code>
     * @return
     */
    public static JwtTokenJson decoded(String jwtTokenValue) {
        JwtTokenJson jwtTokenJson = new JwtTokenJson();
        String[] split = jwtTokenValue.split("\\.");
        String tokenHeader = getJson(split[0]);
        String tokenBody = getJson(split[1])+"}";
        jwtTokenJson.setJwtBody(tokenBody);
        jwtTokenJson.setJwtHeader(tokenHeader);
        return jwtTokenJson;
    }

    private static String getJson(String strEncoded) {
        byte[] decodedBytes = Base64.decode(strEncoded.getBytes());
        try {
            return new String(decodedBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }
}