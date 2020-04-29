package com.lh.blog.controller.fore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.*;
import com.lh.blog.dao.FocusDAO;
import com.lh.blog.dao.LikeDAO;
import com.lh.blog.dao.OptionDAO;
import com.lh.blog.es.CiLin;
import com.lh.blog.service.*;
import com.lh.blog.util.EncodeUtil;
import com.lh.blog.util.ImageUtil;
import com.lh.blog.util.PageUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

@RestController
public class ForeController {
    @Autowired
    ArticleService articleService;
    @Autowired
    CarouselService carouselService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    LikeService likeService;
    @Autowired
    StartService startService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    ManagerService managerService;
    @Autowired
    LogService logService;
    @Autowired
    MailService mailService;
    @Autowired
    HistoryService historyService;
    @Autowired
    OptionService optionService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    LinkService linkService;
    @Autowired
    MessageService messageService;
    @Autowired
    MsgService msgService;
    @Autowired
    TagArticleService tagArticleService;
    @Autowired
    AuthorizedService authorizedService;
    @Autowired
    UserTagService userTagService;
    @Autowired
    FocusService focusService;

    @GetMapping("/foreHome")
    public Map<String, Object> home(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "4") int size) {
        Map<String, Object> map = new HashMap<>();
        PageUtil<Article> pages = articleService.listForShow(start, size, 5, 0);
        List<Article> articles = pages.getContent();
        articleService.fillCategory(articles);
        articleService.fillUser(articles);
        pages.setContent(articles);
//        List<Carousel> carousels = carouselService.list();
        map.put("pages", pages);
//        map.put("carousels", carousels);
        return map;
    }

    @GetMapping(value = "/foreSearch")
    public Map<String, Object> search(@RequestParam("key") String key, @RequestParam(value = "order", defaultValue = "id") String order, @RequestParam(value = "sort", defaultValue = "false") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        PageUtil<Article> pages = articleService.listByKey(key, start, size, 5, order, sort);
        List<Article> articles = pages.getContent();
        articleService.fillCategory(articles);
        articleService.fillUser(articles);
        pages.setContent(articles);
        map.put("pages", pages);
        map.put("key", key);
        return map;
    }


    @GetMapping("/foreArticle/{aid}")
    public Map<String, Object> article(@PathVariable("aid") int aid, HttpSession session, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Article article = articleService.get(aid);
//        List<Article> articles = articleService.list();
        boolean has = false;
//        int index = articles.indexOf(articleService.get(aid));
//        PageUtil<Article> page = articleService.listForShow(index, 1, 1, 0);
//        List<Article> article = page.getContent();
        article.setView(article.getView() + 1);
        articleService.update(article);
        articleService.fillCategory(article);
        articleService.fillTags(article);
        articleService.fillUser(article);
//        page.setContent(article);
        PageUtil<Comments> comments = commentsService.listByParent(aid, start, size, 5);
        List<Comments> comments1 = comments.getContent();
        commentsService.fillUser(comments1);
        commentsService.fillChild(comments1);
        comments.setContent(comments1);
        map.put("article", article);
        map.put("pages", comments);
        map.put("key", optionService.getByKey(OptionDAO.WEB_KEY));
//        map.put("page1", page.isHasPrevious() ? articles.get(index - 1).getId() : -1);
//        map.put("page2", page.isHasNext() ? articles.get(index + 1).getId() : -1);
        if (user != null) {
            has = commentsService.checkUser(user.getId(), aid);
            commentsService.fillLike(comments1, user.getId());
            for (Comments comments2 :
                    comments1) {
                List<Comments> comments3 = comments2.getChild();
                commentsService.fillLike(comments3, user.getId());
                comments2.setChild(comments3);
            }
            articleService.fillLikeAndStart(article, user.getId());
            historyService.init(aid, user.getId());
        }
        map.put("has", has);
        map.put("user", user);
        return map;
    }

    @DeleteMapping(value = "/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") int id) throws Exception {
        commentsService.delete(id);
        return null;
    }

    @PostMapping(value = "/startArticle")
    public String startArticle(@RequestBody Article article, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        String s = "";
        if (article.isHasStart()) {
            startService.delete(article.getId(), user.getId());
            article.setStart(article.getStart() - 1);
            articleService.update(article);
            s = "delete";
        } else {
            Start start = new Start();
            start.setAid(article.getId());
            start.setUid(user.getId());
            startService.add(start);
            article.setStart(article.getStart() + 1);
            articleService.update(article);
            s = "save";
        }
        return s;
    }

    @PostMapping(value = "/likeArticle")
    public String likeArticle(@RequestBody Article article, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        String s = "";
        if (article.isHasLike()) {
            likeService.deleteArticle(article.getId(), user.getId());
            article.setLike(article.getLike() - 1);
            articleService.update(article);
            s = "delete";
        } else {
            Like like = new Like();
            like.setAcid(article.getId());
            like.setUid(user.getId());
            like.setType(LikeDAO.type_article);
            likeService.add(like);
            article.setLike(article.getLike() + 1);
            articleService.update(article);
            s = "save";
        }
        return s;
    }

    @PostMapping(value = "/likeComment")
    public String likeComment(@RequestBody Comments comments, HttpSession session) throws Exception {
        System.out.println(comments);
        User user = (User) session.getAttribute("user");
        String s = "";
        if (comments.isHasLike()) {
            likeService.deleteComment(comments.getId(), user.getId());
            comments.setLike(comments.getLike() - 1);
            commentsService.update(comments);
            s = "delete";
        } else {
            Like like = new Like();
            like.setAcid(comments.getId());
            like.setUid(user.getId());
            like.setType(LikeDAO.type_comment);
            likeService.add(like);
            s = "save";
            comments.setLike(comments.getLike() + 1);
            commentsService.update(comments);
        }
        return s;
    }

    @GetMapping(value = "/foreCategory/{cid}")
    public Map<String, Object> category(@PathVariable("cid") int cid, @RequestParam(value = "order") String order, @RequestParam(value = "sort") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Category category = categoryService.get(cid);
        categoryService.fillChild(category);
        PageUtil<Article> pages = articleService.listByCategory(cid, start, size, 5, order, sort);
        List<Article> articles = pages.getContent();
        articleService.fillCategory(articles);
        articleService.fillUser(articles);
        pages.setContent(articles);
        map.put("pages", pages);
        map.put("category", category);
        return map;
    }

    @GetMapping(value = "/foreNotice/{nid}")
    public Notice notice(@PathVariable("nid") int nid) throws Exception {
        return noticeService.get(nid);
    }

    @GetMapping(value = "/foreNotice")
    public PageUtil<Notice> noticeList(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        start = start < 0 ? 0 : start;
        PageUtil<Notice> page = noticeService.list(start, size, 5);
        return page;
    }

    @GetMapping(value = "/foreTag/{tid}")
    public Map<String, Object> tag(@PathVariable("tid") int tid, @RequestParam(value = "order") String order, @RequestParam(value = "sort") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Tag tag = tagService.get(tid);
        PageUtil<Article> pages = articleService.listByTag(tid, start, size, 5, order, sort);
        List<Article> articles = pages.getContent();
        articleService.fillCategory(articles);
        articleService.fillUser(articles);
        pages.setContent(articles);
        map.put("pages", pages);
        map.put("tag", tag);
        return map;
    }

    @GetMapping(value = "/foreArticleCheck")
    public String articleCheck(@RequestParam("aid") int aid, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        Article article = articleService.get(aid);
        if (article.isSpecialty()) {
            if (user == null) {
                return "nologin";
            } else if (user.getMid() == 0) {
                return "nomember";
            }
        }
        return "ok";

    }

    @GetMapping(value = "/foreLoginCheck")
    public String loginCheck(HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "fail";
        return "success";
    }

    @PostMapping(value = "/foreLoginUser")
    public String loginUser(@RequestBody User user, HttpServletRequest request) throws Exception {
        String name = user.getName();
        User trueUser = userService.get(name);
        if (trueUser == null) {
            return "user404";
        } else {
            String truePass = trueUser.getPassword();
            String pass = user.getPassword();
            pass = EncodeUtil.recode(pass, trueUser.getSalt());
            if (!truePass.equals(pass)) {
                return "fail";
            }
        }
        HttpSession session = request.getSession();
        trueUser.setLoginDate(new Date());
        userService.update(trueUser);
        userService.fillMember(trueUser);
        session.setAttribute("user", trueUser);
        return "success";
    }

    @PostMapping(value = "/foreLoginAdmin")
    public String loginAdmin(@RequestBody Manager manager, HttpServletRequest request) throws Exception {

        String name = manager.getName();
        List<Manager> managers = managerService.listByName(name);
        if (managers.size() == 0) {
            return "manager404";
        }
        String pass = manager.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, pass);
        try {
            subject.login(token);
            Manager manager1 = managerService.getByName(subject.getPrincipal().toString());
            Log log = new Log();
            log.setMid(manager1.getId());
            log.setCreateDate(new Date());
            log.setText("登录后台管理系统");
            logService.add(log);
            HttpSession session = request.getSession();
            session.setAttribute("manager", manager1);
            return "success";
        } catch (AuthenticationException e) {
            return "fail";
        }
    }

    @GetMapping(value = "/forgetUser")
    public Map<String, Object> forgetUser(@RequestParam("email") String email) {
        String result = "";
        String random = "";
        // 用户为空直接结束
        User user = userService.getByEmail(email);
        if (user == null) {
            result = "noUser";
        } else {
            // 生成验证码
            random = RandomStringUtils.randomAlphanumeric(8);
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
            try {
                // 发送邮件
                mailService.sendHtmlMail(from, to, subject, content);
                result = "yes";
            } catch (Exception e) {
                result = "no";
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("random", random);
        map.put("email", email);
        return map;
    }

    @PostMapping(value = "/foreUserPass")
    public String setUserPass(@RequestBody JSONObject object) {
        // 判断验证码是否正确
        String random = object.get("random").toString();
        // 初始化新密码
        String key = object.get("key").toString();
        String pass = object.get("pass").toString();
        String email = object.get("email").toString();
        if (key.equals(random)) {
            User user = userService.getByEmail(email);
            // 加密明文
            Map<String, Object> map = EncodeUtil.encode(pass);
            user.setSalt(map.get("salt").toString());
            user.setPassword(map.get("pass").toString());
            userService.update(user);
            return "ok";
        }
        return "no";
    }

    @GetMapping(value = "/forgetManager")
    public Map<String, Object> forgetManager(@RequestParam("email") String email) {
        String result = "";
        String random = "";
        Manager manager = managerService.getByEmail(email);
        if (manager == null) {
            result = "noUser";
        } else {
            random = RandomStringUtils.randomAlphanumeric(8);
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
            try {
                mailService.sendHtmlMail(from, to, subject, content);
                result = "yes";
            } catch (MessagingException e) {
                result = "no";
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("random", random);
        map.put("email", email);
        return map;
    }

    @PostMapping(value = "/foreManagerPass")
    public String setManagerPass(@RequestBody JSONObject object) {
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
            return "ok";
        }
        return "no";
    }

    @PostMapping(value = "/foreRegister")
    public void register(@RequestBody User user, HttpServletRequest request) throws Exception {
        Map<String, Object> map = EncodeUtil.encode(user.getPassword());
        // 加密明文
        user.setPassword(map.get("pass").toString());
        user.setSalt(map.get("salt").toString());
        // 设置注册时间
        user.setRegisterDate(new Date());
        // 用户头像
        File imageFolder = new File("image/profile_user");
        String files[] = imageFolder.list();
        int num = files.length - 1;
        int imgId = (int) (Math.random() * num) + 1;
        String folder = "/image/profile_user/" + imgId + ".jpg";
        user.setImg(folder);
        // 初始积分
        user.setScore(10);
        userService.add(user);
    }

    /**
     * 发表评论
     **/
    @PostMapping(value = "/foreCommitComment")
    public String commit(@RequestBody Comments comments, HttpSession session) throws Exception {
        // 当前评论时间
        comments.setCreateDate(new Date());
        // 处理用户逻辑，根据业务规则，评论的用户加积分
        User user = (User) session.getAttribute("user");
        user.setScore(user.getScore() + 2);
        userService.update(user);
        // 再处理评论，进行持久化
        comments.setUid(user.getId());
        commentsService.add(comments);
        // 最后处理文章
        Article article = articleService.get(comments.getAid());
        article.setComment(article.getComment() + 1);
        articleService.update(article);
        return "yes";
    }

    @PostMapping(value = "/foreLink")
    public void applyLink(@RequestBody Link link) throws Exception {
        link.setCreateDate(new Date());
        linkService.add(link);
    }

    @PostMapping(value = "/foreMessage")
    public String addMessage(@RequestBody Message message, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        message.setCreateDate(new Date());
        message.setUid(user.getId());
        messageService.add(message);
        return "ok";
    }

    @GetMapping(value = "/foreSetting")
    public Map<String, Object> setting(HttpServletRequest request, HttpSession session, @RequestParam(value = "uid", defaultValue = "0") int uid) throws Exception {
        User user = null;
        if (uid == 0) {
            user = (User) session.getAttribute("user");
        } else {
            user = userService.get(uid);
        }
//        File imageFolder= new File("/home/ftpuser/blog/image/profile_user");
        File imageFolder = new File(request.getServletContext().getRealPath("image/profile_user"));
        String files[] = imageFolder.list();
        int num = files.length - 1;
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("num", num);
        return map;
    }

    @PutMapping(value = "/foreSetting/{id}")
    public String modifyUser(HttpSession session, @RequestBody User user) throws Exception {
        Map<String, Object> map = EncodeUtil.encode(user.getPassword());
        user.setSalt(map.get("salt").toString());
        user.setPassword(map.get("pass").toString());
        userService.update(user);
        session.setAttribute("user", userService.get(user.getId()));
        return "ok";
    }

    @PostMapping(value = "/foreSetting")
    public String uploadUser(HttpSession session, @RequestBody JSONObject object) throws Exception {
        User user = (User) session.getAttribute("user");
        String folder = "/image/profile_user/" + Integer.parseInt(object.get("num").toString()) + ".jpg";
        user.setImg(folder);
        userService.update(user);
        return "ok";
    }


    @GetMapping(value = "/foreHistories")
    public PageUtil<History> history(HttpSession session, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        start = start < 0 ? 0 : start;
        User user = (User) session.getAttribute("user");
        PageUtil<History> page = historyService.listByUser(user.getId(), start, size, 5);
        List<History> histories = page.getContent();
        historyService.fill(histories);
        page.setContent(histories);
        return page;
    }

    @GetMapping(value = "/foreStart")
    public PageUtil<Article> start(HttpSession session, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        start = start < 0 ? 0 : start;
        User user = (User) session.getAttribute("user");
        PageUtil<Article> page = articleService.listByUserAndStart(user.getId(), start, size, 5);
        List<Article> articles = page.getContent();
        articleService.fillCategory(articles);
        page.setContent(articles);
        return page;
    }

    @DeleteMapping(value = "/foreHistories/{id}")
    public String deleteHistory(@PathVariable("id") int id) throws Exception {
        historyService.delete(id);
        return null;
    }

    @DeleteMapping(value = "/foreStart/{id}")
    public String unStart(@PathVariable("id") int id) throws Exception {
        startService.delete(id);
        return null;
    }

    @GetMapping(value = "/foreUser")
    public Map<String, Object> user(HttpSession session, @RequestParam(value = "uid", defaultValue = "0") int uid, @RequestParam(value = "start_commnet", defaultValue = "0") int start_commnet, @RequestParam(value = "start_tag", defaultValue = "0") int start_tag, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        User user = null;
        Map<String, Object> map = new HashMap<>();
        boolean has = false;
        int isFollow = FocusDAO.NO;
        if (uid == 0) {
            user = (User) session.getAttribute("user");
            if (user == null) {
                map.put("no", "no");
                return map;
            } else {
                has = true;
                map.put("no", "yes");
            }
        } else {
            user = userService.get(uid);
            userService.fillMember(user);
            User me = (User) session.getAttribute("user");
            if (focusService.isExites(me.getId(), uid)) {
                if (focusService.isExites(uid, me.getId())) {
                    isFollow = FocusDAO.BOTH;
                } else {
                    isFollow = FocusDAO.YES;
                }
            }
            map.put("no", "yes");
        }
        map.put("isFollow", isFollow);
        PageUtil<Comments> comments = commentsService.listByStatusAndUser(user.getId(), start_commnet, size, 5);
        List<Comments> comments1 = comments.getContent();
        commentsService.fillUser(comments1);
        commentsService.fillArticle(comments1);
        commentsService.fillParent(comments1);
        comments.setContent(comments1);
        List<Article> articles = articleService.list5ByStatusAndUser(0, user.getId());
        articleService.fillCategory(articles);
        articleService.fillUser(articles);
        List<Tag> tags = tagService.listByUser(user.getId());
        PageUtil<Tag> page = tagService.listByCount(start_tag, size, 5);
        map.put("page", page);
        map.put("all", tags);
        map.put("comments", comments);
        map.put("articles", articles);
        map.put("user", user);
        map.put("has", has);
        long from = focusService.sumFrom(user.getId());
        long to = focusService.sumTo(user.getId());
        map.put("from", from);
        map.put("to", to);
        return map;
    }

    @PostMapping(value = "/foreFollow/{uid}")
    public Map<String, Object> follow(@PathVariable("uid") int uid, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        int fromId = user.getId();
        boolean isFollow = focusService.isExites(fromId, uid);
        if (isFollow) {
            focusService.delete(fromId, uid);
            map.put("res", FocusDAO.NO);
        } else {
            Focus focus = new Focus();
            focus.setFrom(fromId);
            focus.setTo(uid);
            focusService.add(focus);
            map.put("res", FocusDAO.YES);
            if (focusService.isExites(uid, fromId)) {
                map.put("res", FocusDAO.BOTH);
            }
        }
        long from = focusService.sumFrom(uid);
        long to = focusService.sumTo(uid);
        map.put("from", from);
        map.put("to", to);
        return map;
    }

    @GetMapping(value = "/foreMsg")
    public Map<String, Object> msg(HttpSession session, @RequestParam(value = "start_msg", defaultValue = "0") int start_msg, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        PageUtil<Comments> comments = commentsService.listByUser(user.getId(), start, size, 5);
        List<Comments> comments1 = comments.getContent();
        commentsService.fillUser(comments1);
        commentsService.fillArticle(comments1);
        comments.setContent(comments1);
        map.put("comments", comments);
        PageUtil<Msg> msgs = msgService.listByReceive(start_msg, size, 5, user.getId());
        List<Msg> msgList = msgs.getContent();
        msgService.fill(msgList);
        msgs.setContent(msgList);
        map.put("msgs", msgs);
        return map;
    }

    @PostMapping(value = "/foreMsg/check/{id}")
    public String msg(@PathVariable("id") int id, @RequestBody JSONObject object) throws Exception {
        int type = Integer.parseInt(object.get("type").toString());
        int status = Integer.parseInt(object.get("status").toString());
        if (type == 1) {
            Comments comments = commentsService.get(id);
            comments.setStatus(status);
            commentsService.update(comments);
            return "ok";
        } else if (type == 2) {
            Msg msg = msgService.get(id);
            msg.setStatus(status);
            msgService.update(msg);
            return "ok";
        }
        return "no";
    }

    @GetMapping(value = "/user/writing")
    public Map<String, Object> writing(HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categoryService.listChildWithUser(user.getId()));
        return map;
    }

    /**
     * 创建文章
     **/
    @PostMapping(value = "/user/writing")
    public String addArticle(@RequestBody JSONObject object, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        // 获取前端传的文章JSON字符串
        Object articleStr = object.get("article");
        // 获取前端传的勾选的标签数组
        String tagStr = object.get("tags").toString();
        // 将JSON对象转换为Java对象，也就是Article实体
        Article article = JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(articleStr)), Article.class);
        // 将JSON数组转为List列表
        List<Integer> ids = JSONObject.parseArray(tagStr, Integer.class);
        // 补充文章信息
        article.setCreateDate(new Date());
        article.setUpdateDate(new Date());
        article.setUid(user.getId());
        // 持久化文章实体，获取ID
        articleService.add(article);
        int aid = article.getId();
        // 持久化文章和标签的关系
        tagArticleService.addByTags(aid, ids);
        String result = "yes";
        return result;
    }

    @GetMapping(value = "/user/articles")
    public Map<String, Object> listArticle(HttpServletRequest httpServletRequest, @RequestParam(value = "key") String key, @RequestParam(value = "cid") int cid, @RequestParam(value = "uid") int uid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size, HttpSession session) throws Exception {
        Map<String, Object> map = new HashMap<>();
        boolean has = false;
        if (uid == 0) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                map.put("no", "no");
                return map;
            } else {
                has = true;
                uid = user.getId();
                map.put("no", "yes");
            }
        } else {
            map.put("no", "yes");
        }
        start = start < 0 ? 0 : start;
        PageUtil<Article> page = null;
        if (key.length() == 0) {
            if (cid == 0) {
                page = articleService.listByStatusAndUser(start, size, 5, 0, uid);
            } else {
                page = articleService.listByStatusAndUserAndCategory(start, size, 5, 0, uid, cid);
            }
            map.put("issearch", false);
        } else {
            if (cid == 0) {
                page = articleService.listByStatusAndUserAndKey(start, size, 5, 0, uid, key);
            } else {
                page = articleService.listByStatusAndUserAndCategoryAndKey(start, size, 5, 0, uid, cid, key);
            }
            map.put("issearch", true);
        }

        List<Article> articles = page.getContent();
        articleService.fillCategory(articles);
        articleService.fillUser(articles);
        page.setContent(articles);
        ServletContext context = httpServletRequest.getServletContext();
        List<Category> categories = new ArrayList<>((List<Category>) context.getAttribute("parentCategories"));
        categoryService.fillParentWithUser(categories, uid);
        map.put("pages", page);
        map.put("has", has);
        map.put("userCategories", categories);
        return map;
    }

    @GetMapping(value = "/user/articles/{aid}")
    public Map<String, Object> getArticle(@PathVariable("aid") int aid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Article article = articleService.get(aid);
        List<Tag> tag = tagService.listByArticle(aid);
        map.put("categories", categoryService.listChild());
        map.put("article", article);
        map.put("tag", tag);
        return map;
    }

    @DeleteMapping(value = "/user/articles/{aid}")
    public String deleteArticle(@PathVariable("aid") int aid) throws Exception {
        articleService.delete(aid);
        return null;
    }

    @PutMapping(value = "/user/articles/{aid}")
    public String updateArticle(@RequestBody JSONObject object) throws Exception {
        String result = "";
        Article article = JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(object.get("article"))), Article.class);
        List<Integer> ids = JSONObject.parseArray(object.get("tags").toString(), Integer.class);
