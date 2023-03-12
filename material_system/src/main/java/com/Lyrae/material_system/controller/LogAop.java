package com.Lyrae.material_system.controller;

import com.Lyrae.material_system.domain.SystemLog;
import com.Lyrae.material_system.service.ISystemLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    private Date visitTime;
    private Class clazz;
    private Method method;
    private String ip;
    private String url;
    private String methodName;
    private String username;
    //url、methodName、username、ip、executionTime

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ISystemLogService systemLogService;

    @Before("execution(* com.Lyrae.material_system.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException{
        //访问时间
        visitTime = new Date();
        //获得访问方法:
        //1、先获得访问类
        clazz = joinPoint.getTarget().getClass();
        //2、获得所访问的方法的method
        methodName = joinPoint.getSignature().getName();//得到name根据name反射出方法
        //判断方法是否有参数
        Object[] args = joinPoint.getArgs();//获取访问方法的参数
        if (args == null || args.length == 0){
            method = clazz.getMethod(methodName);
        }else {
            Class[] params = new Class[args.length];
            for (int i = 0 ; i<args.length ; i++){
                params[i] = args[i].getClass();
            }
            method = clazz.getMethod(methodName,params);
        }
    }

    @After("execution(* com.Lyrae.material_system.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint){
        long executionTime = new Date().getTime() - visitTime.getTime();
        if (clazz != null && method != null && clazz !=LogAop.class && clazz != SystemLogController.class && methodName != "getCurrentUser" && methodName !="login"){
            //获取url
            //获取类上的@RequestMapping("/product")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            //获取方法上的"/findAll"
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            if (methodAnnotation != null) {
                String[] methodValues = methodAnnotation.value();
                if (classAnnotation != null){
                    String[] classValues = classAnnotation.value();
                    url = classValues[0] + methodValues[0];
                }else {
                    url = methodValues[0];
                }

                if (methodValues[0] != "/loginPage"){
                    //获取ip
                    ip = request.getRemoteAddr();
                    //获取username
                    SecurityContext context = SecurityContextHolder.getContext();//从上下文中获取当前登录的用户
                    User user = (User) context.getAuthentication().getPrincipal();
                    username = user.getUsername();

                    //将信息封装到SystemLog对象中
                    SystemLog systemLog = new SystemLog();
                    systemLog.setVisitTime(visitTime);
                    systemLog.setExecutionTime(executionTime);
                    systemLog.setUsername(username);
                    systemLog.setIp(ip);
                    systemLog.setUrl(url);
                    systemLog.setMethod("[类名:]" + clazz.getName() + "[方法名:]" + method.getName());

                    //保存
                    try{
                        systemLogService.save(systemLog);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
