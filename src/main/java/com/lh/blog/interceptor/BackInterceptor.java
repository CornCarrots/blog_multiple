package com.lh.blog.interceptor;

import com.lh.blog.bean.Manager;
import com.lh.blog.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台数据拦截器
 */
public class BackInterceptor implements HandlerInterceptor {
    @Autowired
    ManagerService managerService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 获取管理员数据
        HttpSession session = httpServletRequest.getSession();
        Manager manager = (Manager) session.getAttribute("manager");
        // 填充关联对象
        managerService.fill(manager);
        // 放入session并返回
        session.setAttribute("manager",manager);
        // 放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