//        article.setCreateDate(new Date());
        article.setUpdateDate(new Date());
        articleService.update(article);
        int aid = article.getId();
        tagArticleService.updateByTags(aid, ids);
        result = "yes";
        return result;
    }


    @PostMapping(value = "/foreAuthorized")
    public String addAuthorized(MultipartFile image, Authorized authorized, HttpServletRequest request, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        authorized.setUid(user.getId());
        authorizedService.add(authorized);
        if (image != null) {
            int id = authorized.getId();
            File imageFolder = new File(request.getServletContext().getRealPath("image/authorized"));
            File file = new File(imageFolder, id + ".jpg");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
            authorizedService.update(authorized);
        }
        return "ok";
    }

    @GetMapping(value = "/user/categories")
    public Map<String, Object> listCate(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        start = start < 0 ? 0 : start;
        PageUtil<Category> page = categoryService.listByUser(start, size, 5, user.getId());
        List<Category> categories = page.getContent();
        categoryService.fillChild(categories);
        page.setContent(categories);
        map.put("page", page);
        map.put("all", categoryService.listParent());
        return map;
    }

    @GetMapping(value = "/user/tags")
    public Map<String, Object> listTag(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        start = start < 0 ? 0 : start;
        PageUtil<Tag> page = tagService.listByUser(start, size, 5, user.getId());
        map.put("page", page);
        return map;
    }

    @DeleteMapping(value = "/user/tags/{id}")
    public String deleteTag(@PathVariable("id") int id) throws Exception {
        tagService.delete(id);
        return null;
    }

    @GetMapping(value = "/user/categories/{id}")
    public Category getCate(@PathVariable("id") int id) throws Exception {
        Category category = categoryService.get(id);
        return category;
    }

    @PostMapping(value = "/user/categories")
    public String addCate(MultipartFile image, Category category, HttpServletRequest request, HttpSession session) throws Exception {
        // 获取用户
        User user = (User) session.getAttribute("user");
        // 开启了 分类个数限制 的开关
        if (Integer.parseInt(optionService.getByKey(OptionDAO.SWITCH_CATE)) == 1) {
            // 获取用户创建的分类个数
            int count = categoryService.countByUser(user.getId());
            // 获取可创建分类最大个数
            int max = Integer.parseInt(optionService.getByKey(OptionDAO.CATE_MAX));
            if (count > max) {
                return "no";
            } else {
                // 可以创建
                category.setUid(user.getId());
                categoryService.add(category);
                if (image != null) {
                    // 上传分类图片，以分类id为图片名
                    int id = category.getId();
                    File imageFolder = new File(request.getServletContext().getRealPath("image/category"));
                    File file = new File(imageFolder, id + ".jpg");
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    image.transferTo(file);
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                }
                return "yes";
            }
        } else {
            category.setUid(user.getId());
            categoryService.add(category);
            if (image != null) {
                int id = category.getId();
                File imageFolder = new File(request.getServletContext().getRealPath("image/category"));
                File file = new File(imageFolder, id + ".jpg");
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                image.transferTo(file);
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img, "jpg", file);
            }
            return "yes";
        }
    }
