package com.lh.blog.service;

import com.lh.blog.bean.Log;
import com.lh.blog.bean.Manager;
import com.lh.blog.dao.LogDAO;
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
//@CacheConfig(cacheNames = "logs")
public class LogService {
    @Autowired
    LogDAO logDAO;
    @Autowired
    ManagerService managerService;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Log> list()
    {
        return logDAO.findAll(sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Log> list(int start,int size,int number){
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Log> page = logDAO.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Log> listByUser(int uid)
    {
        return logDAO.findAllByMid(uid,sort);
    }

//    @CacheEvict(allEntries = true)
    public void add(Log log){
        logDAO.save(log);
    }

//    @CacheEvict(allEntries = true)
    public void update(Log log){
        logDAO.save(log);
    }

//    @CacheEvict(allEntries = true)
    public void delete(int id){
        logDAO.delete(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Log get(int id){
        return logDAO.findOne(id);
    }

    public void fillLog(Log log){
        int mid = log.getMid();
        Manager manager = managerService.get(mid);
        managerService.fill(manager);
        log.setManager(manager);
    }

    public void fillLog(List<Log> logs)
    {
        LogService logService = SpringContextUtils.getBean(LogService.class);
        for (Log log:
             logs) {
            logService.fillLog(log);
        }
    }
}
