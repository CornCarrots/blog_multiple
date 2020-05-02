package com.lh.blog.controller.back;

import com.lh.blog.bean.Authorized;
import com.lh.blog.bean.User;
import com.lh.blog.service.AuthorizedService;
import com.lh.blog.service.MailService;
import com.lh.blog.service.UserService;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorizedController {
    @Autowired
    AuthorizedService authorizedService;
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/admin/authorizeds")
    public PageUtil<Authorized> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception{
        start = start<0?0:start;
        PageUtil<Authorized> page =authorizedService.list(start, size, 5);
        List<Authorized> authorizeds = page.getContent();
        authorizedService.fillUser(authorizeds);
        return page;
    }

    @GetMapping(value = "/admin/authorizeds/{id}")
    public Authorized get(@PathVariable("id")int id) throws Exception {
        Authorized authorized =  authorizedService.get(id);
        return authorized;
    }

    @PutMapping(value = "/admin/authorizeds/{id}")
    public void check(@PathVariable("id")int id)throws Exception
    {
        Authorized authorized = authorizedService.get(id);
        int uid = authorized.getUid();
        User user = userService.get(uid);
        String title = "浩说网站";
        String from = "953625619@qq.com";
        String to = authorized.getEmail();
        String subject = title+"：您好，欢迎认证！";
        String content = "";
        boolean sep = user.isSpecialty();
        if (!sep) {
            user.setSpecialty(true);
            authorized.setStatus(1);
            content = "我是浩说网站的站长，我已经收到您的信息了，恭喜您认证成功。";
        }else {
            user.setSpecialty(false);
            authorized.setStatus(2);
            content = "我是浩说网站的站长，我已经收到您的信息了，很抱歉认证失败，请重新提交认证信息。";
        }
        userService.update(user);
        authorizedService.update(authorized);
        mailService.sendHtmlMail(from,to,subject,content);
    }

    @DeleteMapping(value = "/admin/authorizeds/{id}")
    public String delete(@PathVariable("id")int id)throws Exception
    {
        Authorized authorized = authorizedService.get(id);
        authorized.setStatus(2);
        authorizedService.update(authorized);
        return null;
    }
}
