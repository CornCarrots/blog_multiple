package com.lh.blog.service;

import com.lh.blog.bean.Authorized;
import com.lh.blog.bean.User;
import com.lh.blog.dao.AuthorizedDAO;
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
@CacheConfig(cacheNames = "authorizeds")
public class AuthorizedService {
    @Autowired
    AuthorizedDAO dao;
    @Autowired
    UserService userService;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @CacheEvict(allEntries = true)
    public void add(Authorized bean){
        dao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        dao.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Authorized bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Authorized get(int id){
        return dao.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Authorized> list(){
        return dao.findAll(sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Authorized> list(int start, int size, int number) {
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = dao.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page, number);
    }


    public void fillUser(List<Authorized> categories){
        AuthorizedService authorizedService = SpringContextUtils.getBean(AuthorizedService.class);
        for (Authorized authorized:
                categories) {
            authorizedService.fillUser(authorized);
        }
    }

    public void fillUser(Authorized authorized){
        AuthorizedService authorizedService = SpringContextUtils.getBean(AuthorizedService.class);
        int uid = authorized.getUid();
        if(uid==0)
            return;
        User user = userService.get(uid);
        authorized.setUser(user);
    }
}
