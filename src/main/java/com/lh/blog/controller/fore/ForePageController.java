package com.lh.blog.controller.fore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ForePageController {

    @GetMapping(value = "/unauthorized")
    public String unauthorized() {
        return "exception/unauthorized";
    }

    @GetMapping(value = "/error")
    public String error() {
        return "exception/404";
    }

    @GetMapping(value = "/")
    public String index() {
        return "fore/home";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "fore/home";
    }

    @GetMapping(value = "/search")
    public String search() {
        return "fore/search";
    }

    @GetMapping(value = "/category")
    public String category() {
        return "fore/category";
    }

    @GetMapping(value = "/article")
    public String article() {
        return "fore/article";
    }

    @GetMapping(value = "/tag")
    public String tag() { return "fore/tag"; }

    @GetMapping(value = "/comment")
    public String comment() { return "fore/comment"; }

    @GetMapping(value = "/noticeList")
    public String noticeList() {
        return "fore/noticeList";
    }

    @GetMapping(value = "/notice")
    public String notice() {
        return "fore/notice";
    }

    @GetMapping(value="/login")
    public String login(){
        return "fore/login";
    }

    @GetMapping(value="/back")
    public String back(){
        return "fore/admin";
    }

    @GetMapping(value="/logout")
    public String logout(HttpSession session){ session.removeAttribute("user");return "redirect:/"; }

    @GetMapping(value="/register")
    public String register(){
        return "fore/register";
    }

    @GetMapping(value="/registerSuccess")
    public String registerSuccess(){ return "fore/registerSuccess"; }

    @GetMapping(value="/applySuccess")
    public String applySuccess(){ return "fore/applySuccess"; }

    @GetMapping(value="/user")
    public String user(){
        return "fore/user";
    }

    @GetMapping(value="/link")
    public String link(){
        return "fore/link";
    }

    @GetMapping(value="/about")
    public String about(){
        return "fore/about";
    }

    @GetMapping(value="/message")
    public String message(){
        return "fore/message";
    }

    @GetMapping(value="/history")
    public String history(){ return "fore/history"; }

    @GetMapping(value="/setting")
    public String setting(){ return "fore/setting"; }

    @GetMapping(value="/msg")
    public String msg(){
        return "fore/msg";
    }

    @GetMapping(value="/publish")
    public String publish(){
        return "fore/publish";
    }

    @GetMapping(value="/allArticle")
    public String articles(){
        return "fore/allArticle";
    }

    @GetMapping(value="/allUser")
    public String users(){
        return "fore/allUser";
    }

    @GetMapping(value="/editArticle")
    public String editArticle(){
        return "fore/getArticle";
    }

    @GetMapping(value="/allCategory")
    public String categorys(){
        return "fore/allCategory";
    }

    @GetMapping(value="/allTag")
    public String tags(){
        return "fore/allTag";
    }

    @GetMapping(value="/focus")
    public String focus(){
        return "fore/focus";
    }

    @GetMapping(value="/likes")
    public String likes(){
        return "fore/likes";
    }

    @GetMapping(value = "/allStart")
    public String start(){ return "fore/allStart"; }
}