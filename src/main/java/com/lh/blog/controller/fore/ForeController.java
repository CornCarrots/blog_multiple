package com.lh.blog.controller.fore;

import com.lh.blog.bean.*;
import com.lh.blog.service.*;
import com.lh.blog.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 *@author linhao
 *@date 2019/1/30
 */
@RestController
public class ForeController {

    private static Logger logger = LoggerFactory.getLogger(ForeController.class);

    @Autowired
    ArticleService articleService;
    @Autowired
    CarouselService carouselService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ManagerService managerService;
    @Autowired
    OptionService optionService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    LinkService linkService;
    @Autowired
    MessageService messageService;

    /**
     * 首页
     * @param start
     * @param size
     * @return
     */
    @GetMapping("/foreHome")
    public Result home(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "4") int size) {
        try {
            Map<String, Object> map = new HashMap<>();
            // 获取5篇首页文章
            PageUtil<Article> pages = articleService.listForShow(start, size, 5, 0);
            List<Article> articles = pages.getContent();
            articleService.fillCategory(articles);
            articleService.fillUser(articles);
            pages.setContent(articles);
            map.put("pages", pages);
            return Result.success(map);
        }catch (Exception e){
            logger.error("进入首页异常", e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("首页"));
        }
    }

    /**
     * 搜索 不打印日志
     * @param key
     * @param order
     * @param sort
     * @param start
     * @param size
     * @return
     */
    @GetMapping(value = "/foreSearch")
    public Result search(@RequestParam("key") String key, @RequestParam(value = "order", defaultValue = "id") String order, @RequestParam(value = "sort", defaultValue = "false") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        try {
            Map<String, Object> map = new HashMap<>();
            // 搜索文章
            PageUtil<Article> pages = articleService.listByKey(key, start, size, 5, order, sort);
            List<Article> articles = pages.getContent();
            articleService.fillCategory(articles);
            articleService.fillUser(articles);
            pages.setContent(articles);
            map.put("pages", pages);
            map.put("key", key);
            return Result.success(map);
        }catch (Exception e){
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("搜索"));
        }
    }

    /**
     * 分类
     * @param cid
     * @param order
     * @param sort
     * @param start
     * @param size
     * @return
     */
    @GetMapping(value = "/foreCategory/{cid}")
    public Result category(@PathVariable("cid") int cid, @RequestParam(value = "order") String order, @RequestParam(value = "sort") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        try {
            Map<String, Object> map = new HashMap<>();
            // 分类信息
            Category category = categoryService.get(cid);
            categoryService.fillChild(category);
            // 通过分类获取文章
            PageUtil<Article> pages = articleService.listByCategory(cid, start, size, 5, order, sort);
            List<Article> articles = pages.getContent();
            articleService.fillCategory(articles);
            articleService.fillUser(articles);
            pages.setContent(articles);
            map.put("pages", pages);
            map.put("category", category);
            return Result.success(map);
        }catch (Exception e){
            logger.error("进入分类 {} 异常", cid, e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("分类"));
        }
    }

    /**
     * 获取公告
     * @param nid
     * @return
     */
    @GetMapping(value = "/foreNotice/{nid}")
    public Result notice(@PathVariable("nid") int nid) {
        try {
            Notice notice = noticeService.get(nid);
            return Result.success(notice);
        }catch (Exception e){
            logger.error("获取公告：{} 异常", nid, e);
            return Result.error(CodeMsg.GET_FAIL.fillArgs("公告"));
        }
    }

    /**
     * 公告列表
     * @param start
     * @param size
     * @return
     */
    @GetMapping(value = "/foreNotice")
    public Result noticeList(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) {
        try {
            start = start < 0 ? 0 : start;
            PageUtil<Notice> page = noticeService.list(start, size, 5);
            return Result.success(page);
        }catch (Exception e){
            logger.error("进入公告异常", e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("公告"));
        }
    }

    /**
     * 标签
     * @param tid
     * @param order
     * @param sort
     * @param start
     * @param size
     * @return
     */
    @GetMapping(value = "/foreTag/{tid}")
    public Result tag(@PathVariable("tid") int tid, @RequestParam(value = "order") String order, @RequestParam(value = "sort") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        try {
            Map<String, Object> map = new HashMap<>();
            // 标签信息
            Tag tag = tagService.get(tid);
            // 通过标签获取文章
            PageUtil<Article> pages = articleService.listByTag(tid, start, size, 5, order, sort);
            List<Article> articles = pages.getContent();
            articleService.fillCategory(articles);
            articleService.fillUser(articles);
            pages.setContent(articles);
            map.put("pages", pages);
            map.put("tag", tag);
            return Result.success(map);
        }catch (Exception e){
            logger.error("进入标签：{} 异常", tid, e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("标签"));
        }
    }

//
//    @PostMapping(value = "/foreLink")
//    public void applyLink(@RequestBody Link link) throws Exception {
//        link.setCreateDate(new Date());
//        linkService.add(link);
//    }

    /**
     * 建议反馈
     * @param message
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/foreMessage")
    public Result addMessage(@RequestBody Message message, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            message.setCreateDate(new Date());
            message.setUid(user.getId());
            messageService.add(message);
            logger.info("用户：{} 反馈成功", user.getId());
            return Result.success(CodeMsg.SEND_SUCCESS);
        }catch (Exception e){
            logger.error("用户：{} 反馈失败", user.getId(), e);
            return Result.success(CodeMsg.SEND_ERROR);
        }
    }

    /**
     * 感兴趣标签的文章
     * @param session
     * @param order
     * @param sort
     * @param start
     * @param size
     * @return
     */
    @GetMapping(value = "/foreLikes")
    public Result likes(HttpSession session, @RequestParam(value = "order") String
            order, @RequestParam(value = "sort") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start,
                                     @RequestParam(value = "size", defaultValue = "5") int size) {
        User user = (User) session.getAttribute("user");
        try {
            Map<String, Object> map = new HashMap<>();
            PageUtil<Article> pages = articleService.listByTags(user.getId(), start, size, 5, order, sort);
            List<Article> articles = pages.getContent();
            articleService.fillCategory(articles);
            articleService.fillUser(articles);
            pages.setContent(articles);
            map.put("pages", pages);
            return Result.success(map);
        }catch (Exception e){
            logger.error("进入用户 {} 感兴趣的页面异常", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("感兴趣"));
        }
    }

    /**
     * 关注的人的文章
     * @param session
     * @param order
     * @param sort
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/foreFocus")
    public Result focus(HttpSession session, @RequestParam(value = "order") String
            order, @RequestParam(value = "sort") Boolean sort, @RequestParam(value = "start", defaultValue = "0") int start,
                                     @RequestParam(value = "size", defaultValue = "5") int size) {
        User user = (User) session.getAttribute("user");
        try {
            Map<String, Object> map = new HashMap<>();
            PageUtil<Article> pages = articleService.listByUser(user.getId(), true, start, size, 5, order, sort);
            List<Article> articles = pages.getContent();
            articleService.fillCategory(articles);
            articleService.fillUser(articles);
            pages.setContent(articles);
            map.put("pages", pages);
            return Result.success(map);
        }catch (Exception e){
            logger.error("进入用户 {} 关注的页面异常", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("关注"));
        }
    }
}
