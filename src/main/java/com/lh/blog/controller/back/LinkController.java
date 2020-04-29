package com.lh.blog.controller.back;

import com.lh.blog.bean.Link;
import com.lh.blog.service.LinkService;
import com.lh.blog.service.MailService;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class LinkController {
    @Autowired
    LinkService linkService;
    @Autowired
    MailService mailService;

    @GetMapping(value = "/admin/links")
    public PageUtil<Link> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception{
        start = start<0?0:start;
        PageUtil<Link> page =linkService.list(start, size, 5);
//        System.out.println(page.getContent());
        return page;
    }

    @PutMapping(value = "/admin/links/check/{id}")
    public void check(@PathVariable("id")int id)throws Exception
    {
        Link link = linkService.get(id);
        link.setStatus(0);
        linkService.update(link);
        String title = "浩说网站";
        String from = "953625619@qq.com";
        String to = link.getEmail();
        String subject = title+"：您好，欢迎交换友链！";
        String content = "我是浩说网站的站长，我已经收到您的信息了，同意交换友链，现已将您的连接信息放到网站上了。";
        mailService.sendHtmlMail(from,to,subject,content);
    }

    @PutMapping(value = "/admin/links/unCheck/{id}")
    public void unCheck(@PathVariable("id")int id)throws Exception
    {
        Link link = linkService.get(id);
        link.setStatus(1);
        linkService.update(link);
        String title = "浩说网站";
        String from = "953625619@qq.com";
        String to = link.getEmail();
        String subject = title+"：您好，欢迎交换友链！";
        String content = "我是浩说网站的站长，我已经收到您的信息了，十分抱歉，由于一些不得已的原因无法交换友链，请见谅！";
        mailService.sendHtmlMail(from,to,subject,content);
    }

    @DeleteMapping(value = "/admin/links/{id}")
    public String delete(@PathVariable("id")int id)throws Exception
    {
        Link link = linkService.get(id);
        link.setStatus(2);
        linkService.update(link);
        return null;
    }
}
