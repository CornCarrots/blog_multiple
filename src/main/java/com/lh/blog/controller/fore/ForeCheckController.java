package com.lh.blog.controller.fore;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.Article;
import com.lh.blog.bean.Log;
import com.lh.blog.bean.Manager;
import com.lh.blog.bean.User;
import com.lh.blog.cache.UserKey;
import com.lh.blog.service.*;
import com.lh.blog.util.CodeMsg;
import com.lh.blog.util.EncodeUtil;
import com.lh.blog.util.Result;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author linhao
 *@date 2020/4/30 11:00
 */
@RestController
public class ForeCheckController {

    private static Logger logger = LoggerFactory.getLogger(ForeCheckController.class);

    @Autowired
    LogService logService;
    @Autowired
    MailService mailService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    ManagerService managerService;
    @Autowired
    CacheService cacheService;
    /**
     * 检查权威文章 权限
     * @param aid
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/foreArticleCheck")
    public Result articleCheck(@RequestParam("aid") int aid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            Article article = articleService.get(aid);
            boolean isSpecialty = article.isSpecialty();
            logger.info("[访问文章] 校验文章是否权威：{}", isSpecialty);
            // 逻辑：权威文章需要会员才能阅读
            if (isSpecialty) {
                if (user == null) {
                    return Result.error(CodeMsg.USER_EMPTY);
                } else if (user.getMid() == 0) {
                    return Result.error(CodeMsg.MEMBER_EMPTY);
                }
                logger.info("[访问权威文章] uid:{}, mid:{}", user.getId(), user.getMid());
            }
            return Result.success(CodeMsg.ACCESS_ARTICLE_SUCCESS);
        }catch (Exception e){
            logger.error("[访问文章] 校验文章是否权威失败", e);
            return Result.error(CodeMsg.ACCESS_ARTICLE_ERROR);
        }
    }

    /**
     * 认证-检查是否登录
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/foreLoginCheck")
    public Result loginCheck(HttpSession session) {
        // 通过session判断用户是否存在
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return Result.error(CodeMsg.USER_EMPTY);
            }
            return Result.success(CodeMsg.USER_ONLINE);
        }catch (Exception e){
            logger.error("[校验用户状态异常]", e);
            return Result.error(CodeMsg.USER_ERROR);
        }
    }

    /**
     * 检查用户密码
     * @param user
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/foreLoginUser")
    public Result loginUser(@RequestBody @Valid User user, HttpServletRequest request) {
        try {
            String name = user.getName();
            User trueUser = userService.get(name);
            // 检查用户名是否存在
            if (trueUser == null) {
                return Result.error(CodeMsg.USER_NOTEXIST);
            }
            // 检查密码是否正确
            else {
                String truePass = trueUser.getPassword();
                String pass = user.getPassword();
                pass = EncodeUtil.recode(pass, trueUser.getSalt());
                if (!truePass.equals(pass)) {
                    return Result.error(CodeMsg.PASSWORD_ERROR);
                }
            }
            // 通过校验，加入session
            // TODO 优化 采用cookie存储，session是存储在内存中的，当用户量增大时，服务器的压力增大。
            HttpSession session = request.getSession();
            trueUser.setLoginDate(new Date());
            userService.update(trueUser);
            userService.fillMember(trueUser);
            session.setAttribute("user", trueUser);
            logger.info("[用户登录校验成功] uid:{}", trueUser.getId());
            return Result.success(CodeMsg.LOGIN_SUCCESS);
        }catch (Exception e){
            logger.error("[用户登录校验异常]", e);
            return Result.error(CodeMsg.LOGIN_ERROR);
        }
    }

    /**
     * 认证-检查管理员
     * @param manager
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/foreLoginAdmin")
    public Result loginAdmin(@RequestBody Manager manager, HttpServletRequest request)  {
        try {
            // 判断用户名是否存在
            String name = manager.getName();
            List<Manager> managers = managerService.listByName(name);
            if (managers.size() == 0) {
                return Result.error(CodeMsg.MANAGER_NOTEXIST);
            }
            // 判断密码是否正确
            String pass = manager.getPassword();
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(name, pass);
            subject.login(token);
            Manager trueManager = managerService.getByName(subject.getPrincipal().toString());
            // 登录成功
            // TODO 使用切面
            Log log = new Log();
            log.setMid(trueManager.getId());
            log.setCreateDate(new Date());
            log.setText("登录后台管理系统");
            logService.add(log);
            HttpSession session = request.getSession();
            session.setAttribute("manager", trueManager);
            logger.info("[管理员认证成功] mid:{}", manager.getId());
            return Result.success(CodeMsg.LOGIN_MANAGER_SUCCESS);
        }
        catch (AuthenticationException e){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        catch (Exception e){
            logger.error("[管理员认证异常]", e);
            return Result.error(CodeMsg.LOGIN_MANAGER_ERROR);
        }
    }

    /**
     * 用户忘记密码
     * @param email
     * @return
     */
    @GetMapping(value = "/forgetUser")
    public Result forgetUser(@RequestParam("email") @Email String email) {
        try {
            // 用户不存在
            User user = userService.getByEmail(email);
            if (user == null) {
                return Result.error(CodeMsg.USER_NOTEXIST);
            } else {
                // 生成验证码
                String random = RandomStringUtils.randomAlphanumeric(8);
                cacheService.set(UserKey.getRandom, user.getId() + "", random);
                // 生成邮件
                String from = "953625619@qq.com";
                String to = user.getEmail();
                String subject = "浩说：你正在找回你的密码！";
                String content = "<html>\n" +
                        "<body>\n" +
                        "<BR>\n" +
                        "<div align='center'>\n" +
                        " <h3>恭喜您，邮箱验证成功！</h3>\n" +
                        "    <h3>您的验证码为：<b>\"" + random + "\"</b></h3>" +
                        "<BR>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>";
                // 发送邮件
                mailService.sendHtmlMail(from, to, subject, content);
                return Result.success(CodeMsg.GET_RANDOM_SUCCESS);
            }
        }catch (MessagingException e){
            logger.error("[发送邮件异常]", e);
            return Result.error(CodeMsg.SEND_EMAIL_ERROR);
        }catch (Exception e){
            logger.error("[用户获取验证码异常]", e);
            return Result.error(CodeMsg.GET_RANDOM_ERROR);
        }
    }

