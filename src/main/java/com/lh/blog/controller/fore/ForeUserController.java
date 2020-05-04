package com.lh.blog.controller.fore;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.annotation.Check;
import com.lh.blog.bean.*;
import com.lh.blog.dao.OptionDAO;
import com.lh.blog.es.CiLin;
import com.lh.blog.service.*;
import com.lh.blog.util.*;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *@author linhao
 *@date 2020/4/30 11:04
 */
@RestController
public class ForeUserController {

    private static Logger logger = LoggerFactory.getLogger(ForeUserController.class);

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
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/foreSetting")
    public Result setting(HttpSession session, HttpServletRequest request) {
        // 从session拿用户
        User user = (User) session.getAttribute("user");
        try {
            // 拿图片的数量
            String path = request.getServletContext().getRealPath("image/profile_user");
            int num = userService.getImgNum(path);
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("num", num);
            return Result.success(map);
        }catch (Exception e){
            logger.error("[访问设置页面] 异常, uid:{}", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("用户设置"));
        }
    }

    /**
     * 更改个人设置
     * @param session
     * @param user
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/foreSetting/{id}")
    public Result modifyUser(HttpSession session, @RequestBody @Valid User user) {
        try {
            // 加密
            Map<String, Object> map = EncodeUtil.encode(user.getPassword());
            user.setSalt(map.get("salt").toString());
            user.setPassword(map.get("pass").toString());
            userService.update(user);
            session.setAttribute("user", userService.get(user.getId()));
            logger.info("[设置用户成功] uid:{}", user.getId());
            return Result.success(CodeMsg.MODIFY_USER_SUCCESS);
        }catch (Exception e){
            logger.error("[设置用户失败] uid:{}", user.getId(), e);
            return Result.error(CodeMsg.MODIFY_USER_ERROR);
        }
    }

    /**
     * 更改头像
     * @param session
     * @param object
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/foreSetting")
    @Check(params = "num")
    public Result uploadUser(@RequestBody JSONObject object,HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            String folder = "/image/profile_user/" + Integer.parseInt(object.get("num").toString()) + ".jpg";
            user.setImg(folder);
            userService.update(user);
            session.setAttribute("user", userService.get(user.getId()));
            logger.info("[设置用户头像成功] uid:{}", user.getId());
            return Result.success(CodeMsg.MODIFY_USER_SUCCESS);
        }catch (Exception e){
            logger.error("[设置用户头像失败] uid:{}", user.getId(), e);
            return Result.error(CodeMsg.MODIFY_USER_ERROR);
        }
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
    public Result history(HttpSession session, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) {
        User user = (User) session.getAttribute("user");
        try {
            start = start < 0 ? 0 : start;
            PageUtil<History> page = historyService.listByUser(user.getId(), start, size, 5);
            List<History> histories = page.getContent();
            historyService.fill(histories);
            return Result.success(page);
        }catch (Exception e){
            logger.error("[访问历史记录] 异常, uid:{}", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("历史记录"));
        }
    }

    /**
     * 删除历史记录
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/foreHistories/{id}")
    public Result deleteHistory(@PathVariable("id") int id) {
        try {
            historyService.delete(id);
            logger.info("[历史记录删除成功] hid:{}", id);
            return Result.success(CodeMsg.DELETE_HISTORY_SUCCESS);
        }catch (Exception e){
            logger.error("[历史记录删除异常] hid:{}", id, e);
            return Result.error(CodeMsg.DELETE_HISTORY_ERROR);
        }
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
    public Result listStart(HttpSession session, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) {
        User user = (User) session.getAttribute("user");
        try {
            start = start < 0 ? 0 : start;
            PageUtil<Article> page = articleService.listByUserAndStart(user.getId(), start, size, 5);
            List<Article> articles = page.getContent();
            articleService.fillCategory(articles);
            return Result.success(page);
        }catch (Exception e){
            logger.error("[访问收藏夹] 异常 uid:{}", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("收藏夹"));
        }
    }

    /**
     * 删除个人收藏
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/foreStart/{id}")
    public Result unStart(@PathVariable("id") int id) {
        try {
            startService.delete(id);
            logger.info("[收藏删除成功] hid:{}", id);
            return Result.success(CodeMsg.DELETE_START_SUCCESS);
        }catch (Exception e){
            logger.error("[收藏删除异常] sid:{}", id, e);
            return Result.error(CodeMsg.DELETE_START_ERROR);
        }
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
    public Result foreFellow(HttpSession session, @RequestParam("uid") int uid,
                                          @RequestParam(value = "start_following", defaultValue = "0") int start_following,
                                          @RequestParam(value = "start_fellow", defaultValue = "0") int start_fellow,
                                          @RequestParam(value = "size", defaultValue = "7") int size) {
        try {
            Map<String, Object> map = new HashMap<>();
            boolean has = false;
            // 判断是不是自己访问
            if (uid == 0) {
                User user = (User) session.getAttribute("user");
                // TODO 好像没必要，待确定
                if (user == null) {
                    return Result.error(CodeMsg.USER_EMPTY);
                } else {
                    has = true;
                }
                uid = user.getId();
            }
            map.put("has", has);

            // 关注的人
            PageUtil<User> pages_following = focusService.listFollow(start_following, size, 5, uid, true);
            // 粉丝
            PageUtil<User> pages_fellow = focusService.listFollow(start_fellow, size, 5, uid, false);

            map.put("pages_following", pages_following);
            map.put("pages_fellow", pages_fellow);
            return Result.success(map);
        }catch (Exception e){
            logger.error("[访问关注列表] 异常 uid:{}", uid, e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("关注列表"));
        }
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
    public Result foreMsg(HttpSession session, @RequestParam(value = "start_msg", defaultValue = "0") int start_msg, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        User user = (User) session.getAttribute("user");
        try {
            Map<String, Object> map = new HashMap<>();
            // 获取评论
            PageUtil<Comments> comments = commentsService.listByUser(user.getId(), start, size, 5);
            List<Comments> comments1 = comments.getContent();
            commentsService.fillComments(comments1);
            map.put("comments", comments);
            // 获取私信
            PageUtil<Msg> msgs = msgService.listByReceive(start_msg, size, 5, user.getId());
            List<Msg> msgList = msgs.getContent();
            msgService.fill(msgList);
            msgs.setContent(msgList);
            map.put("msgs", msgs);
            return Result.success(map);
        }catch (Exception e){
            logger.error("[访问私信列表] 异常 uid:{}", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("私信列表"));
        }
    }

    /**
     * 审核私信或评论
     * @param id
     * @param object
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/foreMsg/check/{id}")
    @Check(params = {"type:IsNum","status:IsNum"})
    public Result msg(@RequestBody JSONObject object, @PathVariable("id") int id) {
        int type = Integer.parseInt(object.get("type").toString());
        int status = Integer.parseInt(object.get("status").toString());
        try {
            if (type == 1) {
                Comments comments = commentsService.get(id);
                comments.setStatus(status);
                commentsService.update(comments);
            } else if (type == 2) {
                Msg msg = msgService.get(id);
                msg.setStatus(status);
                msgService.update(msg);
            }
            logger.info("[私信审核成功] mid:{}, type:{}", id, type);
            return Result.error(CodeMsg.CHECK_MSG_SUCCESS);
        }catch (Exception e){
            logger.error("[私信审核异常] mid:{}, type:{}", id, type, e);
            return Result.error(CodeMsg.CHECK_MSG_ERROR);
        }
    }

    /**
     * 写文章
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/user/writing")
    public Result writing(HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            List<Category> categories = categoryService.listChildWithUser(user.getId());
            logger.error("[访问写文章页面] 成功 uid:{}", user.getId());
            return Result.success(categories);
        }catch (Exception e){
            logger.error("[访问写文章页面] 异常 uid:{}", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("写文章"));
        }
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
    public Result getArticleTag(@RequestParam("tid") String tidsStr,
                                             @RequestParam(value = "start", defaultValue = "0") int start,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
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
            return Result.success(map);
        }catch (Exception e){
            logger.error("[为文章添加标签] 异常", e);
            return Result.error(CodeMsg.TAG_ADD_ERROR);
        }
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
    public Result searchTag(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "key") String key, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Map<String, Object> map = new HashMap<>();
            // 感兴趣标签
            List<Tag> all = tagService.listByUser(user.getId());
            // 近义标签
            List<Tag> likes = tagService.listByKeyLike(key);
            // 关联标签
            PageUtil<Tag> tags = tagService.listByKey(start, size, 5, key, likes);
            // 同名标签
            Tag tag = tagService.getByName(key);
            if (tag != null) {
                map.put("tag", tag);
            }
            map.put("all", all);
            map.put("tags", tags);
            map.put("likes", likes);
            logger.info("[搜索标签] 成功 自定义标签:{},近义标签:{},关联标签:{},同名标签:{}", all.size(), likes.size(), tags.getContent().size(), tag != null);
            return Result.success(map);
        }catch (Exception e){
            logger.error("[搜索标签] 异常", e);
            return Result.error(CodeMsg.TAG_SEARCH_ERROR);
        }
    }

    @PostMapping(value = "/user/writing/image")
    public Map<String, Object>  upload(MultipartFile image, HttpServletRequest request)throws Exception
    {
        Map<String, Object> msg = new HashMap<>();
        if(image!=null)
        {
            // 当前日期作为文件夹名
            Date date = new Date();
            String path=new SimpleDateFormat("yyyy/MM/dd/").format(date);
            // 使用随机数生成文件名
            String now = CalendarRandomUtil.getRandom();
            String name = now+".jpg";
            File imageFolder= new File(request.getServletContext().getRealPath("image/article/"+path));
            File file = new File(imageFolder,now+".jpg");
//            String newFileName = request.getContextPath()+"/image/article/"+file.getName();
            //如果不存在,创建文件夹
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
            logger.error("[上传文章图片] 成功，path:{}, name:{}", path, name);
            msg.put("error", 0);
            msg.put("url", "/blog/image/article/"+path+name);
            //            File f = new File(path);
//            if(!f.exists()){
//                f.mkdirs();
//            }
            //            FtpUtil ftpUtil = new FtpUtil();
//            ftpUtil.uploadFile(name,image.getInputStream(),"/home/ftpuser/blog/image/article/"+path);
        }
        else {
            logger.error("[上传文章图片] 失败，图片不存在");
            msg.put("error", 1);
            msg.put("message", "上传失败！");
        }
        return msg;
    }

    /**
     * 创建自定义标签
     * @param session
     * @param object
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/tags")
    public Result addTag(HttpSession session, @RequestBody JSONObject object) {
        User user = (User) session.getAttribute("user");
        try {
            // 获取标签内容
            Object tagStr = object.get("tag");
            Tag tag = JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(tagStr)), Tag.class);
            // 判断是否添加
            String isadd = "yes";
            if (ObjectUtil.isNotNull(object.get("isadd"))){
                isadd = object.get("isadd").toString();
            }
            // 获取用户
            List<String> syms = CiLin.get_sym(tag.getName(), CiLin.TYPE_SYM);
            // 首次添加 且存在关联词
            if ("no".equals(isadd) && !syms.isEmpty()) {
                String symname = syms.get(0);
                Tag tagsym = tagService.getByName(symname);
                logger.info("[自定义标签] 失败 uid:{}, 存在：{}", user.getId(), symname);
                return Result.info(CodeMsg.TAG_INSERT_SYM.fillArgs(tagsym.getName()));
            }
            // 首次添加 不存在关联词；仍然添加
            if (Integer.parseInt(optionService.getByKey(OptionDAO.SWITCH_TAG)) == 1) {
                int count = tagService.countByUser(user.getId());
                int max = Integer.parseInt(optionService.getByKey(OptionDAO.TAG_MAX));
                if (count >= max) {
                    logger.info("[自定义标签] 失败 uid:{}, 个数:{}, 限制:{}", user.getId(), count, max);
                    return Result.error(CodeMsg.TAG_INSERT_OVER.fillArgs(max));
                } else {
                    tag.setUid(user.getId());
                    if (tagService.getByName(tag.getName()) != null) {
                        logger.info("[自定义标签] 失败 uid:{}, 已存在：{}", user.getId(), tag.getName());
                        return Result.info(CodeMsg.TAG_INSERT_SYM.fillArgs(tag.getName()));
                    }
                    tagService.add(tag);
                    logger.info("[自定义标签] 成功 uid:{}, name：{}", user.getId(), tag.getName());
                    return Result.success(CodeMsg.TAG_INSERT_SUCCESS);
                }
            } else {
                tag.setUid(user.getId());
                if (tagService.getByName(tag.getName()) != null) {
                    logger.info("[自定义标签] 失败 uid:{}, 已存在：{}", user.getId(), tag.getName());
                    return Result.info(CodeMsg.TAG_INSERT_SYM.fillArgs(tag.getName()));
                }
                tagService.add(tag);
                logger.info("[自定义标签] 成功 uid:{}, name：{}", user.getId(), tag.getName());
                return Result.success(CodeMsg.TAG_INSERT_SUCCESS);
            }
        }catch (Exception e){
            logger.error("[自定义标签] 异常", e);
            return Result.error(CodeMsg.TAG_INSERT_ERROR);
        }
    }

    /**
     * 创建文章
     **/
    @PostMapping(value = "/user/writing")
    public Result addArticle(@RequestBody JSONObject object, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            // 文章逻辑
            // 获取前端传的文章JSON字符串
            Object articleStr = object.get("article");
            // 将JSON对象转换为Java对象，也就是Article实体
            Article article = JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(articleStr)), Article.class);
            // 补充文章信息
            article.setCreateDate(new Date());
            article.setUpdateDate(new Date());
            article.setUid(user.getId());
            // 持久化文章实体，获取ID
            articleService.add(article);
            logger.info("[创建文章] 处理文章逻辑 aid:{}", article.getId());
            // 标签逻辑
            // 获取前端传的勾选的标签数组
            String tagStr = object.get("tags").toString();
            // 将JSON数组转为List列表
            List<Integer> ids = JSONObject.parseArray(tagStr, Integer.class);
            int aid = article.getId();
            // 持久化文章和标签的关系
            tagArticleService.addByTags(aid, ids);
            logger.info("[创建文章] 处理标签逻辑 tids:{}", ids.size());
            logger.info("[创建文章] 成功 uid:{} aid:{}", user.getId(), article.getId());
            return Result.success(CodeMsg.ARTICLE_PUBLISH_SUCCESS);
        }catch (Exception e){
            logger.error("[创建文章] 异常 uid:{}", user.getId(), e);
            return Result.error(CodeMsg.ARTICLE_PUBLISH_ERROR);
        }
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
    public Result listArticle(HttpServletRequest httpServletRequest, @RequestParam(value = "key") String key, @RequestParam(value = "cid") int cid, @RequestParam(value = "uid") int uid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size, HttpSession session) {
        try {
            Map<String, Object> map = new HashMap<>();
            boolean has = false;
            // 自己的页面
            if (uid == 0) {
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    return Result.error(CodeMsg.USER_EMPTY);
                } else {
                    has = true;
                    uid = user.getId();
                }
            }
            map.put("has", has);
            // 文章逻辑
            start = start < 0 ? 0 : start;
            PageUtil<Article> page;
            // 是否搜索
            map.put("issearch", StrUtil.isNotEmpty(key));
            // 获取文章
            page = articleService.listByStatusAndUserAndCategory(start, size, 5, 0, uid, cid, key, has);
            List<Article> articles = page.getContent();
            articleService.fillArticle(articles);
            map.put("pages", page);

            // 分类逻辑
            ServletContext context = httpServletRequest.getServletContext();
            List<Category> categories = new ArrayList<>((List<Category>) context.getAttribute("parentCategories"));
            categoryService.fillParentWithUser(categories, uid);
            map.put("userCategories", categories);
            return Result.success(map);
        }catch (Exception e){
            logger.error("访问文章列表失败", e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("文章列表"));
        }
    }

    /**
     * 编辑文章
     * @param aid
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/user/articles/{aid}")
    public Result getArticle(@PathVariable("aid") int aid) {
        try {
            Map<String, Object> map = new HashMap<>();
            Article article = articleService.get(aid);
            List<Tag> tag = tagService.listByArticle(aid);
            map.put("categories", categoryService.listChild());
            map.put("article", article);
            map.put("tag", tag);
            return Result.success(map);
        }catch (Exception e){
            logger.error("[获取文章]失败 aid:{}", aid, e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("编辑文章"));
        }
    }

    /**
     * 删除文章
     * @param aid
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/user/articles/{aid}")
    public Result deleteArticle(@PathVariable("aid") int aid) {
        try {
            articleService.delete(aid);
            logger.info("[删除文章]成功 aid:{}", aid);
            return Result.error(CodeMsg.ARTICLE_DELETE_SUCCESS);
        }catch (Exception e){
            logger.error("[删除文章]异常 aid:{}", aid, e);
            return Result.error(CodeMsg.ARTICLE_DELETE_ERROR);
        }
    }

    /**
     * 更新文章
     * @param object
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/user/articles/{aid}")
    public Result updateArticle(@RequestBody JSONObject object, @PathVariable("aid") int aid) {
        try {
            // 文章逻辑
            Article article = JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(object.get("article"))), Article.class);
            article.setUpdateDate(new Date());
            articleService.update(article);

            // 标签逻辑
            List<Integer> ids = JSONObject.parseArray(object.get("tags").toString(), Integer.class);
            tagArticleService.updateByTags(aid, ids);
            logger.info("[更新文章] 成功 aid:{}", aid);
            return Result.success(CodeMsg.ARTICLE_UPDATE_SUCCESS);
        }catch (Exception e){
            logger.error("[更新文章] 失败 aid:{}", aid, e);
            return Result.error(CodeMsg.ARTICLE_UPDATE_ERROR);
        }
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
    public Result listCate(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            Map<String, Object> map = new HashMap<>();
            start = start < 0 ? 0 : start;
            PageUtil<Category> page = categoryService.listByUser(start, size, 5, user.getId());
            List<Category> categories = page.getContent();
            categoryService.fillChild(categories);
            map.put("page", page);

            // 主分类 用于编辑分类
            map.put("all", categoryService.listParent());
            return Result.success(map);
        }catch (Exception e){
            logger.error("[访问分类页面] 异常 uid:{}", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("分类列表"));
        }
    }

    /**
     * 编辑分类
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/user/categories/{id}")
    public Result getCate(@PathVariable("id") int id) {
        try {
            Category category = categoryService.get(id);
            return Result.success(category);
        }catch (Exception e){
            logger.error("[获取分类异常] id:{}", id, e);
            return Result.error(CodeMsg.CATE_GET_ERROR);
        }
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
    public Result addCate(MultipartFile image,@Valid Category category, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            // 开启了 分类个数限制 的开关
            if (Integer.parseInt(optionService.getByKey(OptionDAO.SWITCH_CATE)) == 1) {
                // 获取用户创建的分类个数
                int count = categoryService.countByUser(user.getId());
                // 获取可创建分类最大个数
                int max = Integer.parseInt(optionService.getByKey(OptionDAO.CATE_MAX));
                if (count >= max) {
                    logger.info("[创建分类] 超过限制 uid:{} count:{} max:{}", user.getId(), count, max);
                    return Result.error(CodeMsg.CATE_INSERT_OVER.fillArgs(max));
                } else {
                    // 可以创建
                    category.setUid(user.getId());
                    categoryService.add(category);
                    if (image != null) {
                        // 上传分类图片，以分类id为图片名
                        String path = request.getServletContext().getRealPath("image/category");
                        ImageUtil.uploadCate(category.getId(), path, image);
                    }
                    logger.info("[创建分类] 成功 uid:{} cid:{}", user.getId(), category.getId());
                    return Result.success(CodeMsg.CATE_INSERT_SUCCESS);
                }
            } else {
                category.setUid(user.getId());
                categoryService.add(category);
                if (image != null) {
                    int id = category.getId();
                    String path = request.getServletContext().getRealPath("image/category");
                    ImageUtil.uploadCate(id, path, image);
                }
                logger.info("[创建分类] 成功 uid:{} cid:{}", user.getId(), category.getId());
                return Result.success(CodeMsg.CATE_INSERT_SUCCESS);
            }
        }catch (IOException e){
            logger.error("[创建分类] 图片异常 ", e);
            return Result.error(CodeMsg.PIC_UPLOAD_ERROR.fillArgs("分类"));
        } catch (Exception e){
            logger.error("[创建分类] 异常 uid:{}", user.getId(), e);
            return Result.error(CodeMsg.CATE_INSERT_ERROR);
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
    public Result deleteCate(@PathVariable("id") int id, HttpServletRequest request) {
        try {
            categoryService.delete(id);
            // 删除分类图片
            String path = request.getServletContext().getRealPath("image/category");
            ImageUtil.deleteCate(id, path);
            logger.info("[删除分类] 成功 id:{}", id);
            return Result.error(CodeMsg.CATE_DELETE_SUCCESS);

        }catch (Exception e){
            logger.error("[删除分类] 异常 id:{}", id, e);
            return Result.error(CodeMsg.CATE_DELETE_ERROR);
        }
    }

    /**
     * 更新分类
     * @param image
     * @param category
     * @param request
     * @throws Exception
     */
    @PutMapping(value = "/user/categories/{id}")
    public Result updateCate(@PathVariable("id") int id, MultipartFile image, @Valid Category category, HttpServletRequest request) {
        try {
            categoryService.update(category);
            if (image != null) {
                String path = request.getServletContext().getRealPath("image/category");
                ImageUtil.uploadCate(category.getId(), path, image);
            }
            logger.info("[更新分类] 成功 id:{}", id);
            return Result.success(CodeMsg.CATE_UPDATE_SUCCESS);
        }catch (IOException e){
            logger.error("[更新分类] 图片异常 id:{}", id, e);
            return Result.error(CodeMsg.PIC_UPLOAD_ERROR.fillArgs("分类"));
        }catch (Exception e){
            logger.error("[更新分类] 异常 id:{}", id, e);
            return Result.error(CodeMsg.CATE_UPDATE_ERROR);
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
    public Result listTag(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            start = start < 0 ? 0 : start;
            PageUtil<Tag> page = tagService.listByUser(start, size, 5, user.getId());
            return Result.success(page);
        }catch (Exception e){
            logger.error("[访问标签列表页面] 异常 uid:{}", user.getId(), e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("标签列表"));
        }
    }

    /**
     * 删除自定义标签
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/user/tags/{id}")
    public Result deleteTag(@PathVariable("id") int id) {
        try {
            tagService.delete(id);
            logger.info("[删除标签] 成功 id:{}", id);
            return Result.success(CodeMsg.TAG_DELETE_SUCCESS);
        }catch (Exception e){
            logger.error("[删除标签] 异常 id:{}", id, e);
            return Result.error(CodeMsg.TAG_DELETE_ERROR);
        }
    }
}
