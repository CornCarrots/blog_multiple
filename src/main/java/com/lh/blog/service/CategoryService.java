package com.lh.blog.service;

import com.lh.blog.bean.Category;
import com.lh.blog.dao.CategoryDAO;
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

import java.util.List;

@Service
@CacheConfig(cacheNames = "categories")
public class CategoryService {
    @Autowired
    CategoryDAO dao;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public long sum(){
        return dao.count();
    }

//    @CacheEvict(allEntries = true)
    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "categories", allEntries = true)
    })
    public void add(Category bean){
        dao.save(bean);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "categories", allEntries = true)
    })
    public void delete(int id){
        dao.delete(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "articles", allEntries = true),
            @CacheEvict(cacheNames = "categories", allEntries = true)
    })
    public void update(Category bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Category get(int id){
        return dao.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Category> list(){
        return dao.findAll(sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Category> listChild(){
        return dao.findAllByPidNot(0,sort);
    }


//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Category> listChildWithUser(int uid){
        return dao.findAllByPidNotAndUid(0, uid, sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Category> listParent(){
        return dao.findAllByPid(0,sort);
    }

    public List<Category> listByParent(int pid){
        return dao.findAllByPid(pid,sort);
    }

    public List<Category> listByUser(int uid){
        return dao.findAllByUid(uid,sort);
    }

    public List<Category> listByParentAndUser(int pid, int uid){
        List<Category> categories =  dao.findAllByPidAndUid(pid, uid, sort);
        return categories;
    }

    public List<Category> listByKey(String key){
        return dao.findAllByNameLike("%"+key+"%",sort);
    }

    public PageUtil<Category> list(int start, int size, int number) {
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = dao.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<Category>(page, number);
    }


    public PageUtil<Category> listByUser(int start, int size, int number, int uid) {
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = dao.findAllByUid(uid, pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<Category>(page, number);
    }
    public int countByUser(int uid) {
        return dao.countAllByUid(uid);
    }

    public void fillParentWithUser(List<Category> categories, int uid){
        CategoryService categoryService = SpringContextUtils.getBean(CategoryService.class);
        for (Category category:
             categories) {
            categoryService.fillParentWithUser(category, uid);
        }
    }

    public void fillParent(List<Category> categories){
        CategoryService categoryService = SpringContextUtils.getBean(CategoryService.class);
        for (Category category:
             categories) {
            categoryService.fillParent(category);
        }
    }

    public void fillParentWithUser(Category category, int uid){
        CategoryService categoryService = SpringContextUtils.getBean(CategoryService.class);
        int id = category.getId();
        if(id != 0) {
            category.setChild(categoryService.listByParentAndUser(id, uid));
        }
    }


     // 为分类填充孩子字段
    public void fillParent(Category category){
        CategoryService categoryService = SpringContextUtils.getBean(CategoryService.class);
        int id = category.getId();
        // 如果当前分类id=0，则为抽象的分类，是祖先
        if(id != 0) {
            // 否则为该分类设置孩子
            List<Category> categories = categoryService.listByParent(id);
            category.setChild(categories);
        }
    }

    /**
     * 为分类填充孩子字段
     **/
    public void fillChild(List<Category> categories){
        CategoryService categoryService = SpringContextUtils.getBean(CategoryService.class);
        for (Category category: categories) {
            categoryService.fillChild(category);
        }
    }


     // 为分类填充父亲字段
    public void fillChild(Category category){
        CategoryService categoryService = SpringContextUtils.getBean(CategoryService.class);
        int pid = category.getPid();
        // 如果pid=0，则证明该分类为父分类，跳过
        if(pid != 0) {
            category.setParent(categoryService.get(pid));
        }
    }
}
