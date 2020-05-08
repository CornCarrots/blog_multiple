package com.lh.blog.controller.fore;

import com.lh.blog.annotation.AddScore;
import com.lh.blog.bean.*;
import com.lh.blog.dao.LikeDAO;
import com.lh.blog.dao.OptionDAO;
import com.lh.blog.exception.GlobalException;
import com.lh.blog.service.*;
import com.lh.blog.util.CodeMsg;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author linhao
 *@date 2020/4/30 10:56
 */
@RestController
public class ForeArticleController {

    private static Logger logger = LoggerFactory.getLogger(ForeArticleController.class);

    @Autowired
    ArticleService articleService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    LikeService likeService;
    @Autowired
    StartService startService;
    @Autowired
    HistoryService historyService;
    @Autowired
    OptionService optionService;
    @Autowired
    UserService userService;

    /**
     * 获取文章
     * @param aid
     * @param session
     * @param start
     * @param size
     * @return
     */
    @GetMapping("/foreArticle/{aid}")
    public Result article(@PathVariable("aid") int aid, HttpSession session, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        try {
            Map<String, Object> map = new HashMap<>();

            // 处理文章逻辑
            Article article = articleService.get(aid);
            // 访问次数+1
            article.setView(article.getView() + 1);
            articleService.update(article);
            articleService.fillArticle(article);
            logger.info("[访问文章] 处理文章 {} 逻辑", aid);
            map.put("article", article);

            // 保留历史逻辑...
            map.put("key", optionService.getByKey(OptionDAO.WEB_KEY));

            // 处理评论逻辑，分页
            PageUtil<Comments> page = commentsService.listByParent(aid, start, size, 5);
            List<Comments> comments = page.getContent();
            commentsService.fillComments(comments);
            commentsService.fillChild(comments);
            map.put("pages", page);
            logger.info("[访问文章] 处理评论逻辑，获取评论：{}条", comments.size());

            // 处理用户的特殊逻辑
            User user = (User) session.getAttribute("user");
            boolean has = false;
            if (user != null) {
                // 保留历史逻辑 判断用户有没有回复神秘内容
                has = true;
//              has = commentsService.checkUser(user.getId(), aid);

                // 修正评论的点赞逻辑,简单重构了代码
                commentsService.fillLike(comments, user.getId());

                // 修正文章的点赞收藏逻辑
                articleService.fillLikeAndStart(article, user.getId());

                // 历史记录逻辑
                historyService.init(aid, user.getId());
                logger.info("[访问文章] 处理用户 {} 逻辑", user.getId());
            }else {
                logger.info("[访问文章] 用户未登录");
            }
            map.put("has", has);
            map.put("user", user);
            logger.info("[访问文章] 获取文章：{} 成功", aid);
            return Result.success(map);
        }catch (Exception e){
            logger.error("[访问文章] 获取文章：{} 失败", aid, e);
            return Result.error(CodeMsg.GET_FAIL.fillArgs("文章"));
        }
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteComment/{id}")
    public Result deleteComment(@PathVariable("id") int id) {
        try {
            commentsService.delete(id);
            logger.info("[评论文章] 删除评论：{} 成功", id);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            logger.error("[评论文章] 删除评论：{} 失败", id, e);
            return Result.error(CodeMsg.DELETE_FAIL.fillArgs("评论"));
        }
    }


    /**
     * 发表评论
     **/
    @PostMapping(value = "/foreCommitComment")
    @AddScore
    public Result commit(@RequestBody Comments comments,@RequestParam("uid") int uid, HttpSession session) {
        User user = userService.get(uid);
        try {
            // 当前评论时间
            comments.setCreateDate(new Date());
            // 处理用户逻辑，根据业务规则，评论的用户加积分
            user.setScore(user.getScore() + 2);
            userService.update(user);
            session.setAttribute("user", userService.get(user.getId()));
            logger.info("[评论文章] 处理用户:{} 逻辑", user.getId());

            // 再处理评论，进行持久化
            comments.setUid(user.getId());
            commentsService.add(comments);
            logger.info("[评论文章] 处理评论:{} 逻辑", comments.getId());

            // 最后处理文章
            Article article = articleService.get(comments.getAid());
            article.setComment(article.getComment() + 1);
            articleService.update(article);
            logger.info("[评论文章] 处理文章:{} 逻辑", article.getId());

            logger.info("[评论文章] 评论文章成功");
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            logger.error("[评论文章] 用户：{}，评论文章失败", user.getId(), e);
            return Result.error(CodeMsg.COMMIT_ERROR);
        }
    }

    /**
     * 收藏文章
     * @param article
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/startArticle")
    public Result startArticle(@RequestBody Article article, HttpSession session){
        User user = (User) session.getAttribute("user");
        boolean isStart = article.isHasStart();
        try {
            if (isStart) {
                startService.delete(article.getId(), user.getId());
                article.setStart(article.getStart() - 1);
                articleService.update(article);
                logger.info("[收藏文章] 用户：{}，取消收藏文章：{} 成功", user.getId(), article.getId());
                return Result.success(CodeMsg.UNSTART_SUCCESS);
            } else {
                Start start = new Start();
                start.setAid(article.getId());
                start.setUid(user.getId());
                startService.add(start);
                article.setStart(article.getStart() + 1);
                articleService.update(article);
                logger.info("[收藏文章] 用户：{}，收藏文章：{} 成功", user.getId(), article.getId());
                return Result.success(CodeMsg.START_SUCCESS);
            }
        }catch (Exception e){
            logger.info("[收藏文章] 用户：{}，收藏文章状态：{} 失败", user.getId(), isStart, e);
            if (!isStart) {
                return Result.error(CodeMsg.START_ERROR);
            }
            else {
                return Result.error(CodeMsg.UNSTART_ERROR);
            }
        }
    }

    /**
     * 点赞文章
     * @param article
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/likeArticle")
    public Result likeArticle(@RequestBody Article article, HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean isLike = article.isHasLike();
        try {
            if (isLike) {
                likeService.deleteArticle(article.getId(), user.getId());
                article.setLike(article.getLike() - 1);
                articleService.update(article);
                logger.info("[点赞文章] 用户：{}，取消点赞文章：{} 成功", user.getId(), article.getId());
                return Result.success(CodeMsg.UNLIKE_SUCCESS);
            } else {
                Like like = new Like();
                like.setAcid(article.getId());
                like.setUid(user.getId());
                like.setType(LikeDAO.TYPE_ARTICLE);
                likeService.add(like);
                article.setLike(article.getLike() + 1);
                articleService.update(article);
                logger.info("[点赞文章] 用户：{}，点赞文章：{} 成功", user.getId(), article.getId());
                return Result.success(CodeMsg.LIKE_SUCCESS);
            }
        }catch (Exception e){
            logger.info("[点赞文章] 用户：{}，点赞文章状态：{} 失败", user.getId(), isLike, e);
            if (!isLike) {
                return Result.error(CodeMsg.LIKE_ERROR);
            }
            else {
                return Result.error(CodeMsg.UNLIKE_ERROR);
            }
        }
    }

    /**
     * 点赞评论
     * @param comments
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/likeComment")
    public Result likeComment(@RequestBody Comments comments, HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean isLike = comments.isHasLike();
        try {
            if (isLike) {
                likeService.deleteComment(comments.getId(), user.getId());
                comments.setLike(comments.getLike() - 1);
                commentsService.update(comments);
                logger.info("[点赞评论] 用户：{}，取消点赞评论：{} 成功", user.getId(), comments.getId());
                return Result.success(CodeMsg.UNLIKE_COMMENT_SUCCESS);
            } else {
                Like like = new Like();
                like.setAcid(comments.getId());
                like.setUid(user.getId());
                like.setType(LikeDAO.TYPE_COMMENT);
                likeService.add(like);
                comments.setLike(comments.getLike() + 1);
                commentsService.update(comments);
                logger.info("[点赞评论] 用户：{}，点赞评论：{} 成功", user.getId(), comments.getId());
                return Result.success(CodeMsg.LIKE_COMMENT_SUCCESS);
            }
        }catch (Exception e){
            logger.info("[点赞评论] 用户：{}，点赞评论状态：{} 失败", user.getId(), isLike);
            if (!isLike) {
                throw new GlobalException(CodeMsg.LIKE_COMMENT_ERROR);
            }
            else {
                throw new GlobalException(CodeMsg.UNLIKE_COMMENT_ERROR);
            }
        }
    }

    /**
     * 赞赏用户
     * @param uid
     * @return
     */
    @PostMapping(value = "/addChicken/{uid}")
    @AddScore
    public Result addChicken(@PathVariable("uid") int uid, HttpSession session) {
        try {
            User user = userService.get(uid);
            user.setScore(user.getScore() + 5);
            userService.update(user);
            logger.info("[赞赏] 用户:{} 成功", uid);
            return Result.success(CodeMsg.CHICKEN_SUCCESS);
        }catch (Exception e){
            logger.info("[赞赏] 用户:{} 失败", uid);
            throw new GlobalException(CodeMsg.CHICKEN_ERROR);
        }
    }
}
