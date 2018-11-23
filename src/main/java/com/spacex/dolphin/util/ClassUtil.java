package com.spacex.dolphin.util;

import com.google.common.collect.Lists;
import com.spacex.dolphin.annotation.Component;
import com.spacex.dolphin.annotation.Configuration;
import com.spacex.dolphin.annotation.Controller;
import com.spacex.dolphin.annotation.Service;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.net.URL;
import java.util.List;

public class ClassUtil {
    public static List<String> scanPackage(String packageName) {

        List<String> classNames = Lists.newArrayList();
        URL url = Thread.currentThread().getContextClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));

        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                List<String> classNameList = scanPackage(packageName + "." + file.getName());
                if (CollectionUtils.isNotEmpty(classNameList)) {
                    classNames.addAll(classNameList);
                }
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }

                String className = packageName + "." + file.getName().replace(".class", "");
                try {
                    Class clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Configuration.class) || clazz.isAnnotationPresent(Component.class)) {
                        classNames.add(className);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return classNames;
    }
}
