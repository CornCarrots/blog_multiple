package com.lh.blog.service;

import com.lh.blog.bean.Msg;
import com.lh.blog.bean.User;
import com.lh.blog.dao.MsgDAO;
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

import java.util.*;

@Service
//@CacheConfig(cacheNames = "msgs")
public class MsgService {
    @Autowired
    MsgDAO msgDAO;
    @Autowired
    UserService userService;

    Sort sort = new Sort(Sort.Direction.ASC, "id");

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Msg> list() {
        return msgDAO.findAll(sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Msg> listByUser(int uid) {
        return msgDAO.findAllByRidAndStatusIn(uid,Arrays.asList(0,2),sort);
    }

//    @CacheEvict(allEntries = true)
    public void add(Msg msg)
    {
        msgDAO.save(msg);
    }

//    @CacheEvict(allEntries = true)
    public void delete(int id)
    {
        msgDAO.delete(id);
    }

//    @CacheEvict(allEntries = true)
    public void update(Msg operation)
    {
        msgDAO.save(operation);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Msg get(int id)
    {
        return msgDAO.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Msg> listByReceive(int start, int size, int number,int uid)
    {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page page = msgDAO.findAllByRidAndStatusIn(uid, Arrays.asList(0,2),pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<Msg>(page,number);
    }

    public void fill(List<Msg> msgs){
        for (Msg msg: msgs) {
            fill(msg);
        }
    }

    public void fill(Msg msg){
        int sid = msg.getSid();
        int rid = msg.getRid();
        User send = userService.get(sid);
        msg.setSend(send);
        User receive = userService.get(rid);
        msg.setReceive(receive);
    }
}
