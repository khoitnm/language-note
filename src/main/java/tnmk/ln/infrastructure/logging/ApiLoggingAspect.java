package tnmk.ln.infrastructure.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import tnmk.common.infrastructure.logging.BaseLoggingAspect;

/**
 * @author khoi.tran on 2/9/17.
 */
@Aspect
@Component
public class ApiLoggingAspect extends BaseLoggingAspect {

    @Around("execution(* tnmk..*.*Resource.*(..))")
    public Object aroundRuntime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.aroundRuntime(proceedingJoinPoint);
    }
}
