package com.lh.blog.controller.back;

import com.lh.blog.bean.Message;
import com.lh.blog.service.MailService;
import com.lh.blog.service.MessageService;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    MailService mailService;

    @GetMapping(value = "/admin/messages")
    public PageUtil<Message> listMessage(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception{
        start = start<0?0:start;
        PageUtil<Message> page =messageService.list(start, size, 5);
        List<Message> messages = page.getContent();
        messageService.fillUser(messages);
        page.setContent(messages);
        return page;
    }

    @PutMapping(value = "/admin/messages/check/{id}")
    public void check(@PathVariable("id")int id)throws Exception
    {
        Message message = messageService.get(id);
        message.setStatus(0);
        messageService.update(message);
    }

    @PutMapping(value = "/admin/messages/unCheck/{id}")
    public void unCheck(@PathVariable("id")int id)throws Exception
    {
        Message message = messageService.get(id);
        message.setStatus(1);
        messageService.update(message);
    }

    @DeleteMapping(value = "/admin/messages/{id}")
    public String delete(@PathVariable("id")int id)throws Exception
    {
        Message message = messageService.get(id);
        message.setStatus(2);
        messageService.update(message);
        return null;
    }

    @PutMapping(value = "/admin/messages/{id}")
    public void update(@RequestBody Message message) throws MessagingException {
        // 邮件标题
        String title = "浩说网站";
        // 回复日期
        message.setReplyDate(new Date());
        // 通过DAO更改
        messageService.update(message);
        // 填充用户对象，方便后面获取
        messageService.fillUser(message);
        // 系统的官方邮箱
        String from = "953625619@qq.com";
        // 用户个人邮箱
        String to = message.getUser().getEmail();
        // 邮件主题
        String subject = title+"：您好，我们已收到您的留言！";
        // 邮件内容
        String content = message.getReply();
        // 发送
        mailService.sendHtmlMail(from,to,subject,content);
    }

    @PostMapping(value = "/admin/messages/search")
    public List<Message> searchMessage(@RequestParam("key")String key)
    {
        List<Message> messages = messageService.listByKey(key);
        messageService.fillUser(messages);
        return messages;
    }

    @GetMapping(value = "/admin/messages/{id}")
    public Message get(@PathVariable("id")int id)
    {
        Message message = messageService.get(id);
        messageService.fillUser(message);
        return message;
    }



}
