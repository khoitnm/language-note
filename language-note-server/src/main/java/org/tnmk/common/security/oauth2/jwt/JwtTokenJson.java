package org.tnmk.common.security.oauth2.jwt;

/**
 * When parse an jwt access token (pattern "XXX.YYY.ZZZ") into an object, that object usually includes:
 * 1) header (which is parsed from "XXX")
 * 2) body (which is parsed from "YYY")
 * 3) certificate key information (which is parsed from "ZZZ")
 */
public class JwtTokenJson {
    /**
     * The encoded json of jwt header token.
     */
    private String jwtHeader;
    /**
     * The encoded json of jwt body token
     */
    private String jwtBody;

    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public String getJwtBody() {
        return jwtBody;
    }

    public void setJwtBody(String jwtBody) {
        this.jwtBody = jwtBody;
    }
}
