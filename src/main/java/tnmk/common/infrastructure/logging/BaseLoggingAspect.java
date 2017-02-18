package tnmk.common.infrastructure.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import tnmk.common.util.LogUtil;

import java.time.Instant;

/**
 * Usage:
 * In your application, create a new class which use annotations {@link org.aspectj.lang.annotation.Aspect} and {@link org.springframework.stereotype.Component}.
 * Inside that class, declare your pointcut and reuse the public methods of this class (e.g. {@link #around(ProceedingJoinPoint)}).
 *
 * @author khoi.tran on 2/9/17.
 */
public class BaseLoggingAspect {
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodInvoker = String.format("%s.%s(..)", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        Instant startTime = Instant.now();
        try {
            Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            LogUtil.logRuntime(startTime, methodInvoker);
            return result;
        } catch (Throwable ex) {
            LogUtil.logRuntime(startTime, methodInvoker + ": error " + ex.getMessage());
            throw ex;
        }
    }
}
