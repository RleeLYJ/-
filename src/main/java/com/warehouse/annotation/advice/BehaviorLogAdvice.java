package com.warehouse.annotation.advice;

import com.warehouse.annotation.BehaviorLog;
import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;
import com.warehouse.pojo.Behaviorlog;
import com.warehouse.pojo.User;
import com.warehouse.service.IBehaviorlogService;
import com.warehouse.utils.TimeUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Aspect
public class BehaviorLogAdvice {

    @Autowired
    IBehaviorlogService ibs;
    @Autowired
    HttpSession session;
    Logger logger = Logger.getLogger(BehaviorLogAdvice.class);

    @Around("@annotation(behaviorLog)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint, BehaviorLog behaviorLog) throws Exception {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new Exception("cant find user in session");
        }
        //获取到被代理方法
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            String operatorname = user.getName();
            String title = behaviorLog.value();
            String time = TimeUtils.getCurrentTime();
            //获取方法的名字
            String methodname = methodSignature.getName();
            String args = "";
            Object[] preArgs = joinPoint.getArgs();
            for (int i = 0; i < preArgs.length; i++) {
                if (i!=preArgs.length-1){
                    args+=preArgs[i].toString()+",";
                }else {
                    args+=preArgs[i].toString();
                }
            }
            //获取返回值类型
            String returnType = methodSignature.getReturnType().getSimpleName();
            Object result = "";
            Behaviorlog behaviorlog = null;
            try {
                result = joinPoint.proceed();
                //记录日志
                behaviorlog = new Behaviorlog(null,"普通日志","成功",operatorname, title, time, methodname, args, returnType, null);
                return result;
            } catch (Throwable throwable) {
                behaviorlog = new Behaviorlog(null, "异常日志","失败",operatorname, title, time, methodname, args, returnType,  throwable.getMessage());
                switch (returnType) {
                    case "String":
                        return "/error/500";
                    case "DataResults":
                        return DataResults.fail(ResultCode.FAIL);
                    default:
                        return "";
                }
            } finally {
                logger.info(behaviorlog);
                ibs.save(behaviorlog);
            }
        }
        return null;
    }
}
