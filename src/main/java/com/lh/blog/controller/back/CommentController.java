package com.lh.blog.controller.back;

import com.lh.blog.bean.Comments;
import com.lh.blog.service.CommentsService;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentsService commentsService;

    @GetMapping("/admin/comments")
    public PageUtil<Comments> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception{
        start = start<0?0:start;
        PageUtil<Comments> page =commentsService.list(start, size, 5);
        List<Comments> comments = page.getContent();
        commentsService.fillArticle(comments);
        commentsService.fillUser(comments);
        page.setContent(comments);
        return page;
    }

    @PutMapping(value = "/admin/comments/check/{id}")
    public void check(@PathVariable("id")int id)throws Exception
    {
        Comments comments = commentsService.get(id);
        comments.setStatus(0);
        commentsService.update(comments);
    }

    @PutMapping(value = "/admin/comments/unCheck/{id}")
    public void unCheck(@PathVariable("id")int id)throws Exception
    {
        Comments comments = commentsService.get(id);
        comments.setStatus(1);
        commentsService.update(comments);
    }

    @DeleteMapping(value = "/admin/comments/{id}")
    public String delete(@PathVariable("id")int id)throws Exception
    {
        commentsService.delete(id);
        return null;
    }


    @GetMapping(value = "/admin/comments/{id}")
    public Comments get(@PathVariable("id")int id)
    {
        Comments comments = commentsService.get(id);
        commentsService.fillArticle(comments);
        commentsService.fillUser(comments);
        return comments;
    }


    @PostMapping(value = "/admin/comments/search")
    public List<Comments> searchMessage(@RequestParam("key")String key)
    {
        List<Comments> comments = commentsService.listByKey(key);
        commentsService.fillArticle(comments);
        commentsService.fillUser(comments);
        return comments;
    }
}
