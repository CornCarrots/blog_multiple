package com.lh.blog.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.lh.blog.bean.Manager;
import com.lh.blog.service.PermissionService;
import com.lh.blog.util.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
public class URLPathMatchingFilter extends HttpMethodPermissionFilter {

    private static Logger logger = LoggerFactory.getLogger(URLPathMatchingFilter.class);

    @Autowired
    PermissionService permissionService;

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 过滤器先从bean拿权限服务
        if (null == permissionService) {
            permissionService = SpringContextUtils.getBean(PermissionService.class);
        }

        // 获取资源路径
        String requestURI = getPathWithinApplication(request);
        // 获取操作
        String requestMethod = getHttpMethodAction(request);
        // 获取登录对象
        Subject subject = SecurityUtils.getSubject();
        // 如果没有登录，就跳转到登录页面
        if (!subject.isAuthenticated()) {
            logger.info("[校验登录失败]: {} login error", subject.getPrincipal());
            WebUtils.issueRedirect(request, response, "/back");
            return false;
        }else {
            logger.info("[校验登录成功]: {} login success", subject.getPrincipal());
        }

        // 检查资源路径
        // 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
        boolean needInterceptor = permissionService.needInterceptor(requestURI);
        if (!needInterceptor) {
            logger.info("[校验资源路径] uri:{} method:{} ,not protected", requestURI, requestMethod);
            return true;
        } else {
            logger.info("[校验资源路径] uri:{} method:{} , protected", requestURI, requestMethod);
            // 看看有没有权限,有则放行
            Manager manager = (Manager) subject.getSession().getAttribute("manager");
            // 进行优化，直接用管理员id就可以，
            // 不要再通过名字，节省SQL查询的开销 776ms -> 710ms -> 2ms
            boolean hasPermission = permissionService.hasPermission(manager.getId(), requestURI, requestMethod);
//            boolean hasPermission = permissionService.hasPermission(manager.getId(), requestURI, requestMethod);
            if (hasPermission) {
                logger.info("[校验权限] uri:{} method:{} manager:{}, success", requestURI, requestMethod, manager.getId());
                return true;
            }
            // 没有的话拦截
            else {
                logger.info("[校验权限] uri:{} method:{} manager:{}, fail", requestURI, requestMethod, manager.getId());
                UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");
                subject.getSession().setAttribute("ex", ex);
                WebUtils.issueRedirect(request, response, "/unauthorized");
                return false;
            }
        }
    }

}
