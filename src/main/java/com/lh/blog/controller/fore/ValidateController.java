package com.lh.blog.controller.fore;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.Manager;
import com.lh.blog.bean.User;
import com.lh.blog.service.ManagerService;
import com.lh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ValidateController {
    @Autowired
    UserService userService;
    @Autowired
    ManagerService managerService;


    @GetMapping(value = "/validate/userName")
    public String validateUser(@RequestParam("name")String name){
         User user = userService.get(name);
        if(user!=null)
            return "true";
        return "false";
    }

    @GetMapping(value = "/validate/userEmail")
    public String validateUserEmail(@RequestParam("email")String name){
         User user = userService.getByEmail(name);
        if(user!=null)
            return "true";
        return "false";
    }
//
//    @GetMapping(value = "/validate/manager")
//    public String validateManager(@RequestParam("managerName")String name){
//        Manager result = managerService.getByName(name);
//        if(result!=null)
//            return "true";
//        return "false";
//    }
//
//    @GetMapping(value = "/validate/manager")
//    public String validateManagerEmail(@RequestParam("email")String email){
//        Manager result = managerService.getByEmail(email);
//        if(result!=null)
//            return "true";
//        return "false";
//    }

}
