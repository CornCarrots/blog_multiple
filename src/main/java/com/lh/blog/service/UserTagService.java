package com.lh.blog.service;

import com.lh.blog.bean.UserTag;
import com.lh.blog.dao.UserTagDAO;
import com.lh.blog.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "userTags")
public class UserTagService {
    @Autowired
    UserTagDAO dao;

    Sort sort = new Sort(Sort.Direction.DESC, "id");

    //    @CacheEvict(allEntries = true)
    @Caching(evict = {
            @CacheEvict(value = "tags", key = "'listByUser '+ #bean.uid"),
            @CacheEvict(cacheNames = "userTags", allEntries = true)
    })
    public void add(UserTag bean) {
        dao.save(bean);
    }

    //    @CacheEvict(allEntries = true)
    @Caching(evict = {
            @CacheEvict(value = "tags", key = "'listByUser '+ #bean.uid"),
            @CacheEvict(cacheNames = "userTags", allEntries = true)
    })
    public void delete(UserTag bean) {
        dao.delete(bean.getId());
    }

//    @CacheEvict(allEntries = true)
    @Caching(evict = {
            @CacheEvict(value = "tags", key = "'listByUser '+ #bean.uid"),
            @CacheEvict(cacheNames = "userTags", allEntries = true)
    })
    public void update(UserTag bean) {
        dao.save(bean);
    }

    //    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public UserTag get(int id) {
        return dao.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<UserTag> list() {
        return dao.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<UserTag> listByUser(int uid) {
        return dao.findAllByUid(uid, sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public int sumByUser(int uid) {
        return dao.countAllByUid(uid, sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<UserTag> listByTag(int tid) {
        return dao.findAllByTid(tid, sort);
    }

    @Caching(evict = {
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "userTags", allEntries = true)
    })
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTag(int tid){
        dao.deleteByTid(tid);
    }

    public boolean isExites(int tid, int uid) {
        List<UserTag> list = dao.findAllByTidAndUid(tid, uid, sort);
        return list != null && list.size() > 0;
    }

    public void delete(int tid, int uid) {
        UserTagService userTagService = SpringContextUtils.getBean(UserTagService.class);
        List<UserTag> list = dao.findAllByTidAndUid(tid, uid, sort);
        if (list != null && list.size() > 0) {
            UserTag userTag = list.get(0);
            userTagService.delete(userTag);
        }
    }
}
