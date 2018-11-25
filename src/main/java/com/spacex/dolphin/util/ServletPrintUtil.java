package com.spacex.dolphin.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletPrintUtil {

    public static void print(HttpServletResponse httpServletResponse, String text) {
        print(httpServletResponse, text, null);
    }

    public static void print(HttpServletResponse httpServletResponse, String text, String contentType) {
        if (httpServletResponse == null || StringUtils.isBlank(text)) {
            return;
        }

        if (StringUtils.isNotBlank(contentType)) {
            httpServletResponse.setContentType(contentType);
        }

        httpServletResponse.setCharacterEncoding("utf-8");

        try {
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.print(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
