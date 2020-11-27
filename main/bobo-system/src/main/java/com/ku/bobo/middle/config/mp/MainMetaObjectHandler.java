package com.ku.bobo.middle.config.mp;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2020/11/24 16:24
 * @Created by xb
 */
@Configuration
public class MainMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        PropDV propDV = metaObject.getOriginalObject().getClass().getAnnotation(PropDV.class);
        if ( propDV != null ){
            String[] names = propDV.name();
            String[] values = propDV.value();
            Class[] types = propDV.type();

            for (int i = 0; i < names.length; i++) {
                if ( Integer.class.equals( types[i] )  ){
                    metaObject.setValue( names[i] , Integer.valueOf( values[i] ) );
                    continue;
                }
                metaObject.setValue( names[i] , values[i] );
            }

        }
//        String[] getterNames = metaObject.getGetterNames();
//        for (String name : getterNames) {
//            System.out.println("name = " + name);
//
//            System.out.println("metaObject.getValue(name) = " + metaObject.getValue(name));
//        }
//        this.strictInsertFill(metaObject, "voteCount", Long.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
