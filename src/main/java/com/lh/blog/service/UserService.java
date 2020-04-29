package com.lh.blog.service;
import com.lh.blog.bean.User;
import com.lh.blog.dao.UserDAO;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.RestPageImpl;
import com.lh.blog.util.SpringContextUtils;
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
@CacheConfig(cacheNames = "users")
public class UserService {
    @Autowired
    UserDAO dao;
    @Autowired
    MemberService memberService;
    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public long sum(){
        return dao.count();
    }

    @CacheEvict(allEntries = true)
    public void add(User bean){
        dao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        dao.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void update(User bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public User get(int id){
        return dao.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public User get(String name){return dao.findByName(name);}

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public User getByEmail(String name){return dao.findByEmail(name);}

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<User> list(){
        return dao.findAll(sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<User> listByKey(String key)
    {
        return dao.findAllByNameContaining(key,sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<User> list(int start, int size, int number)
    {
        Pageable pageable = new PageRequest(start,size,sort);
        Page<User> page = dao.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<User> listByKeyAndMember(String key,int mid)
    {
        return dao.findAllByNameContainingAndMidNot(key,mid,sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<User> listMember(int start,int size,int number,int mid)
    {
        Pageable pageable = new PageRequest(start,size,sort);
        Page<User> page = dao.findAllByMidNot(mid,pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

    public void fillMember(User user)
    {
        int mid = user.getMid();
        if(mid==0)
            return;
        user.setMember(memberService.get(mid));
    }

    public void fillMember(List<User> users)
    {
        UserService userService = SpringContextUtils.getBean(UserService.class);

        for (User user:
                users) {
            userService.fillMember(user);
        }
    }


//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<User> listByIds(int start,int size,int number,List<Integer> ids)
    {
        Pageable pageable = new PageRequest(start,size,sort);
        Page<User> page = dao.findAllByIdIn(ids,pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }
    public List<User> listByIds(List<Integer> ids)
    {
        return dao.findAllByIdIn(ids, sort);
    }
}
