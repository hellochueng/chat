package org.lzz.chat.aop;

import org.lzz.chat.aop.annotation.CacheRemove;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 功能描述:清除缓存切面类
 * 赖章洲
 */
@Component
@Aspect
public class CacheRemoveAspect {
 
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    protected RedisTemplate redisTemplate;
 
    //截获标有@CacheRemove的方法
    @Pointcut(value = "(execution(* *.*(..)) && @annotation(org.lzz.chat.aop.annotation.CacheRemove))")
    private void pointcut() {
    }

 
    /**
     * 功能描述: 切面在截获方法返回值之后
     *
     * @return void
     * @date 2018/9/14 16:55
     * @params [joinPoint]
     */
    @AfterReturning(value = "pointcut()")
    private void process(JoinPoint joinPoint) {

        //获取切入方法的数据
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入方法
        Method method = signature.getMethod();
        CacheRemove cacheRemove = null;
        Object[] arguments = joinPoint.getArgs();
        String[] paramNames = {};

        Method realMethod = null;
        try {
            //获取真实对象的注解  spring会分配cglib代理对象获取不到真实注解
            realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(),method.getParameterTypes());
            cacheRemove = realMethod.getAnnotation(CacheRemove.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        paramNames = getParamterNames(realMethod);
        if (cacheRemove != null) {

            //需要移除的正则key
            String keys = cacheRemove.key();
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(keys);
            EvaluationContext context = new StandardEvaluationContext();
            for(int i=0;i<arguments.length;i++){
                context.setVariable(paramNames[i],arguments[i]);
            }

            //指定清除的key的缓存
            cleanRedisCache("*" + expression.getValue(context,String.class) + "*");
        }
    }


    public String[] getParamterNames(Method method){
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        return  u.getParameterNames(method);
    }


    private void cleanRedisCache(String key) {
        if (key != null) {
            Set<String> stringSet = redisTemplate.keys(key);
            redisTemplate.delete(stringSet);//删除缓存
            logger.info("清除 " + key + " 缓存");
        }
    }
}