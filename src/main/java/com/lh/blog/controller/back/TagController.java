package com.lh.blog.controller.back;

import com.lh.blog.bean.Option;
import com.lh.blog.bean.Tag;
import com.lh.blog.dao.OptionDAO;
import com.lh.blog.service.OptionService;
import com.lh.blog.service.TagService;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    TagService tagService;
    @Autowired
    OptionService optionService;

    @GetMapping(value = "/admin/tags")
    public PageUtil<Tag> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception
    {
        start = start < 0 ? 0 : start;
        PageUtil<Tag> page =tagService.list(start, size, 5);

        return page;
    }
    @GetMapping(value = "/admin/tags/{id}")
    public Tag get(@PathVariable("id")int id) throws Exception
    {
        Tag tag =  tagService.get(id);
        return tag;
    }

    @PostMapping(value = "/admin/tags")
    public void add(@RequestBody Tag tag) throws Exception
    {
        int count = Integer.parseInt(optionService.getByKey(OptionDAO.TAG_COUNT));
        tag.setCount(count);
        tagService.add(tag);
    }

    @DeleteMapping(value = "/admin/tags/{id}")
    public String delete(@PathVariable("id") int id) throws Exception
    {
        tagService.delete(id);
        return null;
    }

    @PutMapping(value = "/admin/tags/{id}")
    public void update(@RequestBody Tag tag) throws Exception
    {
        tagService.update(tag);
    }

    @PostMapping(value = "/admin/tags/search")
    public List<Tag> searchCategory(@RequestParam(value = "key") String key) throws Exception
    {
        List<Tag> tags =  tagService.listByKey(key);
        return tags;
    }

}
