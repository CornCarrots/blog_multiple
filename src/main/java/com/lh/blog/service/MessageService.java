package com.lh.blog.service;
import com.lh.blog.bean.Message;
import com.lh.blog.dao.MessageDAO;
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
@CacheConfig(cacheNames = "messages")
public class MessageService {
    @Autowired
    MessageDAO dao;
    @Autowired
    UserService userService;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @CacheEvict(allEntries = true)
    public void add(Message bean){
        dao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        dao.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Message bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Message get(int id){
        return dao.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Message> list(){
        return dao.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Message> list(int start, int size, int number)
    {
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Message> page = dao.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Message> listByKey(String key)
    {
        return dao.findAllByTextContaining(key,sort);
    }

    public void fillUser(Message message){
        int uid = message.getUid();
        message.setUser(userService.get(uid));
    }

    public void fillUser(List<Message> messages){
        MessageService messageService = SpringContextUtils.getBean(MessageService.class);
        for (Message message:
                messages) {
            messageService.fillUser(message);
        }
    }
}
