package com.volia.eadmin.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class HttpUtil {
    private HttpUtil() {
    }

    public static HttpServletResponse writeMessageToResponse(String message, HttpServletResponse response, int status){
        try {
            response.getWriter().print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(status);
        return response;
    }
}
