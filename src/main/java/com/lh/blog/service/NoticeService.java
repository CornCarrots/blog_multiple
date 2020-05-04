package com.lh.blog.service;
import com.lh.blog.bean.Notice;
import com.lh.blog.dao.NoticeDAO;
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
@CacheConfig(cacheNames = "notices")
public class NoticeService {
    @Autowired
    NoticeDAO dao;

    Sort sort = new Sort(Sort.Direction.DESC,"updateDate");

    @CacheEvict(allEntries = true)
    public void add(Notice bean){
        dao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        dao.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Notice bean){
        dao.save(bean);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Notice get(int id){
        return dao.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Notice> list(){
        return dao.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Notice> list(int start, int size, int number)
    {
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Notice> page = dao.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Notice> listByKey(String key){
        return dao.findAllByTitleLike("%"+key+"%",sort);
    }


}
