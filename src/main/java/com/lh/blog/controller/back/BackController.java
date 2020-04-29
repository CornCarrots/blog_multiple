package com.lh.blog.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.Article;
import com.lh.blog.bean.Message;
import com.lh.blog.dao.ArticleDAO;
import com.lh.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BackController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    MessageService messageService;

    @GetMapping(value = "/admin/home")
    public Map<String,Object> home () throws Exception {
        Map<String,Object> object = new HashMap<>();
        List<Article> articles = articleService.list();
        int article_sum = articles.size();
        int view_sum = articleService.viewSum(articles);
        long user_sum = userService.sum();
        int comment_sum = articleService.commentSum(articles);
        int publish_sum = articleService.listByType(ArticleDAO.TYPE_PUBLISH).size();
        int draft_sum = articleService.listByType(ArticleDAO.TYPE_DRAFT).size();
        int category_sum = categoryService.list().size();
        long tag_sum = tagService.sum();
        List<Message> messages = messageService.list();
        object.put("article_sum",article_sum);
        object.put("view_sum",view_sum);
        object.put("user_sum",user_sum);
        object.put("comment_sum",comment_sum);
        object.put("publish_sum",publish_sum);
        object.put("draft_sum",draft_sum);
        object.put("category_sum",category_sum);
        object.put("tag_sum",tag_sum);
        object.put("messages",messages);
        object.put("articles",articles);
        return object;
    }
}
