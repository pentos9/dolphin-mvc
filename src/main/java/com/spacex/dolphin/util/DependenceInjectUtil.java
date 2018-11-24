package com.spacex.dolphin.util;

import com.spacex.dolphin.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class DependenceInjectUtil {
    public static void doInject(Map<String, Object> beanMapFactory) {
        if (beanMapFactory == null) {
            return;
        }

        Map<String, Object> beanInstanceMap = beanMapFactory;

        beanInstanceMap.forEach((beanName, beanClazz) -> {
            Field[] fields = beanClazz.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }

                String realBeanName = null;
                Autowired autowired = field.getAnnotation(Autowired.class);
                if (StringUtils.isBlank(autowired.value())) {
                    realBeanName = DolphinStringUtil.lowerFirstChar(field.getType().getSimpleName());
                } else {
                    realBeanName = autowired.value();
                }

                field.setAccessible(true);

                if (beanInstanceMap.get(realBeanName) != null) {
                    try {
                        field.set(beanClazz, beanInstanceMap.get(realBeanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
