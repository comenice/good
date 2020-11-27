package com.ku.bobo.middle.config.mp;

import com.ku.bobo.common.base.BaseKV;

import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2020/11/24 16:54
 * @Created by xb
 * 属性填充默认值
 * default value
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropDV {

   String[] name() ;

   String[] value() ;

   Class[] type() default String.class  ;


}
