package com.di.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectDemo {
    /**
     * 前置通知
     * @param joinPoint
     * 不需要参数时可以不提供
     */
    @Before(value = "execution(* com.di.service.impl.AspectServiceImpl.*(..))")
    public void before(JoinPoint joinPoint){
        System.out.println("before"+joinPoint.getSignature().getName());
    }
    /**
     * 环绕通知
     * @param proceedingJoinPoint 可用于执行切点的类
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(* com.di.service.impl.AspectServiceImpl.method1(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint ) throws Throwable{
        //getSignature 获取切点的签名    proceed() 执行切点本来的业务
        System.out.println("around获取切点的签名" + proceedingJoinPoint.getSignature().getName());
        Object object = proceedingJoinPoint.proceed();
        System.out.println("around获取切点的签名" + proceedingJoinPoint.getSignature().getName());
        return object;
    }
    /**
     * 后置通知
     * @param returnVal,切点方法执行后的返回值
     */
    @AfterReturning(value = "execution(* com.di.service.impl.AspectServiceImpl.method2(..))",returning = "returnVal")
    public void afterReturn(Object returnVal){
        System.out.println("AfterReturning and "+returnVal);
    }
    /**
     * 异常通知
     * @param throwable
     */
    @AfterThrowing(value="execution(* com.di.service.impl.AspectServiceImpl.method3(..))",throwing = "throwable")
    public void afterThrowable(Throwable throwable){
        System.out.println("AfterThrowing--出现异常:msg="+throwable.getMessage());
    }

    /**
     * 最终通知
     * 无论什么情况下都会执行的方法
     */
    @After(value = "execution(* com.di.service.impl.AspectServiceImpl.*(..))")
    public void after(){
        System.out.println("after");
    }
}
