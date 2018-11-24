package com.spacex.dolphin.util;

import com.google.common.collect.Maps;
import com.spacex.dolphin.annotation.Controller;
import com.spacex.dolphin.annotation.RequestMapping;
import com.spacex.dolphin.annotation.RequestParam;
import com.spacex.dolphin.beans.HandlerModel;
import jdk.internal.org.objectweb.asm.Type;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class HandlerMappingUtil {
    public static Map<String, HandlerModel> doHandlerMapping(Map<String, Object> beanInstanceMap) {

        Map<String, HandlerModel> handlerMappingMap = Maps.newConcurrentMap();

        if (beanInstanceMap == null || beanInstanceMap.isEmpty()) {
            return handlerMappingMap;
        }


        beanInstanceMap.forEach((beanName, beanInstance) -> {
            Class beanClass = beanInstance.getClass();

            if (!beanClass.isAnnotationPresent(Controller.class)) {
                return;
            }

            String url = "/";
            if (beanClass.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = (RequestMapping) beanClass.getAnnotation(RequestMapping.class);
                url += requestMapping.value();
            }

            Method[] methods = beanClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }

                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String realUrl = url + "/" + requestMapping.value();
                realUrl = realUrl.replaceAll("/+", "/");


                Annotation[][] annotations = method.getParameterAnnotations();
                Map<String, Integer> paramMap = Maps.newHashMap();

                String[] paramNames = ParameterNameUtil.getMethodParameterNamesByAsm(beanClass, method);
                Class[] paramTypes = method.getParameterTypes();

                for (int i = 0; i < annotations.length; i++) {
                    Annotation[] anns = annotations[i];
                    if (anns.length == 0) {
                        Class<?> paramType = paramTypes[i];
                        if (paramType == HttpServletRequest.class || paramType == HttpServletResponse.class) {
                            paramMap.put(paramType.getName(), i);
                        } else {
                            paramMap.put(paramNames[i], i);
                        }
                        continue;
                    }


                    // annotation founded
                    for (Annotation annotation : anns) {
                        if (annotation.annotationType() == RequestParam.class) {
                            String paramName = ((RequestParam) annotation).value();
                            if (StringUtils.isNotBlank(paramName.trim())) {
                                paramMap.put(paramName, i);
                            }
                        }
                    }


                }

                HandlerModel handlerModel = new HandlerModel(method, beanClass, paramMap);
                handlerMappingMap.put(realUrl, handlerModel);

            }


        });

        return handlerMappingMap;
    }

    private static boolean matchTypes(Type[] types, Class<?>[] parameterTypes) {
        if (types.length != parameterTypes.length) {
            return false;
        }
        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(parameterTypes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }
}
