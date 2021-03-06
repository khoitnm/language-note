package org.tnmk.ln.infrastructure.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.tnmk.common.infrastructure.logging.BaseLoggingAspect;

/**
 * @author khoi.tran on 2/9/17.
 */
@Aspect
@Component
public class Neo4jRepositoryLoggingAspect extends BaseLoggingAspect {

    @Around("execution(* tnmk..*.Neo4jRepository.*(..))")
    public Object aroundRuntime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.aroundInput(proceedingJoinPoint);
    }
}
