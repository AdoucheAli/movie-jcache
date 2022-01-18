package fr.adouche.movie.interceptor;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Timing
public class TimingInterceptor {

    private Logger logger = Logger.getLogger(TimingInterceptor.class.getName());

    @AroundInvoke
    public Object aroundInvoke(InvocationContext ctx) throws Exception {

        String clazz = ctx.getMethod().getDeclaringClass().getName();
        String method = ctx.getMethod().getName();
        logger.entering(clazz, method, ctx.getParameters());

        final long start = System.currentTimeMillis();

        Object result = ctx.proceed();

        final long end = System.currentTimeMillis();

        final long timeTaken = end - start;

        logger.exiting(clazz, method);
        logger.info(() -> String.format("%s completed in %dms", method, timeTaken));

        return result;
    }

}
