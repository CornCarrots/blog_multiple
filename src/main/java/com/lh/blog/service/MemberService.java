package com.lh.blog.service;
import com.lh.blog.bean.Member;
import com.lh.blog.dao.MemberDAO;
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
@CacheConfig(cacheNames = "members")
public class MemberService {
    @Autowired
    MemberDAO dao;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @CacheEvict(allEntries = true)
    public void add(Member bean){
        dao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        dao.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Member bean){
        dao.save(bean);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Member get(int id){
        return dao.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Member> list(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        return dao.findAll(sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Member> listByKey(String key) {
        return dao.findAllByNameLike("%"+key+"%",sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Member> list(int start, int size, int number) {
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = dao.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page, number);
    }
}
