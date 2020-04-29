package com.lh.blog.service;

import com.lh.blog.bean.*;
import com.lh.blog.dao.PermissionDAO;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.RestPageImpl;
import com.lh.blog.util.SpringContextUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@CacheConfig(cacheNames = "permissions")
public class PermissionService {
    @Autowired
    PermissionDAO permissionDAO;
    @Autowired
    ModuleService moduleService;
    @Autowired
    ManagerService managerService;
    @Autowired
    OperationService operationService;
    @Autowired
    RolePermissionService rolePermissionService;

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Permission> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return permissionDAO.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Permission> list(int start, int size, int number) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = permissionDAO.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());

        return new PageUtil<>(page, number);
    }

    @CacheEvict(allEntries = true)
    public void add(Permission permission)
    {
        permissionDAO.save(permission);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id)
    {
        permissionDAO.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void delete(int mid,int oid)
    {
        Permission permission = get(oid,mid);
        permissionDAO.delete(permission.getId());
    }

    @CacheEvict(allEntries = true)
    public void update(Permission permission)
    {
        permissionDAO.save(permission);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Permission get(int id)
    {
        return permissionDAO.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Permission get(int oid,int mid)
    {
        return permissionDAO.findByOidAndMid(oid,mid);
    }

    public void fill(List<Permission> permissions){
        PermissionService permissionService = SpringContextUtils.getBean(PermissionService.class);
        for (Permission permission:
             permissions) {
            permissionService.fill(permission);
        }
    }


    public void fill(Permission permission){
        int mid = permission.getMid();
        int oid = permission.getOid();
        permission.setModule(moduleService.get(mid));
        permission.setOperation(operationService.get(oid));
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")

    /**
     * 判断是否需要拦截 以/admin/为标准
     * @param url 拦截URI
     * @return
     */
    public boolean needInterceptor(String url) {
        List<String> urls = moduleService.listURL();
        for (String target : urls) {
            if (url.equals(target) || "/admin/".equals(url)) {
                return true;
            }
        }
        return false;
    }
    // 格式化URL所对应的权限 “模块路径 - 操作”
    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public HashMap<String, List<Operation>> formatPermission(List<Permission> permissions) {
        HashMap<String, List<Operation>> map = new HashMap<>();
        for (Permission permission : permissions) {
            String url;
            Module module = permission.getModule();
            Operation operation = permission.getOperation();
            // 模块为祖先模块，加上斜杠
            if (moduleService.hasChild(module)) {
                url = module.getUrl() + (module.getPid() == 0 ? "/" : "");
            }
            // 模块为子模块
            else {
                url = moduleService.getChildURL(module.getUrl());
            }
            // 加入路径对应的操作
            if (map.containsKey(url)) {
                List<Operation> list = map.get(url);
                list.add(operation);
                map.put(url,list);
            } else {
                List<Operation> list = new ArrayList<>();
                list.add(operation);
                map.put(url,list);
            }
        }
        return map;
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public boolean hasPermission(String name, String url, String method, Manager manager) {
        PermissionService permissionService = SpringContextUtils.getBean(PermissionService.class);
        // 获取权限
        List<Permission> permissions = rolePermissionService.listPermissionByManager(name);
        permissionService.fill(permissions);
        HashMap<String, List<Operation>> map = permissionService.formatPermission(permissions);
        for (String key : map.keySet()) {
            //
            if (key.equals(url)) {
                for (Operation val: map.get(key)) {
                    if (val.getName().equals(method)) {
                        String uri = url.substring(url.lastIndexOf("/"),url.length());
                        List<Module> modules = moduleService.getByURL(uri);
                        Module module = null;
                        if(modules.size()==1)
                            module = modules.get(0);
                        else
                        {
                            String parent = url.substring(0,url.indexOf(uri));
                            parent = parent.substring(parent.lastIndexOf("/"),parent.length());
                            Module module1 = moduleService.getByURL(parent).get(0);
                            for (Module module2: modules) {
                                if(module2.getPid()==module1.getPid())
                                    module=module1;
                            }
                        }
                        return true;
                    }
                }

            }
        }
        return false;
    }

}
