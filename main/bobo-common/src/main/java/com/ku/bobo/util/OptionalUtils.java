package com.ku.bobo.util;

import com.ku.bobo.exception.MainException;

import java.util.List;
import java.util.Optional;

/**
 * @Date 2020/5/12 16:31
 * @Created by xb
 * java1.8提供的null值处理封装
 */
public class OptionalUtils {

    /**
     * 判断集合不为null 集合长度大于0
     * @param v
     * @param <T>
     */
    public static <T> void ofCollect(List<T> v ) {
        try {
            Optional.ofNullable(v).filter(myList -> myList.get(0) != null).get();
        }catch ( IndexOutOfBoundsException e ){
            throw new MainException( "获取不到值，或该值为空" );
        }catch (Exception e) {
            throw new MainException( "获取不到值，或该值为空" );
        }
    }

}
