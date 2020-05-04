package com.lh.blog.service;
import cn.hutool.core.collection.CollUtil;
import com.lh.blog.bean.Tag;
import com.lh.blog.bean.TagArticle;
import com.lh.blog.bean.UserTag;
import com.lh.blog.dao.OptionDAO;
import com.lh.blog.dao.TagDAO;
import com.lh.blog.es.CiLin;
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
import java.util.List;
@Service
@CacheConfig(cacheNames = "tags")
public class TagService {
    @Autowired
    TagDAO dao;
    @Autowired
    TagArticleService tagArticleService;
    @Autowired
    UserTagService userTagService;
    @Autowired
    OptionService optionService;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public long sum(){
        return dao.count();
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "tags", allEntries = true)
    })
    public void add(Tag bean){
        dao.save(bean);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "tags", allEntries = true)
    })
    public void delete(int id){
        dao.delete(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "tags", allEntries = true)
    })
    public void update(Tag bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Tag get(int id){
        return dao.findOne(id);
    }
    public Tag getByName(String name){
        List<Tag> tags = dao.findTagByNameEquals(name);
        if (!tags.isEmpty() && tags.size() > 0){
            return tags.get(0);
        }
        return null;
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Tag> list(){
        return dao.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Tag> listByCount(int count){
        return dao.findAllByCountGreaterThanEqual(count, sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Tag> listByCount(int start, int size, int number) {
        Pageable pageable = new PageRequest(start, size, sort);
        int count = Integer.parseInt(optionService.getByKey(OptionDAO.TAG_COUNT));
        Page page = dao.findAllByCountGreaterThanEqual(count, pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<Tag>(page, number);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Tag> list15(){
        int count = Integer.parseInt(optionService.getByKey(OptionDAO.TAG_COUNT));
        return dao.findFirst15ByCountGreaterThanEqual(count,sort);
    }


    // 判断关联词
    public List<Tag> listByKeyLike(String key){
        TagService tagService = SpringContextUtils.getBean(TagService.class);
        List<Tag> likes = new ArrayList<>();
        // 获取同义词，如果存在，直接返回，提示用户
        List<String> syms = CiLin.get_sym(key, CiLin.TYPE_SYM);
        if (!syms.isEmpty() && syms.size() > 0){
            for (String sym: syms) {
                Tag tagsunm = tagService.getByName(sym);
                if (tagsunm != null){
                    likes.add(tagsunm);
                    break;
                }
            }
        }
        // 如果不存在，找出关联词
        List<String> symclazz = CiLin.get_sym(key,CiLin.TYPE_SYMCLASS);
        if (!symclazz.isEmpty() && symclazz.size() > 0){
            for (String symclass: symclazz) {
                Tag symTag = tagService.getByName(symclass);
                if (symTag != null){
                    likes.add(symTag);
                }
            }
        }
        return likes;
    }

    public List<Tag> listByKey(String key){
        List<Tag> contains = dao.findAllByNameLike("%"+key+"%",sort);
        return contains;
    }
    public PageUtil<Tag> listByKey(int start, int size, int number, String key, List<Tag> likes){
        Pageable pageable = new PageRequest(start, size, sort);
        List<Integer> liketag = CollUtil.toList(0);
        for (Tag tag: likes) {
            liketag.add(tag.getId());
        }
        Page page = dao.findAllByNameLikeAndIdNotIn("%"+key+"%", liketag, pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<Tag>(page, number);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Tag> listByUser(int uid){
        List<Tag> list= new ArrayList<>();
        TagService tagService = SpringContextUtils.getBean(TagService.class);
            for (UserTag userTag:
             userTagService.listByUser(uid)) {
            int tid = userTag.getTid();
            Tag tag = tagService.get(tid);
//                System.out.println(tag);
            list.add(tag);
        }
        return list;
    }

    public PageUtil<Tag> listByUser(int start, int size, int number, int uid){
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = dao.findAllByUid(uid, pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<Tag>(page, number);
    }

    public int countByUser(int uid){
        return dao.countAllByUid(uid);
    }

    /**
     * 获取文章的关联标签
     **/
    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Tag> listByArticle(int aid){
        // 存储标签集结果
        List<Tag> list= new ArrayList<>();
        TagService tagService = SpringContextUtils.getBean(TagService.class);
        // 通过中间表，获取与文章ID关联的标签ID
            for (TagArticle tagArticle: tagArticleService.listByArticle(aid)) {
            int tid = tagArticle.getTid();
            // 获取标签实体
            Tag tag = tagService.get(tid);
            // 存储标签实体
            list.add(tag);
        }
        return list;
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Tag> list(int start, int size, int number) {
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = dao.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<Tag>(page, number);
    }
}
