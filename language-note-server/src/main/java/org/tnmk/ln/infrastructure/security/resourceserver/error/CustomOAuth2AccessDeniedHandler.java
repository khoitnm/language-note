package org.tnmk.ln.infrastructure.security.resourceserver.error;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The source code of this class is copied from Spring framework: {@link AbstractOAuth2SecurityExceptionHandler}
 */
public class CustomOAuth2AccessDeniedHandler implements AccessDeniedHandler {
    public CustomOAuth2AccessDeniedHandler() {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        this.doHandle(request, response, authException);
    }

    //Copy code from abstract

    /** Logger available to subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();

    //My custom exception translator.
    private CustomOAuth2ExceptionTranslator customOAuth2ExceptionTranslator = new CustomOAuth2ExceptionTranslator();

    private OAuth2ExceptionRenderer exceptionRenderer = new DefaultOAuth2ExceptionRenderer();

    // This is from Spring MVC.
    private HandlerExceptionResolver handlerExceptionResolver = new DefaultHandlerExceptionResolver();

    public void setExceptionTranslator(WebResponseExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    public void setExceptionRenderer(OAuth2ExceptionRenderer exceptionRenderer) {
        this.exceptionRenderer = exceptionRenderer;
    }

    protected void doHandle(HttpServletRequest request, HttpServletResponse response, Exception authException) throws IOException, ServletException {
        try {
            //For simple, we can just throw exception here, and then use Exception Advice to handle it.
            ResponseEntity<OAuth2Exception> oAuth2ExceptionResponseEntity = exceptionTranslator.translate(authException);

            //CUSTOM CODE: Convert OAuth2Exception to our object
            ResponseEntity<Result> resultResponseEntity = customOAuth2ExceptionTranslator.translate(request,oAuth2ExceptionResponseEntity);
            exceptionRenderer.handleHttpEntityResponse(resultResponseEntity, new ServletWebRequest(request, response));


            response.flushBuffer();
        } catch (ServletException e) {
            // Re-use some of the default Spring dispatcher behaviour - the exception came from the filter chain and
            // not from an MVC handler so it won't be caught by the dispatcher (even if there is one)
            if (handlerExceptionResolver.resolveException(request, response, this, e) == null) {
                throw e;
            }
        } catch (IOException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // Wrap other Exceptions. These are not expected to happen
            throw new RuntimeException(e);
        }
    }


    public CustomOAuth2ExceptionTranslator getCustomOAuth2ExceptionTranslator() {
        return customOAuth2ExceptionTranslator;
    }

    public void setCustomOAuth2ExceptionTranslator(CustomOAuth2ExceptionTranslator customOAuth2ExceptionTranslator) {
        this.customOAuth2ExceptionTranslator = customOAuth2ExceptionTranslator;
    }
}
