package com.lh.blog.service;

import cn.hutool.core.util.StrUtil;
import com.lh.blog.annotation.MethodLog;
import com.lh.blog.bean.*;
import com.lh.blog.dao.PermissionDAO;
import com.lh.blog.filter.URLHelper;
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
        for (Permission permission: permissions) {
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
     * 判断是否需要拦截
     * @param url 拦截URI
     * @return
     */
    public boolean needInterceptor(String url) {
        // 从缓存拿维护路径
        List<String> urls = URLHelper.getUrls();
//        List<String> urls = moduleService.listURL();
        for (String target : urls) {
            if (url.equals(target) || "/admin/".equals(url)) {
                return true;
            }
        }
        return false;
    }
    /**
     *  格式化URL所对应的权限 “模块路径 - 操作”
     *  优化空间复杂度，初始化map时指定大小，避免动态扩展，占用内存
     */
//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public HashMap<String, List<Operation>> formatPermission(List<Permission> permissions) {
        HashMap<String, List<Operation>> map = new HashMap<>(100);
        for (Permission permission : permissions) {
            String url;
            Module module = permission.getModule();
            Operation operation = permission.getOperation();
            // 祖先模块，加上斜杠
            boolean isParent = moduleService.hasChild(module);
            if (isParent) {
                url = module.getUrl() + (module.getPid() == 0 ? "/" : "");
            } else {
                url = moduleService.getChildURL(module.getUrl());
            }
            // 加入路径对应的操作
            List<Operation> operationList;
            if (map.containsKey(url)) {
                operationList = map.get(url);
            } else {
                operationList = new ArrayList<>();
            }
            operationList.add(operation);
            map.put(url,operationList);
        }
        return map;
    }

    /**
     * 判断是否有权限
     * @param mid 管理员ID
     * @param url 资源
     * @param method 操作
     * @return
     */
    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    @MethodLog
    public boolean hasPermission(int mid, String url, String method) {
//         方案一：获取所有权限，用字符串一一比对
        PermissionService permissionService = SpringContextUtils.getBean(PermissionService.class);
        // 获取权限
        List<Permission> permissions = rolePermissionService.listPermissionByManager(mid);
        HashMap<String, List<Operation>> map = permissionService.formatPermission(permissions);
        // 优化代码，去除无关逻辑，直接判断
        for (String key : map.keySet()) {
            // 找到资源
            if (key.equals(url)) {
                for (Operation val: map.get(key)) {
                    // 找到操作
                    if (val.getName().equals(method)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
