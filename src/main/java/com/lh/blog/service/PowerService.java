package com.lh.blog.service;
import com.lh.blog.bean.Power;
import com.lh.blog.dao.PowerDAO;
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
@CacheConfig(cacheNames = "powers")
public class PowerService {
    @Autowired
    PowerDAO dao;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @CacheEvict(allEntries = true)
    public void add(Power bean){
        dao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        dao.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Power bean){
        dao.save(bean);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Power get(int id){
        return dao.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Power> list(){
        return dao.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Power> listByMember(int start, int size, int number, int mid)
    {
        Pageable pageable = new PageRequest(start,size,sort);
        Page page = dao.findAllByMid(mid,pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<Power>(page,number);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Power> listByMemberAndKey(int mid,String key) {
        return dao.findAllByMidAndAndTitleContaining(mid,key,sort);
    }
}
