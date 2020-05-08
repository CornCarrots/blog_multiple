package com.lh.blog.service;
import com.lh.blog.bean.Tag;
import com.lh.blog.bean.TagArticle;
import com.lh.blog.dao.TagArticleDAO;
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
@CacheConfig(cacheNames = "tagArticles")
public class TagArticleService {
    @Autowired
    TagArticleDAO dao;
    @Autowired
    TagService tagService;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "tagArticles", allEntries = true)
    })
    public void add(TagArticle bean){
        dao.save(bean);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "tagArticles", allEntries = true)
    })
    public void delete(int id){
        dao.delete(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "tagArticles", allEntries = true)
    })
    public void deleteByTag(int tid){
        dao.deleteAllByTid(tid);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "tagArticles", allEntries = true)
    })
    public void deleteByAid(int aid){
        dao.deleteAllByAid(aid);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "tagArticles", allEntries = true)
    })
    public void update(TagArticle bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public TagArticle get(int id){
        return dao.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<TagArticle> list(){
        return dao.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<TagArticle> listByArticle(int aid){
        return dao.findAllByAid(aid,sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<TagArticle> listByTag(int tid){
        return dao.findAllByTid(tid,sort);
    }

    public void addByTags(int aid,List<Integer> tids){
        TagArticleService tagArticleService = SpringContextUtils.getBean(TagArticleService.class);
        for (int tid:
             tids) {
            TagArticle tagArticle = new TagArticle();
            tagArticle.setAid(aid);
            tagArticle.setTid(tid);
            tagArticleService.add(tagArticle);
            Tag tag = tagService.get(tid);
            int count = tag.getCount();
            tag.setCount(count + 1);
            tagService.update(tag);
        }
    }

    public void updateByTags(int aid,List<Integer> tids){
        TagArticleService tagArticleService = SpringContextUtils.getBean(TagArticleService.class);
        for (TagArticle tagArticle :listByArticle(aid)) {
            int tid = tagArticle.getTid();
            Tag tag = tagService.get(tid);
            int count = tag.getCount();
            tag.setCount(count - 1);
            tagArticleService.delete(tagArticle.getId());
        }
        for (int tid:
                tids) {
            Tag tag = tagService.get(tid);
            int count = tag.getCount();
            tag.setCount(count + 1);
            TagArticle tagArticle = new TagArticle();
            tagArticle.setAid(aid);
            tagArticle.setTid(tid);
            tagArticleService.add(tagArticle);
        }
    }
}
