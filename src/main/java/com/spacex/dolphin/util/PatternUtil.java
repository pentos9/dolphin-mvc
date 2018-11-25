package com.spacex.dolphin.util;

import com.spacex.dolphin.beans.HandlerModel;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class PatternUtil {
    public static boolean pattern(Map<String, HandlerModel> handlerMappingMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        if (MapUtils.isEmpty(handlerMappingMap)) {
            return false;
        }

        String requestUri = httpServletRequest.getRequestURI();
        String contextPath = httpServletRequest.getContextPath();

        requestUri = requestUri.replace(contextPath, "").replaceAll("/+", "/");

        final String requestURI = requestUri;

        for (Map.Entry<String, HandlerModel> entry : handlerMappingMap.entrySet()) {
            String uri = entry.getKey();
            HandlerModel handlerModel = entry.getValue();
            if (StringUtils.equals(uri, requestURI)) {
                Map<String, Integer> paramIndexMap = handlerModel.getParamMap();
                Object[] paramValues = new Object[paramIndexMap.size()];
                Class<?>[] types = handlerModel.getMethod().getParameterTypes();

                paramIndexMap.forEach((paramKey, index) -> {
                    if (paramKey.equals(HttpServletRequest.class.getName())) {
                        paramValues[index] = httpServletRequest;
                    } else if (paramKey.equals(HttpServletResponse.class.getName())) {
                        paramValues[index] = httpServletResponse;
                    } else {
                        String parameter = httpServletRequest.getParameter(paramKey);
                        if (parameter != null) {
                            paramValues[index] = convert(parameter.trim(), types[index]);
                        }
                    }
                });

                // do invoke
                try {
                    handlerModel.getMethod().invoke(handlerModel.getController(), paramValues);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                return true;

            }
        }

        return false;
    }


    private static Object convert(String parameter, Class<?> typeTarget) {
        if (typeTarget == String.class) {
            return parameter;
        } else if (typeTarget == Integer.class || typeTarget == int.class) {
            return Integer.valueOf(parameter);
        } else if (typeTarget == Long.class || typeTarget == long.class) {
            return Long.valueOf(parameter);
        } else if (typeTarget == Boolean.class || typeTarget == boolean.class) {
            if (parameter.equalsIgnoreCase("true")) {
                return true;
            } else if (parameter.equalsIgnoreCase("false")) {
                return false;
            }

            throw new RuntimeException(String.format("Parameter Type: {} not supported!", typeTarget));
        } else {
            return null;
        }

    }
}
