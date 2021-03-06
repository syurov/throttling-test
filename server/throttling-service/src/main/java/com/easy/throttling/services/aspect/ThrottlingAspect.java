package com.easy.throttling.services.aspect;

import com.easy.throttling.common.annotations.Throttling;
import com.easy.throttling.common.exceptions.ThrottlingOverException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Aspect
@Order(1)
public class ThrottlingAspect {

  @DeclareParents(value = "com.easy.throttling.common.interfaces.services.ThrottleAble+"
      , defaultImpl = ThrottlingMethodsMap.class)
  public static ConcurrentMap<String, LinkedList<Long>> throttlingMethods;

  @Pointcut("@annotation(com.easy.throttling.common.annotations.Throttling)")
  public void annotationThrottling() {
  }

  @Around("annotationThrottling()")
  public void throttle(ProceedingJoinPoint joinPoint) throws Throwable {

    Object aThis = joinPoint.getThis();

    ConcurrentMap<String, LinkedList<Long>> throttlingMethods = (ConcurrentMap<String, LinkedList<Long>>) aThis;
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Throttling annotation = signature.getMethod().getAnnotation(Throttling.class);

    throttlingMethods.putIfAbsent(joinPoint.getSignature().getName(), new LinkedList<>());

    long time = new Date().getTime();
    long expired = time - 60000;


    LinkedList<Long> longs = throttlingMethods.computeIfPresent(joinPoint.getSignature().getName(),
        (k, v) -> {

          // Удаляем сначал списка, как только дошли до первого не истекшего времени, то выходим из цикла
          // остальные точно не истекли

            final Iterator<Long> each = v.iterator();
            while (each.hasNext()) {
              if (each.next() < expired) {
                each.remove();
              } else
                break;
            }

          if (v.size() < annotation.value()) {
            v.add(time);
          }

          return v;
        });

    if (longs.size() >= annotation.value())
      throw new ThrottlingOverException("Limit of calls is exceeded");

    joinPoint.proceed();

  }

}
