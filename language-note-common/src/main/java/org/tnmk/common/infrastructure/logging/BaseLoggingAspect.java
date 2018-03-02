package org.tnmk.common.infrastructure.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.tnmk.common.utils.log.LogUtils;
import org.tnmk.common.utils.ToStringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Usage:
 * In your application, create a new class which use annotations {@link org.aspectj.lang.annotation.Aspect} and {@link org.springframework.stereotype.Component}.
 * Inside that class, declare your pointcut and reuse the public methods of this class (e.g. {@link #aroundRuntime(ProceedingJoinPoint)}).
 *
 * @author khoi.tran on 2/9/17.
 */
public class BaseLoggingAspect {
    /**
     * This method will write log with runtime information.
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    public Object aroundRuntime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodInvoker = String.format("%s.%s(..)", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        return around(methodInvoker, proceedingJoinPoint);
    }

    /**
     * This method will write log with input (parameters values) information, and also the running time.
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    public Object aroundInput(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        List<String> params = new ArrayList<>();
        Object[] args = proceedingJoinPoint.getArgs();
        int i = 0;
        for (Object arg : args) {
            params.add("param[" + i + "] " + ToStringUtils.toSimpleString(arg));
            i++;
        }
        String methodParameters = params.stream().collect(Collectors.joining("\n"));
        String methodInvoker = String.format("%s.%s()\nParams: \n%s",
                proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName(),
                methodParameters
        );
        return around(methodInvoker, proceedingJoinPoint);
    }

    protected Object around(String methodInvoker, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Instant startTime = Instant.now();
        try {
            Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            LogUtils.logRuntime(startTime, methodInvoker);
            return result;
        } catch (Throwable ex) {
            //Just catch for calculating runtime.
            LogUtils.logRuntime(startTime, methodInvoker + "\nError: " + ex.getMessage());
            throw ex;
        }
    }

}
