package com.lh.blog.controller.back;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.Notice;
import com.lh.blog.enums.PathEnum;
import com.lh.blog.service.NoticeService;
import com.lh.blog.util.CalendarRandomUtil;
import com.lh.blog.util.ImageUtil;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @GetMapping(value = "/admin/notices")
    public PageUtil<Notice>  list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception{
        start = start<0?0:start;
        PageUtil<Notice> page = noticeService.list(start, size, 5);
        return page;
    }

    @PostMapping(value = "/admin/notices")
    public void addArticle(@RequestBody Notice notice)throws Exception
    {
        notice.setCreateDate(new Date());
        notice.setUpdateDate(new Date());
        noticeService.add(notice);
    }

    @PostMapping(value = "/admin/notices/image")
    public String upload(MultipartFile image)throws Exception
    {
        String path = ImageUtil.uploadArticle(image, PathEnum.Notice);
        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        obj.put("url", "/blog/image/" + path);
        return obj.toString();
    }


    @GetMapping(value = "/admin/notices/{id}")
    public Notice getNotice(@PathVariable("id")int id)
    {
        return noticeService.get(id);
    }

    @PutMapping(value = "/admin/notices/{id}")
    public void updateArticle(@PathVariable("id")int id,@RequestBody Notice notice)
    {
        notice.setUpdateDate(new Date());
        noticeService.update(notice);
    }

    @DeleteMapping(value = "/admin/notices/{id}")
    public String deleteArticle(@PathVariable("id")int id)
    {
        noticeService.delete(id);
        return null;
    }

    @PostMapping(value = "/admin/notices/search")
    public List<Notice> search(@RequestParam("key")String key)
    {
        return noticeService.listByKey(key);
    }

}
