package org.tnmk.ln.infrastructure.security.resourceserver.error;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import javax.servlet.http.HttpServletRequest;

public class CustomOAuth2ExceptionTranslator {
    public ResponseEntity<Result> translate(HttpServletRequest request, ResponseEntity<OAuth2Exception> oAuth2ExceptionResponseEntity) {
        Result result = translateToResult( request, oAuth2ExceptionResponseEntity);
        ResponseEntity responseEntity = new ResponseEntity<>(result, oAuth2ExceptionResponseEntity.getHeaders(), oAuth2ExceptionResponseEntity.getStatusCode());
        return responseEntity;
    }

    private Result translateToResult(HttpServletRequest request, ResponseEntity<OAuth2Exception> oAuth2ExceptionResponseEntity) {
        OAuth2Exception oAuth2Exception = oAuth2ExceptionResponseEntity.getBody();
        Result result  = new Result();
        result.setRequestPath(request.getRequestURI());
        result.setCode(oAuth2Exception.getOAuth2ErrorCode());
        result.setMessage(oAuth2Exception.getMessage());
        result.setDetails(oAuth2ExceptionResponseEntity.getHeaders());
        return result;
    }
}
