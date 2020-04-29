package com.lh.blog.service;

import com.lh.blog.bean.Manager;
import com.lh.blog.bean.ManagerRole;
import com.lh.blog.bean.Role;
import com.lh.blog.dao.ManagerDAO;
import com.lh.blog.util.EncodeUtil;
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
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "managers")
public class ManagerService {
    @Autowired
    ManagerDAO managerDAO;
    @Autowired
    ManagerRoleService managerRoleService;
    @Autowired
    RoleService roleService;

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Manager> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return managerDAO.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Manager> listByKey(String key) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return managerDAO.findAllByNameContaining(key,sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Manager> list(int start, int size, int number) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = managerDAO.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page, number);
    }

    @Caching(evict = {
            @CacheEvict(value = "roles", allEntries = true),
            @CacheEvict(cacheNames = "managers", allEntries = true)
    })
    public void add(Manager manager) { managerDAO.save(manager); }

    @Caching(evict = {
            @CacheEvict(value = "roles", allEntries = true),
            @CacheEvict(cacheNames = "managers", allEntries = true)
    })
    public void delete(int id)
    {
        managerDAO.delete(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "roles", allEntries = true),
            @CacheEvict(cacheNames = "managers", allEntries = true)
    })
    public void update(Manager manager)
    {
        managerDAO.save(manager);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Manager get(int id)
    {
        return managerDAO.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Manager> listByName(String name){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return managerDAO.findAllByName(name,sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Manager getByName(String name){ return managerDAO.findByName(name); }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Manager getByEmail(String email){ return managerDAO.findByEmail(email); }

    public void fill(Manager manager)
    {
        List<Role> roles = new ArrayList<>();
        List<ManagerRole> managerRoles = managerRoleService.listByManager(manager.getId());
        for (ManagerRole managerRole:
             managerRoles) {
            int rid = managerRole.getRid();
            roles.add(roleService.get(rid));
        }
        manager.setRoles(roles);
    }

    public void fill(List<Manager> managers)
    {
        ManagerService managerService = SpringContextUtils.getBean(ManagerService.class);
        for (Manager manager:
             managers) {
            managerService.fill(manager);
        }
    }
}
