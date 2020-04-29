package com.lh.blog.service;

import com.lh.blog.bean.Link;
import com.lh.blog.dao.LinkDAO;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.RestPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@CacheConfig(cacheNames = "links")
public class LinkService {
    @Autowired
    LinkDAO dao;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

//    @CacheEvict(allEntries = true)
    public void add(Link bean){
        dao.save(bean);
    }

//    @CacheEvict(allEntries = true)
    public void delete(int id){
        dao.delete(id);
    }

//    @CacheEvict(allEntries = true)
    public void update(Link bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Link get(int id){
        return dao.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Link> list(){
        return dao.findAll(sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Link> list(int start, int size, int number) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = dao.findAllByStatusNot(pageable,2);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page, number);
    }
}
