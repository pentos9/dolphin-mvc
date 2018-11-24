package com.spacex.dolphin;

import com.spacex.dolphin.bean.DolphinInfo;
import com.spacex.dolphin.util.BeanMapUtil;
import com.spacex.dolphin.util.ClassScanUtil;
import com.spacex.dolphin.util.JsonUtil;
import com.spacex.dolphin.util.PrintUtil;
import com.spacex.dolphin.util.ServletPrintUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        PrintUtil.print("DispatcherServlet#init invoked!");
        List<String> classNames = ClassScanUtil.scanPackage(config.getInitParameter("scanPackage"));
        Map<String, Object> beanMap = BeanMapUtil.doInstance(classNames);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintUtil.print("DispatcherServlet#doGet invoked! ");
        ServletPrintUtil.print(resp, "dolphin-mvc DispatcherServlet#doGet!" + JsonUtil.toJson(DolphinInfo.getDefault()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintUtil.print("DispatcherServlet#doPost invoked! ");
        ServletPrintUtil.print(resp, "dolphin-mvc !" + JsonUtil.toJson(DolphinInfo.getDefault()));
    }
}
