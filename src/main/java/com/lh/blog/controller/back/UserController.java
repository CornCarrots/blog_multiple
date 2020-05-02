package com.lh.blog.controller.back;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.User;
import com.lh.blog.service.UserService;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/admin/users")
    public PageUtil<User> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception{
        start = start<0?0:start;
        PageUtil<User> page =userService.list(start,size,5);
        List<User> users = page.getContent();
        return page;
    }

    @PutMapping(value = "/admin/users/shield/{id}")
    public void shieldUser(@PathVariable("id")int id)throws Exception
    {
        User user = userService.get(id);
        user.setStatus(1);
        userService.update(user);
    }
    @PutMapping(value = "/admin/users/check/{id}")
    public void checkUser(@PathVariable("id")int id)throws Exception
    {
        User user = userService.get(id);
        user.setStatus(0);
        userService.update(user);
    }

    @PostMapping(value = "/admin/users/search")
    public List<User> search(@RequestParam(value = "key") String key) throws Exception
    {
        List<User> users =  userService.listByKey(key);
        return users;
    }

    @GetMapping(value = "/admin/users/{id}")
    public User getUser(@PathVariable("id")int id)throws Exception
    {
        User user = userService.get(id);
        return user;
    }



}
