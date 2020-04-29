package com.lh.blog.service;
import com.lh.blog.bean.Start;
import com.lh.blog.dao.StartDAO;
import com.lh.blog.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@CacheConfig(cacheNames = "starts")
public class StartService {
    @Autowired
    StartDAO dao;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "starts", allEntries = true)
    })
    public void add(Start bean){
        dao.save(bean);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "starts", allEntries = true)
    })
    public void delete(int id){
        dao.delete(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "starts", allEntries = true)
    })
    public void delete(int aid,int uid){
        StartService startService = SpringContextUtils.getBean(StartService.class);
        startService.delete(startService.get(aid,uid).getId());
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "starts", allEntries = true)
    })
    public void update(Start bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Start get(int id){
        return dao.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Start get(int aid,int uid){
        return dao.findAllByAidAndUid(aid,uid);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Start> list(){
        return dao.findAll(sort);
    }
    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Start> listByUser(int uid){
        return dao.findAllByUid(uid, sort);
    }
}
