package com.lh.blog.service;

import com.lh.blog.bean.Like;
import com.lh.blog.dao.LikeDAO;
import com.lh.blog.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@CacheConfig(cacheNames = "likes")
public class LikeService {
    @Autowired
    LikeDAO dao;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

//    @CacheEvict(allEntries = true)
    public void add(Like bean){
        dao.save(bean);
    }

//    @CacheEvict(allEntries = true)
    public void delete(int id){
        dao.delete(id);
    }

//    @CacheEvict(allEntries = true)
    public void update(Like bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Like get(int id){
        return dao.findOne(id);
    }

    public void deleteArticle(int aid,int uid){
        LikeService likeService = SpringContextUtils.getBean(LikeService.class);
        likeService.delete(likeService.getArticle(aid,uid).getId());
    }

    public void deleteByAid(int aid){
        dao.deleteAllByAcidAndType(aid, LikeDAO.TYPE_ARTICLE);
    }

    public void deleteComment(int cid,int uid){
        LikeService likeService = SpringContextUtils.getBean(LikeService.class);
        likeService.delete(likeService.getComment(cid,uid).getId());
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Like getArticle(int aid,int uid){
        return dao.findAllByAcidAndUidAndType(aid,uid,LikeDAO.TYPE_ARTICLE);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Like getComment(int cid,int uid){
        return dao.findAllByAcidAndUidAndType(cid,uid,LikeDAO.TYPE_COMMENT);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Like> list(){
        return dao.findAll(sort);
    }
}
