package com.lh.blog.controller.back;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.Article;
import com.lh.blog.bean.Tag;
import com.lh.blog.bean.User;
import com.lh.blog.service.ArticleService;
import com.lh.blog.service.CategoryService;
import com.lh.blog.service.TagArticleService;
import com.lh.blog.service.TagService;
import com.lh.blog.util.CalendarRandomUtil;
import com.lh.blog.util.ImageUtil;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    TagArticleService tagArticleService;

    @GetMapping(value = "/admin/articles")
    public PageUtil<Article> listArticle(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        start = start < 0 ? 0 : start;
        PageUtil<Article> page = articleService.list(start, size, 5);
        List<Article> articles = page.getContent();
        articleService.fillCategory(articles);
        return page;
    }

    @GetMapping(value = "/admin/writing")
    public Map<String, Object> writing() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categoryService.listChild());
        map.put("tags", tagService.list());
        return map;
    }

    @PostMapping(value = "/admin/writing")
    public String add(@RequestBody JSONObject object, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        String result = "";
        Article article = JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(object.get("article"))), Article.class);
        List<Integer> ids = JSONObject.parseArray(object.get("tags").toString(), Integer.class);
        article.setCreateDate(new Date());
        article.setUpdateDate(new Date());
        article.setUid(user.getId());
        articleService.add(article);
        int aid = article.getId();
        tagArticleService.addByTags(aid, ids);
        result = "yes";
        return result;
    }

    @GetMapping(value = "/admin/articles/{aid}")
    public Map<String, Object> get(@PathVariable("aid") int aid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Article article = articleService.get(aid);
        List<Tag> tag = tagService.listByArticle(aid);
        map.put("categories", categoryService.listChild());
        map.put("tags", tagService.list());
        map.put("article", article);
        map.put("tag", tag);
        return map;
    }

    @DeleteMapping(value = "/admin/articles/{aid}")
    public String delete(@PathVariable("aid") int aid) throws Exception {
        articleService.delete(aid);
        return null;
    }
    @PutMapping(value = "/admin/articles/{aid}")
    public String update(@PathVariable("aid") int aid) throws Exception {
        String result = "";
        Article article = articleService.get(aid);
        article.setUpdateDate(new Date());
        int status = article.getStatus();
        article.setStatus(status == 0 ? 1 : 0);
        articleService.update(article);
        result = "yes";
        return result;
    }
//    @PutMapping(value = "/admin/articles/{aid}")
//    public String update(@RequestBody JSONObject object) throws Exception {
//        String result = "";
//        Article article = JSONObject.toJavaObject(JSONObject.parseObject(JSON.toJSONString(object.get("article"))), Article.class);
//        List<Integer> ids = JSONObject.parseArray(object.get("tags").toString(), Integer.class);
////        article.setCreateDate(new Date());
//        article.setUpdateDate(new Date());
//        articleService.update(article);
//        int aid = article.getId();
//        tagArticleService.updateByTags(aid, ids);
//        result = "yes";
//        return result;
//    }

    @PostMapping(value = "/admin/articles/search")
    public List<Article> search(@RequestParam(value = "key") String key) throws Exception
    {
        List<Article> articles =  articleService.listByKey(key);
        articleService.fillCategory(articles);
        return articles;
    }

}
