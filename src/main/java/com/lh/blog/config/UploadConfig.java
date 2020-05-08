package com.lh.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author linhao
 * @date 2020/5/7 18:29
 * @descripe 上传图片
 */
@Configuration
public class UploadConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private Environment environment;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = environment.getProperty("upload-path");
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + path);

        super.addResourceHandlers(registry);
    }
}
