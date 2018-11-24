package com.spacex.dolphin.beans;

import java.lang.reflect.Method;
import java.util.Map;

public class HandlerModel {
    private Method method;
    private Object controller;
    private Map<String, Integer> paramMap;

    public HandlerModel() {
    }

    public HandlerModel(Method method, Object controller, Map<String, Integer> paramMap) {
        this.method = method;
        this.controller = controller;
        this.paramMap = paramMap;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Map<String, Integer> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Integer> paramMap) {
        this.paramMap = paramMap;
    }
}
