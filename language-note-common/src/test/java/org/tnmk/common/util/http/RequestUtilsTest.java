package org.tnmk.common.util.http;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.tnmk.common.utils.http.RequestUtils;
import org.tnmk.common.utils.http.RestTemplateHelper;

import javax.servlet.http.Cookie;

/**
 * @author khoi.tran on 6/5/17.
 */
public class RequestUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestUtilsTest.class);

    @Test
    public void getCookies() {
        MockHttpServletRequest httpServletRequest = mockHttpServletRequest();
        httpServletRequest.setCookies(new Cookie("akey","avalue"),new Cookie("bkey","bvalue"));

        //Assert
        Assert.assertEquals("avalue",RequestUtils.getCookieValue(httpServletRequest, "akey"));
        Assert.assertEquals("bvalue",RequestUtils.getCookieValue(httpServletRequest, "bkey"));

        Assert.assertEquals("avalue",RequestUtils.getCookie(httpServletRequest, "akey").getValue());
        Assert.assertEquals("bvalue",RequestUtils.getCookie(httpServletRequest, "bkey").getValue());
    }
    @Test
    public void getCookies_null() {
        MockHttpServletRequest httpServletRequest = mockHttpServletRequest();
        Assert.assertNull(RequestUtils.getCookie(httpServletRequest, "xxx"));
    }
    private MockHttpServletRequest mockHttpServletRequest(){
        return new MockHttpServletRequest("post","to-some-domain");
    }
}
