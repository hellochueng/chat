package org.lzz.chat.aop.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:需要清除的当前类型--当前类
 *
 * @author 赖章洲
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemove {
 
    /**
     * 需要清除的大类 例如 autocms 所有缓存
     *
     * @return
     */
    @AliasFor("value")
    String value() default "";
 
 
    /**
     * 需要清除的具体的额类型
     *
     * @return
     */
    @AliasFor("key")
    String key() default "";
}