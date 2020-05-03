package com.lh.blog.filter;

import cn.hutool.core.collection.CollectionUtil;
import com.lh.blog.bean.Module;
import com.lh.blog.service.ModuleService;
import com.lh.blog.util.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *@author linhao
 *@date 2020/4/29 21:48
 */
public class URLHelper {

    private static Logger logger = LoggerFactory.getLogger(URLHelper.class);

    private static List<String> urls;

    public static void init(){
        urls = new ArrayList<>();
        try {
            ModuleService moduleService = SpringContextUtils.getBean(ModuleService.class);
            // 获取根路径
            List<Module> roots = moduleService.listRoot();
            for (Module root : roots) {
                StringBuilder rootURL = new StringBuilder(root.getUrl());
                // 拼装路径
                List<String> paths = moduleService.listURLByParent(rootURL,root.getId());
                urls.addAll(paths);
            }
            logger.info("[拼装维护路径成功] {}条路径", urls.size());
        }catch (Exception e){
            logger.error("[拼装维护路径失败]", e);
        }
    }

    /**
     * 采用单例工厂模式
     * @return
     */
    public static List<String> getUrls(){
        if (urls == null){
            init();
        }
        return urls;
    }
}
