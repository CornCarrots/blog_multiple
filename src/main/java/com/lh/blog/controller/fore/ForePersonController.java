package com.lh.blog.controller.fore;

import com.lh.blog.bean.*;
import com.lh.blog.dao.FocusDAO;
import com.lh.blog.dao.OptionDAO;
import com.lh.blog.service.*;
import com.lh.blog.util.CodeMsg;
import com.lh.blog.util.ImageUtil;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author linhao
 *@date 2020/4/30 12:39
 */
@RestController
public class ForePersonController {

    private static Logger logger = LoggerFactory.getLogger(ForePersonController.class);

    @Autowired
    UserService userService;
    @Autowired
    FocusService focusService;
    @Autowired
    UserTagService userTagService;
    @Autowired
    MsgService msgService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;
    @Autowired
    AuthorizedService authorizedService;
    @Autowired
    OptionService optionService;
    /**
     * 用户主页
     * @param session
     * @param uid
     * @param start_commnet
     * @param start_tag
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/foreUser")
    public Result user(HttpSession session, @RequestParam(value = "uid", defaultValue = "0") int uid, @RequestParam(value = "start_commnet", defaultValue = "0") int start_commnet, @RequestParam(value = "start_tag", defaultValue = "0") int start_tag, @RequestParam(value = "size", defaultValue = "5") int size) {
        try {
            User user;
            Map<String, Object> map = new HashMap<>();
            boolean ismyself = false;
            int isFollow = FocusDAO.NO;

            // 判断是访问自己的还是别人的
            user = (User) session.getAttribute("user");
            if (uid == 0 || (user!= null && uid == user.getId())) {
                if (uid == 0 && user == null){
                    return Result.error(CodeMsg.USER_EMPTY);
                }
                ismyself = true;
                uid = user.getId();
            }

            // 访问他人页面，需要处理 关注 逻辑
            else {
                user = userService.get(uid);
                userService.fillMember(user);
                User me = (User) session.getAttribute("user");
                if (me != null){
                    if (focusService.isExites(me.getId(), uid)) {
                        if (focusService.isExites(uid, me.getId())) {
                            isFollow = FocusDAO.BOTH;
                        } else {
                            isFollow = FocusDAO.YES;
                        }
                    }
                    logger.info("[访问个人页面] 处理关注逻辑,isFollow:{}", isFollow);
                }

            }
            logger.info("[访问个人页面] uid: {}", uid);
            map.put("isFollow", isFollow);

            // 处理访问用户的数据
            // 处理评论逻辑
            PageUtil<Comments> comments = commentsService.listByStatusAndUser(uid, start_commnet, size, 5);
            List<Comments> comments1 = comments.getContent();
            commentsService.fillComments(comments1);
            commentsService.fillParent(comments1);
            // 处理文章逻辑
            List<Article> articles = articleService.list5ByStatusAndUser(0, uid, ismyself);
            articleService.fillArticle(articles);
            // 处理标签逻辑
            List<Tag> tags = tagService.listByUser(uid);
            PageUtil<Tag> page = tagService.listByCount(start_tag, size, 5);
            // 处理关注和粉丝
            long from = focusService.sumFrom(uid);
            long to = focusService.sumTo(uid);
            logger.info("[访问个人页面] 评论条数:{}，文章数:{}，个人标签数:{},粉丝:{}，关注：{}",
                    comments1.size(), articles.size(), tags.size(), from, to);

            map.put("page", page);
            map.put("all", tags);
            map.put("comments", comments);
            map.put("articles", articles);
            map.put("user", user);
            map.put("ismyself", ismyself);
            map.put("from", from);
            map.put("to", to);
            return Result.success(map);
        }catch (Exception e){
            logger.error("[访问个人页面异常]", e);
            return Result.error(CodeMsg.SERVER_PAGE_ERROR.fillArgs("用户"));
        }
    }

    /**
     * 关注用户
     * @param uid
     * @param session
     * @return
     */
    @PostMapping(value = "/foreFollow/{uid}")
    public Result follow(@PathVariable("uid") int uid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        int fromId = user.getId();
        boolean isFollow = focusService.isExites(fromId, uid);
        try {
            Map<String, Object> map = new HashMap<>();
            if (isFollow) {
                focusService.delete(fromId, uid);
                logger.info("[取消关注用户成功] from:{} to:{}", fromId, uid);
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
                logger.info("[关注用户成功] from:{} to:{}", fromId, uid);
            }
            long from = focusService.sumFrom(uid);
            long to = focusService.sumTo(uid);
            map.put("from", from);
            map.put("to", to);
            return Result.success(map);
        }catch (Exception e){
            if (!isFollow) {
                logger.error("[关注用户失败] from:{} to:{}", fromId, uid, e);
                return Result.error(CodeMsg.FOLLOW_ERROR);
            }else {
                logger.error("[取消关注用户失败] from:{} to:{}", fromId, uid, e);
                return Result.error(CodeMsg.UNFOLLOW_ERROR);
            }
        }
    }

