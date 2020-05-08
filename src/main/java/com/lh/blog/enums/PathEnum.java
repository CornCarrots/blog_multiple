package com.lh.blog.enums;

/**
 * @author linhao
 * @date 2020/5/7 19:39
 */
public enum  PathEnum {

    /**
     * 文章图片
     */
    Article("/article/"),
    /**
     * 公告图片
     */
    Notice("/notice/"),
    /**
     * 分类图片
     */
    Category("/category/"),
    /**
     * 认证图片
     */
    Authorized("/authorized/"),
    /**
     * 管理员头像
     */
    MANAGER_PROFILE("/profile_manager/"),
    /**
     * 用户头像
     */
    USER_PROFILE("/profile_user/"),
    /**
     * 轮播图
     */
    Carousel("/carousel/"),
    /**
     * 积分福利图片
     */
    Power("/power/"),
    /**
     * 系统图片
     */
    Option("/option/");


    public String path;

    PathEnum(String path){
        this.path = path;
    }
}
