package com.example.lagomfurniture.utils;


import com.example.lagomfurniture.model.OrderDetail;
import com.example.lagomfurniture.model.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";
    public static final String PRODUCT_SESSION_ID= "productId";
    public static final String ORDER_DETAIL= "orderDetail";

    public static boolean isLoginUserSession(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);
        if (sessionUser == null) {
            return false;
        }
        return true;
    }

    public static User getUserSession(HttpSession session) {
        if (!isLoginUserSession(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static boolean isProductSessionId(HttpSession session) {
        String productId = (String) session.getAttribute(PRODUCT_SESSION_ID);
        if (productId == null) {
            return false;
        }
        return true;
    }

    public static boolean isOrderDetailDataSession (HttpSession session) {
        Object orderDetail= session.getAttribute(ORDER_DETAIL);
        if (orderDetail == null) {
            return false;
        }
        return true;
    }
}
