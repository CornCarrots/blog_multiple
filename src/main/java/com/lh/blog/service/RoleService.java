package com.lh.blog.service;

import com.lh.blog.bean.*;
import com.lh.blog.dao.RoleDAO;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "roles")
public class RoleService {
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    ManagerRoleService managerRoleService;
    @Autowired
    ManagerService managerService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    PermissionService permissionService;

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Role> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return roleDAO.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Role> listByKey(String key) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return roleDAO.findAllByNameLike("%"+key+"%",sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Role> listByManager(String username) {
        Manager manager = managerService.getByName(username);
        List<Role> roles = new ArrayList<>();
        List<ManagerRole> managerRoles = managerRoleService.listByManager(manager.getId());
        for (ManagerRole managerRole : managerRoles) {
            Role role = get(managerRole.getRid());
            roles.add(role);
        }
        return roles;
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Set<String> listRoleNamesByManager(String name) {
        RoleService roleService = SpringContextUtils.getBean(RoleService.class);
        List<Role> roles = roleService.listByManager(name);
        Set<String> names = new HashSet<>();
        for (Role role: roles) {
            names.add(role.getName());
        }
        return names;
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Role> list(int start, int size, int number) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = roleDAO.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());

        return new PageUtil<>(page, number);
    }

    @Caching(evict = {
            @CacheEvict(value = "rolePermissions", allEntries = true),
            @CacheEvict(cacheNames = "roles", allEntries = true)
    })
    public void add(Role role)
    {
        roleDAO.save(role);
    }

    @Caching(evict = {
            @CacheEvict(value = "rolePermissions", allEntries = true),
            @CacheEvict(cacheNames = "roles", allEntries = true)
    })
    public void delete(int id)
    {
        roleDAO.delete(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "rolePermissions", allEntries = true),
            @CacheEvict(cacheNames = "roles", allEntries = true)
    })
    public void update(Role role)
    {
        roleDAO.save(role);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Role get(int id)
    {
        return roleDAO.findOne(id);
    }

    public void fill(Role role){
        List<Manager> managers = new ArrayList<>();
        List<ManagerRole> managerRoles = managerRoleService.listByRole(role.getId());
        for (ManagerRole managerRole:
             managerRoles) {
            int mid = managerRole.getMid();
            managers.add(managerService.get(mid));
        }
        role.setManagers(managers);
        List<RolePermission> rolePermissions = rolePermissionService.listByRole(role.getId());
        List<Permission> permissions = new ArrayList<>();
        for (RolePermission rolePermission:
             rolePermissions) {
            int pid = rolePermission.getPid();
            permissions.add(permissionService.get(pid));
        }
        role.setPermissions(permissions);
    }

    public void fill(List<Role> roles)
    {
        RoleService roleService = SpringContextUtils.getBean(RoleService.class);

        for (Role role:
             roles) {
            roleService.fill(role);
        }
    }


}
