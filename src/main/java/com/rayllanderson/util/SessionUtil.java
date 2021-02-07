package com.rayllanderson.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {

    /**
     * @return o id do usuário logado na session atual
     */
    public static Long getUserId(HttpServletRequest request) {
	return (Long) request.getSession().getAttribute("userId");
    }
    
    /**
     * @return seta o id do usuário logado na session atual
     */
    public static void setUserId(Long userId, HttpServletRequest request) {
	 request.getSession().setAttribute("userId", userId);
    }
}