    /**
     * 用户重置密码
     * @param object
     * @return
     */
    @PostMapping(value = "/foreUserPass")
    public Result setUserPass(@RequestBody JSONObject object) {
        try {
            String email = object.get("email").toString();
            User user = userService.getByEmail(email);
            // 判断验证码是否正确
            String random = cacheService.get(UserKey.getRandom, user.getId() + "");
            String key = object.get("key").toString();
             if (StrUtil.equals(key, random)) {
                 // 初始化新密码
                 String pass = object.get("pass").toString();
                // 加密明文
                Map<String, Object> map = EncodeUtil.encode(pass);
                user.setSalt(map.get("salt").toString());
                user.setPassword(map.get("pass").toString());
                userService.update(user);
                logger.info("[用户重置密码成功] uid:{}", user.getId());
                return Result.success(CodeMsg.MODIFY_PASSWORD_SUCCESS);
            }
            return Result.error(CodeMsg.VALIDATE_RANDOM_ERROR);
        }catch (Exception e){
            logger.error("[用户重置密码异常]", e);
            return Result.error(CodeMsg.MODIFY_PASSWORD_ERROR);
        }
    }

    /**
     * 管理员忘记密码
     * @param email
     * @return
     */
    @GetMapping(value = "/forgetManager")
    public Result forgetManager(@RequestParam("email") @Email String email) {
        try {
            Manager manager = managerService.getByEmail(email);
            if (manager == null) {
                return Result.error(CodeMsg.USER_NOTEXIST);
            } else {
                String random = RandomStringUtils.randomAlphanumeric(8);
                String from = "953625619@qq.com";
                String to = manager.getEmail();
                String subject = "浩说：你正在找回你的密码！";
                String content = "<html>\n" +
                        "<body>\n" +
                        "<BR>\n" +
                        "<div align='center'>\n" +
                        " <h3>恭喜您，邮箱验证成功！</h3>\n" +
                        "    <h3>您的验证码为：<b>\"" + random + "\"</b></h3>" +
                        "<BR>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>";
                mailService.sendHtmlMail(from, to, subject, content);
                return Result.success(random);
            }
        }
        catch (MessagingException e){
            logger.error("[发送邮件异常]", e);
            return Result.error(CodeMsg.SEND_EMAIL_ERROR);
        }catch (Exception e){
            logger.error("[管理员获取验证码异常]", e);
            return Result.error(CodeMsg.GET_RANDOM_ERROR);
        }
    }

    /**
     * 管理员重置密码
     * @param object
     * @return
     */
    @PostMapping(value = "/foreManagerPass")
    public Result setManagerPass(@RequestBody JSONObject object) {
        try {
            String random = object.get("random").toString();
            String key = object.get("key").toString();
            String pass = object.get("pass").toString();
            String email = object.get("email").toString();
            if (key.equals(random)) {
                Manager manager = managerService.getByEmail(email);
                Map<String, Object> map = EncodeUtil.encode(pass);
                manager.setSalt(map.get("salt").toString());
                manager.setPassword(map.get("pass").toString());
                managerService.update(manager);
                logger.info("[管理员重置密码成功] uid:{}", manager.getId());
                return Result.success(CodeMsg.MODIFY_PASSWORD_SUCCESS);
            }
            return Result.error(CodeMsg.VALIDATE_RANDOM_ERROR);
        }catch (Exception e){
            logger.error("[管理员重置密码异常]", e);
            return Result.error(CodeMsg.MODIFY_PASSWORD_ERROR);
        }
    }

    /**
     * 用户注册
     * @param user
     * @throws Exception
     */
    @PostMapping(value = "/foreRegister")
    public Result register(@RequestBody @Valid User user, HttpServletRequest request) {
        try {
            Map<String, Object> map = EncodeUtil.encode(user.getPassword());
            // 加密明文
            user.setPassword(map.get("pass").toString());
            user.setSalt(map.get("salt").toString());
            // 设置注册时间
            user.setRegisterDate(new Date());
            // 用户头像
            String path = request.getServletContext().getRealPath("image/profile_user");
            String folder = userService.getImgPath(path);
            user.setImg(folder);
            // 初始积分
            user.setScore(10);
            userService.add(user);
            logger.info("[用户注册成功] uid:{}", user.getId());
            return Result.success(CodeMsg.REGISTER_SUCCESS);
        }catch (Exception e){
            logger.error("[用户注册失败]", e);
            return Result.success(CodeMsg.REGISTER_ERROR);
        }
    }
}