    /**
     * 发送私信
     * @param session
     * @param msg
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/msg")
    public Result addMsg(HttpSession session, @RequestBody @Valid Msg msg) {
        User user = (User) session.getAttribute("user");
        int sid = user.getId();
        try {
            msg.setSid(sid);
            msg.setCreateDate(new Date());
            msgService.add(msg);
            logger.info("[发送私信] 成功, uid:{}", sid);
            return Result.success(CodeMsg.SENDMSG_SUCCESS);
        }catch (Exception e){
            logger.error("[发送私信] 失败, uid:{}", sid, e);
            return Result.error(CodeMsg.SENDMSG_ERROR);
        }
    }

    /**
     * 添加感兴趣标签
     * @param tid
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/tags/{tid}")
    public Result addLikeTag(@PathVariable("tid") int tid, HttpSession session) {
        // 获取用户
        User user = (User) session.getAttribute("user");
        boolean isExist = userTagService.isExites(tid, user.getId());
        try {
            // 如果已存在用户-标签映射，则移除
            if (isExist) {
                userTagService.delete(tid, user.getId());
                logger.info("[删除感兴趣标签] 成功, uid:{}, tid:{}", user.getId(), tid);
                return Result.success(CodeMsg.DELETE_INTERESTTAG_SUCCESS);
            } else {
                // 非会员用户添加的感兴趣标签的个数设置上限
                if (user.getMid() == 0){
                    int sum = userTagService.sumByUser(user.getId());
                    int over = Integer.parseInt(optionService.getByKey(OptionDAO.INTERESTTAG_OVER));
                    logger.info("[添加感兴趣标签] 校验非会员个数, uid:{}, sum:{}, over:{}", user.getId(), sum, over);
                    if (sum >= over) {
                        return Result.error(CodeMsg.ADD_INTERESTTAG_OVER.fillArgs(over));
                    }
                }
                // 添加用户-标签映射
                UserTag userTag = new UserTag();
                userTag.setUid(user.getId());
                userTag.setTid(tid);
                userTagService.add(userTag);
                logger.info("[添加感兴趣标签] 成功, uid:{}, tid:{}", user.getId(), tid);
                return Result.success(CodeMsg.ADD_INTERESTTAG_SUCCESS);
            }
        }catch (Exception e){
            if (!isExist){
                logger.error("[添加感兴趣标签] 失败, uid:{}, tid:{}", user.getId(), tid, e);
                return Result.error(CodeMsg.ADD_INTERESTTAG_ERROR);
            }
            else {
                logger.error("[删除感兴趣标签] 失败, uid:{}, tid:{}", user.getId(), tid, e);
                return Result.error(CodeMsg.DELETE_INTERESTTAG_ERROR);
            }
        }
    }

    /**
     * 申请权限
     * @param image
     * @param authorized
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/foreAuthorized")
    public Result addAuthorized(MultipartFile image, Authorized authorized, HttpServletRequest request, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        try {
            authorized.setUid(user.getId());
            authorizedService.add(authorized);
            if (image != null) {
                int id = authorized.getId();
                String path = request.getServletContext().getRealPath("image/authorized");
                ImageUtil.uploadCate(id, path, image);
                authorizedService.update(authorized);
            }
            logger.info("[申请认证] 成功 uid:{}", user.getId());
            return Result.success(CodeMsg.APPLY_AUTHORIZED_SUCCESS);
        }catch (Exception e){
            logger.error("[申请认证] 失败 uid:{}", user.getId() ,e);
            return Result.error(CodeMsg.APPLY_AUTHORIZED_ERROR);
        }
    }
}
