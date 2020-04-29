package com.lh.blog.config;

import com.lh.blog.interceptor.BackInterceptor;
import com.lh.blog.interceptor.ForeInterceptor;
import com.lh.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public HandlerInterceptor getForeInterceptor(){
        return new ForeInterceptor();
    }
    @Bean
    public HandlerInterceptor getBackInterceptor(){ return new BackInterceptor(); }
    @Bean
    public HandlerInterceptor getLoginInterceptor(){ return new LoginInterceptor(); }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //注册登录拦截器
        InterceptorRegistration loginRegistration = registry.addInterceptor(getLoginInterceptor());
        //配置拦截路径
        loginRegistration.addPathPatterns("/history/**");
        loginRegistration.addPathPatterns("/msg/**");
        loginRegistration.addPathPatterns("/allStart/**");
        loginRegistration.addPathPatterns("/setting/**");
        loginRegistration.addPathPatterns("/publish/**");
        loginRegistration.addPathPatterns("/allCategory/**");
        loginRegistration.addPathPatterns("/message/**");
        loginRegistration.addPathPatterns("/focus/**");
        loginRegistration.addPathPatterns("/likes/**");


        //注册前台拦截器
        InterceptorRegistration foreRegistration = registry.addInterceptor(getForeInterceptor());
        //配置拦截路径
        foreRegistration.addPathPatterns("/**");
        //配置不拦截的路径
        foreRegistration.excludePathPatterns("/*/admin/**");

        //注册后台拦截器
        InterceptorRegistration BackRegistration = registry.addInterceptor(getBackInterceptor());
        //配置拦截路径
        BackRegistration.addPathPatterns("/*/admin/**");
        //配置不拦截的路径

        //注册会员拦截器
        //配置拦截路径
        //配置不拦截的路径


    }
}
