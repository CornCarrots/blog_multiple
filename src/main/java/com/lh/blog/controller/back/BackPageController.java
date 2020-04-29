package com.lh.blog.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BackPageController {
    @GetMapping(value="/admin")
    public String admin(){
        return "back/home";
    }
    @GetMapping(value="/admin_login")
    public String login(){
        return "back/home";
    }
    @GetMapping(value="/admin/logout")
    public String logout(){
        return "fore/admin";
    }
    @GetMapping(value="/admin/article")
    public String article(){ return "back/blog/listArticle"; }
    @GetMapping(value="/admin/category")
    public String category(){ return "back/blog/listCategory"; }
    @GetMapping(value="/admin/tag")
    public String tag(){ return "back/blog/listTag"; }
    @GetMapping(value="/admin/addArticle")
    public String addArticle(){ return "back/blog/addArticle"; }
    @GetMapping(value="/admin/getArticle")
    public String getArticle(){ return "back/blog/getArticle"; }
    @GetMapping(value="/admin/message")
    public String message(){ return "back/msg/listMessage"; }
    @GetMapping(value="/admin/comment")
    public String comment(){ return "back/msg/listComment"; }
    @GetMapping(value="/admin/user")
    public String user(){ return "back/user/listUser"; }
    @GetMapping(value="/admin/member")
    public String member(){ return "back/user/listMember"; }
    @GetMapping(value="/admin/score")
    public String score(){ return "back/user/listScore"; }
    @GetMapping(value="/admin/power")
    public String power(){ return "back/user/listPower"; }
    @GetMapping(value="/admin/option")
    public String option(){ return "back/system/listOption"; }
    @GetMapping(value="/admin/carousel")
    public String carousel(){ return "back/web/listCarousel"; }
    @GetMapping(value="/admin/profile")
    public String profile(){ return "back/web/listUser"; }
    @GetMapping(value="/admin/link")
    public String link(){ return "back/web/listLink"; }
    @GetMapping(value="/admin/notice")
    public String notice(){ return "back/web/listNotice"; }
    @GetMapping(value="/admin/manager")
    public String manager(){ return "back/system/listManager"; }
    @GetMapping(value="/admin/role")
    public String role(){ return "back/system/listRole"; }
    @GetMapping(value="/admin/permission")
    public String permission(){ return "back/system/listPermission"; }
    @GetMapping(value="/admin/module")
    public String module(){ return "back/system/listModule"; }
    @GetMapping(value="/admin/operation")
    public String operation(){ return "back/system/listOperation"; }
    @GetMapping(value="/admin/person")
    public String person(){ return "back/person"; }
    @GetMapping(value="/admin/authorized")
    public String authorized(){ return "back/user/listAuthorized"; }
}
