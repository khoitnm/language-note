package org.tnmk.common.util.http;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.tnmk.common.utils.http.LogRequestUtils;
import org.tnmk.common.utils.http.RestTemplateHelper;

import java.time.Instant;

/**
 * @author khoi.tran on 6/5/17.
 */
public class RequestHelperTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestHelperTest.class);

    @Test
    public void addAccessTokenHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplateHelper.addAccessTokenHeader(httpHeaders, "bearer","x.y.z");
        Assert.assertEquals("bearer x.y.z",httpHeaders.getFirst("Authorization"));

        RestTemplateHelper.addBasicAuthenticationHeader(httpHeaders, "somename","somepassword");
        LOGGER.debug(httpHeaders.getFirst("Authorization"));
        Assert.assertEquals("Basic c29tZW5hbWU6c29tZXBhc3N3b3Jk",httpHeaders.getFirst("Authorization"));
    }
}
