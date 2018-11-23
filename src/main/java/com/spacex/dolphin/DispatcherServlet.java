package com.spacex.dolphin;

import com.google.common.collect.Lists;
import com.spacex.dolphin.util.BeanFactoryUtil;
import com.spacex.dolphin.util.ClassUtil;
import com.spacex.dolphin.util.JsonUtil;
import com.spacex.dolphin.util.PrintUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("DispatcherServlet#init invoked!");
        List<String> classNames = ClassUtil.scanPackage(config.getInitParameter("scanPackage"));
        Map<String, Object> beanMap = BeanFactoryUtil.doInstance(classNames);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintUtil.print("DispatcherServlet#doGet invoked! ");
        out(resp, "dolphin-mvc DispatcherServlet#doGet!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintUtil.print("DispatcherServlet#doPost invoked! ");
        out(resp, "dolphin-mvc !");
    }

    private void out(HttpServletResponse response, String text) {
        try {
            //response.setContentType("application/json;charset=utf-8");
            //response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            Map<String, String> textMap = new HashMap<>();
            textMap.put("text", text);
            response.getWriter().print(JsonUtil.toJson(textMap));
            String jsonStr = "{\"name\":\"dolphin\",\"type\":\"framework\"}";
            Map<String, String> message = new HashMap<>();
            message.put("name", "dolphin");
            message.put("type", "framework");
            message.put("hook", "jackson");
            response.getWriter().write(JsonUtil.toJson(message));
            response.getWriter().write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
