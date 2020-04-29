package com.lh.blog.service;

import com.lh.blog.bean.Article;
import com.lh.blog.bean.History;
import com.lh.blog.bean.User;
import com.lh.blog.dao.HistoryDAO;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.RestPageImpl;
import com.lh.blog.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
//@CacheConfig(cacheNames = "histories")
public class HistoryService {
    @Autowired
    HistoryDAO historyDAO;
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    Sort sort = new Sort(Sort.Direction.ASC, "id");

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<History> list() {
        return historyDAO.findAll(sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<History> listByUser(int uid,int start, int size, int number) {
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = historyDAO.findAllByUid(uid,pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());

        return new PageUtil<>(page, number);
    }

//    @CacheEvict(allEntries = true)
    public void add(History operation)
    {
        historyDAO.save(operation);
    }

//    @CacheEvict(allEntries = true)
    public void delete(int id)
    {
        historyDAO.delete(id);
    }

//    @CacheEvict(allEntries = true)
    public void update(History operation)
    {
        historyDAO.save(operation);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public History get(int id)
    {
        return historyDAO.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public long sum(){return historyDAO.count();}

    public void init(int aid,int uid){
        HistoryService historyService = SpringContextUtils.getBean(HistoryService.class);
        History history = new History();
        history.setAid(aid);
        history.setUid(uid);
        history.setCreateDate(new Date());
        historyService.add(history);
        List<History> list = historyService.list();
        if(historyService.sum()>20)
            historyService.delete(list.get(0).getId());
    }

    public void fill(List<History> histories)
    {
        HistoryService historyService = SpringContextUtils.getBean(HistoryService.class);
        for (History history:
             histories) {
            historyService.fill(history);
        }
    }

    public void fill(History history){
        User user = userService.get(history.getUid());
        if (user!=null)
            history.setUser(user);
        Article article = articleService.get(history.getAid());
        if(article!=null)
            history.setArticle(article);
    }
}
