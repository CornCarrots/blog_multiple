package com.lh.blog.service;

import com.lh.blog.bean.ManagerRole;
import com.lh.blog.dao.ManagerRoleDAO;
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
import java.util.Set;

@Service
@CacheConfig(cacheNames = "managerRoles")
public class ManagerRoleService {
    @Autowired
    ManagerRoleDAO managerRoleDAO;

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<ManagerRole> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return managerRoleDAO.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<ManagerRole> listByManager(int mid) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return managerRoleDAO.findAllByMid(mid,sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<ManagerRole> listByRole(int rid) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return managerRoleDAO.findAllByRid(rid,sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<ManagerRole> list(int start, int size, int number) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = managerRoleDAO.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page, number);
    }

    @Caching(evict = {
            @CacheEvict(value = "roles", allEntries = true),
            @CacheEvict(cacheNames = "managerRoles", allEntries = true)
    })
    public void add(ManagerRole managerRole)
    {
        managerRoleDAO.save(managerRole);
    }

    @Caching(evict = {
            @CacheEvict(value = "roles", allEntries = true),
            @CacheEvict(cacheNames = "managerRoles", allEntries = true)
    })
    public void delete(int id)
    {
        managerRoleDAO.delete(id);
    }

    public void deleteByManager(int mid) {
        ManagerRoleService managerRoleService = SpringContextUtils.getBean(ManagerRoleService.class);
        for (ManagerRole managerRole: listByManager(mid)) {
            managerRoleService.delete(managerRole.getId());
        }

    }

    @Caching(evict = {
            @CacheEvict(value = "roles", allEntries = true),
            @CacheEvict(cacheNames = "managerRoles", allEntries = true)
    })
    public void update(ManagerRole managerRole)
    {
        managerRoleDAO.save(managerRole);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public ManagerRole get(int id)
    {
        return managerRoleDAO.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public ManagerRole getByRoleAndManager(int rid,int mid)
    {
        return managerRoleDAO.findByRidAndMid(rid,mid);
    }

}
