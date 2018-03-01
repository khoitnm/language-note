package org.tnmk.common.util.http;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.tnmk.common.utils.http.LogRequestUtils;

import java.time.Instant;

/**
 * @author khoi.tran on 6/5/17.
 */
public class LogRequestUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogRequestUtilsTest.class);

    @Test
    public void logRequestStarting() {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest("post","to-some-domain");
        //Add some headers
        httpServletRequest.setContentType("application/json");
        httpServletRequest.setCharacterEncoding("utf-8");
        httpServletRequest.setAuthType("bearer");

        Instant startTimeOfLogging = LogRequestUtils.logRequestStarting(httpServletRequest);
        Assert.assertNotNull(startTimeOfLogging);
    }
}
