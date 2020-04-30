package com.lh.blog.controller.fore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.*;
import com.lh.blog.dao.FocusDAO;
import com.lh.blog.dao.OptionDAO;
import com.lh.blog.es.CiLin;
import com.lh.blog.service.*;
import com.lh.blog.util.EncodeUtil;
import com.lh.blog.util.ImageUtil;
import com.lh.blog.util.PageUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 *@author linhao
 *@date 2020/4/30 11:04
 */
@RestController
public class ForeUserController {

    @Autowired
    ArticleService articleService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    MsgService msgService;
    @Autowired
    UserService userService;
    @Autowired
    FocusService focusService;
    @Autowired
    HistoryService historyService;
    @Autowired
    StartService startService;
    @Autowired
    UserTagService userTagService;
    @Autowired
    OptionService optionService;
    @Autowired
    TagArticleService tagArticleService;

    /**
     * 个人设置
     * @param request
     * @param session
     * @param uid
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/foreSetting")
    public Map<String, Object> setting(HttpServletRequest request, HttpSession session, @RequestParam(value = "uid", defaultValue = "0") int uid) throws Exception {
        User user;
        if (uid == 0) {
            user = (User) session.getAttribute("user");
        } else {
            user = userService.get(uid);
        }
        File imageFolder = new File(request.getServletContext().getRealPath("image/profile_user"));
        String files[] = imageFolder.list();
        int num = files.length - 1;
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("num", num);
        return map;
    }

    /**
     * 更改密码
     * @param session
     * @param user
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/foreSetting/{id}")
    public String modifyUser(HttpSession session, @RequestBody User user) throws Exception {
        Map<String, Object> map = EncodeUtil.encode(user.getPassword());
        user.setSalt(map.get("salt").toString());
        user.setPassword(map.get("pass").toString());
        userService.update(user);
        session.setAttribute("user", userService.get(user.getId()));
        return "ok";
    }

    /**
     * 更改个人设置
     * @param session
     * @param object
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/foreSetting")
    public String uploadUser(HttpSession session, @RequestBody JSONObject object) throws Exception {
        User user = (User) session.getAttribute("user");
        String folder = "/image/profile_user/" + Integer.parseInt(object.get("num").toString()) + ".jpg";
        user.setImg(folder);
        userService.update(user);
        return "ok";
    }

    /**
     * 个人历史记录
     * @param session
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
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

    /**
     * 删除历史记录
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/foreHistories/{id}")
    public String deleteHistory(@PathVariable("id") int id) throws Exception {
        historyService.delete(id);
        return null;
    }

    /**
     * 个人收藏记录
     * @param session
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/foreStart")
    public PageUtil<Article> listStart(HttpSession session, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        start = start < 0 ? 0 : start;
        User user = (User) session.getAttribute("user");
        PageUtil<Article> page = articleService.listByUserAndStart(user.getId(), start, size, 5);
        List<Article> articles = page.getContent();
        articleService.fillCategory(articles);
        page.setContent(articles);
        return page;
    }

    /**
     * 删除个人收藏
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/foreStart/{id}")
    public String unStart(@PathVariable("id") int id) throws Exception {
        startService.delete(id);
        return null;
    }


    /**
     * 获取关注列表
     * @param session
     * @param uid
     * @param start_following
     * @param start_fellow
     * @param size
     * @return
     * @throws Exception
     */
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
    /**
     * 获取所有私信
     * @param session
     * @param start_msg
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/foreMsg")
    public Map<String, Object> foreMsg(HttpSession session, @RequestParam(value = "start_msg", defaultValue = "0") int start_msg, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
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


    /**
     * 审核私信或评论
     * @param id
     * @param object
     * @return
     * @throws Exception
     */
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

    /**
     * 写文章
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/user/writing")
    public Map<String, Object> writing(HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categoryService.listChildWithUser(user.getId()));
        return map;
    }

    /**
     * 选择标签，实时更新
     * @param tidsStr
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
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


    /**
     * 搜索标签
     * @param start
     * @param size
     * @param key
     * @param session
     * @return
     * @throws Exception
     */
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


    /**
     * 创建自定义标签
     * @param start
     * @param size
     * @param session
     * @param object
     * @return
     * @throws Exception
     */
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
                map.put("res", "ok");
                return map;
            }
        } else {
            tag.setUid(user.getId());
            tagService.add(tag);
            map.put("res", "ok");
            return map;
        }
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

    /**
     * 获取所有文章
     * @param httpServletRequest
     * @param key
     * @param cid
     * @param uid
     * @param start
     * @param size
     * @param session
     * @return
     * @throws Exception
     */
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

    /**
     * 编辑文章
     * @param aid
     * @return
     * @throws Exception
     */
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

    /**
     * 删除文章
     * @param aid
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/user/articles/{aid}")
    public String deleteArticle(@PathVariable("aid") int aid) throws Exception {
        articleService.delete(aid);
        return null;
    }

    /**
     * 更新文章
     * @param object
     * @return
     * @throws Exception
     */
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
    /**
     * 获取自己的所有分类
     * @param start
     * @param size
     * @param session
     * @return
     * @throws Exception
     */
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

    /**
     * 编辑分类
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/user/categories/{id}")
    public Category getCate(@PathVariable("id") int id) throws Exception {
        Category category = categoryService.get(id);
        return category;
    }

    /**
     * 创建个人分类
     * @param image
     * @param category
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
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
                    String path = request.getServletContext().getRealPath("image/category");
                    ImageUtil.uploadCate(category.getId(), path, image);
                }
                return "yes";
            }
        } else {
            category.setUid(user.getId());
            categoryService.add(category);
            if (image != null) {
                int id = category.getId();
                String path = request.getServletContext().getRealPath("image/category");
                ImageUtil.uploadCate(id, path, image);
            }
            return "yes";
        }
    }

    /**
     * 删除个人分类
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/user/categories/{id}")
    public String deleteCate(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        categoryService.delete(id);
        String path = request.getServletContext().getRealPath("image/category");
        ImageUtil.deleteCate(id, path);
        return null;
    }

    /**
     * 更新分类
     * @param image
     * @param category
     * @param request
     * @throws Exception
     */
    @PutMapping(value = "/user/categories/{id}")
    public void updateCate(MultipartFile image, Category category, HttpServletRequest request) throws Exception {
        categoryService.update(category);
        if (image != null) {
            String path = request.getServletContext().getRealPath("image/category");
            ImageUtil.uploadCate(category.getId(), path, image);
        }
    }

    /**
     * 获取自己创建的所有标签
     * @param start
     * @param size
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/user/tags")
    public Map<String, Object> listTag(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        start = start < 0 ? 0 : start;
        PageUtil<Tag> page = tagService.listByUser(start, size, 5, user.getId());
        map.put("page", page);
        return map;
    }

    /**
     * 删除自定义标签
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/user/tags/{id}")
    public String deleteTag(@PathVariable("id") int id) throws Exception {
        tagService.delete(id);
        return null;
    }
}