//            String name = id+".jpg";
//            FtpUtil ftpUtil = new FtpUtil();
//            ftpUtil.uploadFile(name,image.getInputStream(),"/home/ftpuser/blog/image/category");


    @DeleteMapping(value = "/user/categories/{id}")
    public String deleteCate(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        categoryService.delete(id);
        String name = id + ".jpg";
        File imageFolder = new File(request.getServletContext().getRealPath("image/category"));
        File file = new File(imageFolder, name);
        file.delete();
//        FtpUtil ftpUtil = new FtpUtil();
//        ftpUtil.deleteFile("/home/ftpuser/blog/image/category",name);
        return null;
    }

    @PutMapping(value = "/user/categories/{id}")
    public void updateCate(MultipartFile image, Category category, HttpServletRequest request) throws Exception {
        categoryService.update(category);
        if (image != null) {
            int id = category.getId();
            File imageFolder = new File(request.getServletContext().getRealPath("image/category"));
            File file = new File(imageFolder, id + ".jpg");
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
//            String name = id+".jpg";
//            FtpUtil ftpUtil = new FtpUtil();
//            ftpUtil.uploadFile(name,image.getInputStream(),"/home/ftpuser/blog/image/category");
        }
    }

    @PostMapping(value = "/user/tags/search")
    public Map<String, Object> searchTag(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "key") String key, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        List<Tag> all = tagService.listByUser(user.getId());
        List<Tag> likes = tagService.listByKeyLike(key);
        PageUtil<Tag> tags = tagService.listByKey(start, size, 5, key, likes);
        Tag tag = tagService.getByName(key);
        if (tag != null) {
            map.put("tag", tag);
        }
        map.put("all", all);
        map.put("tags", tags);
        map.put("likes", likes);
        return map;
    }

    @PostMapping(value = "/user/tags/{tid}")
    public String addLikeTag(@PathVariable("tid") int tid, HttpSession session) throws Exception {
        // 获取用户
        User user = (User) session.getAttribute("user");
        // 如果已存在用户-标签映射，则移除
        if (userTagService.isExites(tid, user.getId())) {
            userTagService.delete(tid, user.getId());
            return "2";
        } else {
            // 非会员用户添加的感兴趣标签的个数设置上限
            if (user.getMid() == 0){
                int sum = userTagService.sumByUser(user.getId());
                if (sum > 10) {
                    return "3";
                }
            }
            // 添加用户-标签映射
            UserTag userTag = new UserTag();
            userTag.setUid(user.getId());
            userTag.setTid(tid);
            userTagService.add(userTag);
            return "1";
        }
    }

    @GetMapping(value = "/foreLikes")
    public Map<String, Object> likes(HttpSession session, @RequestParam(value = "order") String
            order, @RequestParam(value = "sort") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start,
                                     @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        PageUtil<Article> pages = articleService.listByTags(user.getId(), start, size, 5, order, sort);
        List<Article> articles = pages.getContent();
        articleService.fillCategory(articles);
        articleService.fillUser(articles);
        pages.setContent(articles);
        map.put("pages", pages);
        return map;
    }

    @GetMapping(value = "/foreFocus")
    public Map<String, Object> focus(HttpSession session, @RequestParam(value = "order") String
            order, @RequestParam(value = "sort") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start,
                                     @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        PageUtil<Article> pages = articleService.listByUser(user.getId(), true, start, size, 5, order, sort);
        List<Article> articles = pages.getContent();
        articleService.fillCategory(articles);
        articleService.fillUser(articles);
        pages.setContent(articles);
        map.put("pages", pages);
        return map;
    }

    @PostMapping(value = "/user/tags")
    public Map<String, Object> addTag(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size, HttpSession session, @RequestBody JSONObject object) throws Exception {
        Object tagStr = object.get("tag");
        Tag tag = JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(tagStr)), Tag.class);
        String isadd = object.get("isadd").toString();
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        List<String> syms = CiLin.get_sym(tag.getName(), CiLin.TYPE_SYM);
        if (isadd.equals("no") && !syms.isEmpty() && syms.size() > 0) {
            map.put("res", "has");
            for (String sym : syms) {
                Tag tagsunm = tagService.getByName(sym);
                if (tagsunm != null) {
                    map.put("tag", tagsunm);
                    return map;
                }
            }
        }
        if (Integer.parseInt(optionService.getByKey(OptionDAO.SWITCH_TAG)) == 1) {
            int count = tagService.countByUser(user.getId());
            int max = Integer.parseInt(optionService.getByKey(OptionDAO.TAG_MAX));
            if (count > max) {
                map.put("res", "no");
                return map;
            } else {
                tag.setUid(user.getId());
                tagService.add(tag);
//                    List<Tag> likes = tagService.listByKeyLike(tag.getName());
//                    PageUtil<Tag> tags = tagService.listByKey(start, size, 5, tag.getName(), likes);
//                    map.put("tags", tags);
                map.put("res", "ok");
                return map;
            }
        } else {
            tag.setUid(user.getId());
            tagService.add(tag);
//                List<Tag> likes = tagService.listByKeyLike(tag.getName());
//                PageUtil<Tag> tags = tagService.listByKey(start, size, 5, tag.getName(), likes);
//                map.put("tags", tags);
            map.put("res", "ok");
            return map;
        }
    }

    @GetMapping(value = "/user/writing/tag")
    public Map<String, Object> getArticleTag(@RequestParam("tid") String tidsStr,
                                             @RequestParam(value = "start", defaultValue = "0") int start,
                                             @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        PageUtil<Tag> page = tagService.listByCount(start, size, 5);
        map.put("page", page);
        String[] tids = tidsStr.split(",");
        List<Tag> tags = new ArrayList<>();
        for (String tidStr : tids) {
            if (tidStr != null && tidStr.length() > 0 && NumberUtils.isDigits(tidStr)) {
                int tid = Integer.parseInt(tidStr);
                Tag tag = tagService.get(tid);
                tags.add(tag);
            }
        }
        map.put("all", tags);
        return map;
    }

    @GetMapping(value = "/foreFellow")
    public Map<String, Object> foreFellow(HttpSession session, @RequestParam("uid") int uid,
                                          @RequestParam(value = "start_following", defaultValue = "0") int start_following,
                                          @RequestParam(value = "start_fellow", defaultValue = "0") int start_fellow,
                                          @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        boolean has = false;
        if (uid == 0) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                map.put("no", "no");
                return map;
            } else {
                has = true;
                map.put("no", "yes");
            }
            uid = user.getId();
        } else {
            map.put("no", "yes");
        }
        PageUtil<User> pages_following = focusService.listFollow(start_following, size, 5, uid, true);
        PageUtil<User> pages_fellow = focusService.listFollow(start_fellow, size, 5, uid, false);

        map.put("pages_following", pages_following);
        map.put("pages_fellow", pages_fellow);
        map.put("has", has);
        return map;
    }

    @PostMapping(value = "/user/msg")
    public String addMsg(HttpSession session, @RequestBody Msg msg) throws Exception {
        User user = (User) session.getAttribute("user");
        int sid = user.getId();
        msg.setSid(sid);
        msg.setCreateDate(new Date());
        msgService.add(msg);
        return "ok";
    }
}
