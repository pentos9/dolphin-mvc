package com.spacex.dolphin.util;

import com.google.common.collect.Maps;
import com.spacex.dolphin.annotation.Component;
import com.spacex.dolphin.annotation.Controller;
import com.spacex.dolphin.annotation.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class BeanFactoryUtil {
    public static Map<String, Object> doInstance(List<String> classNameList) {
        if (CollectionUtils.isEmpty(classNameList)) {
            return Maps.newHashMap();
        }

        Map<String, Object> instanceMap = Maps.newConcurrentMap();

        for (String className : classNameList) {
            try {
                Class clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    String lowerClassName = StringUtil.lowerFirstChar(clazz.getSimpleName());
                    instanceMap.put(lowerClassName, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = (Service) clazz.getAnnotation(Service.class);
                    String value = service.value();
                    if (StringUtils.isNotBlank(value)) {
                        instanceMap.put(value.trim(), clazz.newInstance());
                    } else {
                        Class[] inters = clazz.getInterfaces();
                        for (Class c : inters) {
                            instanceMap.put(StringUtil.lowerFirstChar(c.getSimpleName()), clazz.newInstance());
                            break;
                        }
                    }
                } else if (clazz.isAnnotationPresent(Component.class)) {
                    String lowerClassName = StringUtil.lowerFirstChar(clazz.getSimpleName());
                    instanceMap.put(lowerClassName, clazz.newInstance());
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return instanceMap;
    }
}
