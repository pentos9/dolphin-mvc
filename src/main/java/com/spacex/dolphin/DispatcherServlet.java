package com.spacex.dolphin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spacex.dolphin.beans.DolphinInfo;
import com.spacex.dolphin.beans.HandlerModel;
import com.spacex.dolphin.util.BeanMapUtil;
import com.spacex.dolphin.util.ClassScanUtil;
import com.spacex.dolphin.util.DependenceInjectUtil;
import com.spacex.dolphin.util.HandlerMappingUtil;
import com.spacex.dolphin.util.JsonUtil;
import com.spacex.dolphin.util.PatternUtil;
import com.spacex.dolphin.util.PrintUtil;
import com.spacex.dolphin.util.ServletPrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private List<String> classNames = Lists.newArrayList();
    private Map<String, Object> beanInstanceMap = Maps.newConcurrentMap();
    private Map<String, HandlerModel> handlerModelMap = Maps.newConcurrentMap();

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("DispatcherServlet#init");
        PrintUtil.print("DispatcherServlet#init invoked!");
        classNames = ClassScanUtil.scanPackage(config.getInitParameter("scanPackage"));
        beanInstanceMap = BeanMapUtil.doInstance(classNames);
        handlerModelMap = HandlerMappingUtil.doHandlerMapping(beanInstanceMap);
        System.out.println(handlerModelMap);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //PrintUtil.print("DispatcherServlet#doPost invoked! ");
        //ServletPrintUtil.print(resp, "dolphin-mvc !" + JsonUtil.toJson(DolphinInfo.getDefault()));
        boolean isMatch = PatternUtil.pattern(handlerModelMap, req, resp);
        if (!isMatch) {
            ServletPrintUtil.print(resp, "404 not found!");
        }
    }
}
