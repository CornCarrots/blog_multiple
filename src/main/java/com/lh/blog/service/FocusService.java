package com.lh.blog.service;

import com.lh.blog.bean.Article;
import com.lh.blog.bean.Focus;
import com.lh.blog.bean.History;
import com.lh.blog.bean.User;
import com.lh.blog.dao.FocusDAO;
import com.lh.blog.dao.HistoryDAO;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.RestPageImpl;
import com.lh.blog.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "focus")
public class FocusService {
    @Autowired
    FocusDAO focusDAO;
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    Sort sort = new Sort(Sort.Direction.ASC, "id");

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Focus> list() {
        return focusDAO.findAll(sort);
    }

    public List<Focus> list(int from, int to) {
        return focusDAO.findAllByFromAndTo(from, to, sort);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "focus", allEntries = true)
    })
    public void add(Focus focus)
    {
        focusDAO.save(focus);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "focus", allEntries = true)
    })
    @CacheEvict(allEntries = true)
    public void delete(int id)
    {
        focusDAO.delete(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "focus", allEntries = true)
    })
    @CacheEvict(allEntries = true)
    public void update(Focus focus)
    {
        focusDAO.save(focus);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Focus get(int id)
    {
        return focusDAO.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public long sum(){return focusDAO.count();}

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public long sumTo(int uid){return focusDAO.countAllByFrom(uid);}

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public long sumFrom(int uid){return focusDAO.countAllByTo(uid);}

    public boolean isExites(int from, int to){
        List<Focus> list = focusDAO.findAllByFromAndTo(from, to, sort);
        return list != null && list.size() > 0;
    }

    public void delete(int from, int to){
        FocusService focusService = SpringContextUtils.getBean(FocusService.class);
        List<Focus> list = focusService.list(from, to);
        if (list != null && list.size() > 0){
            Focus focus = list.get(0);
            int id = focus.getId();
            focusService.delete(id);
        }
    }

    public void fill(List<Focus> focusList)
    {
        FocusService focusService = SpringContextUtils.getBean(FocusService.class);
        for (Focus focus: focusList) {
            focusService.fill(focus);
        }
    }

    public void fill(Focus focus){
        User from = userService.get(focus.getFrom());
        focus.setFromUser(from);
        User to = userService.get(focus.getTo());
        focus.setToUser(to);
    }

    public PageUtil<User> listFollow(int start,int size,int number, int uid ,boolean follow){
        List<Focus> focusList = null;
        if (follow){
            focusList = focusDAO.findAllByFrom(uid, sort);
        }else {
            focusList = focusDAO.findAllByTo(uid, sort);
        }
        List<Integer> ids = new ArrayList<>();
        for (Focus focus: focusList) {
            if (follow) {
                ids.add(focus.getTo());
            }else {
                ids.add(focus.getFrom());
            }
        }
        return userService.listByIds(start, size, number, ids);
    }

    public List<User> listFollow(int uid ,boolean follow){
        List<Focus> focusList = null;
        if (follow){
            focusList = focusDAO.findAllByFrom(uid, sort);
        }else {
            focusList = focusDAO.findAllByTo(uid, sort);
        }
        List<Integer> ids = new ArrayList<>();
        for (Focus focus: focusList) {
            if (follow) {
                ids.add(focus.getTo());
            }else {
                ids.add(focus.getFrom());
            }
        }
        return userService.listByIds(ids);
    }
}
