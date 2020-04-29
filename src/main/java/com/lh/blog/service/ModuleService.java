package com.lh.blog.service;

import cn.hutool.core.collection.CollectionUtil;
import com.lh.blog.bean.Module;
import com.lh.blog.bean.Operation;
import com.lh.blog.bean.Permission;
import com.lh.blog.bean.Role;
import com.lh.blog.dao.ModuleDAO;
import com.lh.blog.filter.URLHelper;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.RestPageImpl;
import com.lh.blog.util.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "modules")
public class ModuleService {

    private static Logger logger = LoggerFactory.getLogger(ModuleService.class);

    @Autowired
    ModuleDAO moduleDAO;
    @Autowired
    PermissionService permissionService;
    @Autowired
    OperationService operationService;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Module> list()
    {
        return moduleDAO.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Module> list(int start,int size,int number)
    {
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Module> page = moduleDAO.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

    @Caching(evict = {
            @CacheEvict(value = "permissions", allEntries = true),
            @CacheEvict(cacheNames = "modules", allEntries = true)
    })
    public void add(Module module)
    {
        moduleDAO.save(module);
    }

    @Caching(evict = {
            @CacheEvict(value = "permissions", allEntries = true),
            @CacheEvict(cacheNames = "modules", allEntries = true)
    })
    public void update(Module module){
        moduleDAO.save(module);
    }

    @Caching(evict = {
            @CacheEvict(value = "permissions", allEntries = true),
            @CacheEvict(cacheNames = "modules", allEntries = true)
    })
    public void delete(int id){
        moduleDAO.delete(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Module get(int id){
        return moduleDAO.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Module> getByURL(String url){
        return moduleDAO.findAllByUrl(url);
    }

    public void fill(Module module,List<Permission> permissions){
        Map<Module,List<Operation>> map = new HashMap<>();
        for (Permission permission:
                permissions) {
            if (permission.getMid()!=module.getId())
                continue;
            Operation operation = operationService.get(permission.getOid());
            if(map.containsKey(module))
            {
                map.get(module).add(operation);
            }
            else
            {
                List<Operation> operations = new ArrayList<>();
                operations.add(operation);
                map.put(module,operations);
            }
        }
        module.setOperations(map.get(module));
    }

    public void fill(List<Module> modules,List<Permission> permissions){
        ModuleService moduleService = SpringContextUtils.getBean(ModuleService.class);
        for (Module module:
             modules) {
            moduleService.fill(module,permissions);
        }
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Module> listByKey(String key)
    {
        return moduleDAO.findAllByNameContaining(key,sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Map<String, Object>> listForTree(Role role) {
        ModuleService moduleService = SpringContextUtils.getBean(ModuleService.class);
        List<Module> modules = moduleService.list();
        List<Module> modules1 = new ArrayList<>();
        for (Permission permission: role.getPermissions()) {
            Module module = moduleService.get(permission.getMid());
            if(!modules1.contains(module)) {
                modules1.add(module);
            }
        }
        List<Map<String, Object>> list = new ArrayList<>();
        int pid = -1;
        boolean found = false;
        boolean check = false;
        for (Module module : modules) {
            Map<String, Object> map = new HashMap<>();
            map.put("parent_id", module.getPid());
            map.put("layer_name", module.getDesc());
            map.put("id", module.getId());
            Map<String, Object> state = new HashMap<>();
            if(modules1.size()==0) {
                if (module.getPid()==0) {
                    state.put("checked", false);
                    state.put("selected",true);
                    state.put("expanded",true);
                }
            }
            else {
                for (Module module1: modules1) {
                    if(module1.getId()==module.getId()) {
                        if(!found) {
                            state.put("selected",true);
                            pid = module1.getPid();
                            found = true;
                        }
                        else {
                            if(module1.getId() == pid) {
                                state.put("selected",false);
                                pid = module1.getPid();
                            }
                            else
                                state.put("selected",false);
                        }
                        state.put("expanded",true);
                        state.put("checked",true);
                        check = true;
                        break;
                    }
                }
                if(!check) {
                    state.put("checked",false);
                    state.put("selected",false);
                    state.put("expanded",false);
                }
            }
            map.put("state",state);
            list.add(map);
        }
        return list;
    }

    /**
     * 根路径
     * @return
     */
    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Module> listRoot() {
        ModuleService moduleService = SpringContextUtils.getBean(ModuleService.class);
        return moduleService.listByParent(0);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Module> listByParent(int pid) {
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        return moduleDAO.findAllByPid(pid,sort);
    }

    /**
     * 判断是否父模块
     * @param module
     * @return
     */
//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public boolean hasChild(Module module) {
        int mid = module.getId();
        return moduleDAO.countAllByPid(mid) != 0;
    }

    /**
     * 获取维护模块的路径
     * 进行优化 因为数据只需要读不需要写，无需保证数据一致性
     * Redis缓存 换成本地缓存，减少网络带宽
     * */
    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    @Deprecated
    public List<String> listURL() {
        try {
            ModuleService moduleService = SpringContextUtils.getBean(ModuleService.class);
            List<String> urls = new ArrayList<>();
            // 获取根路径
            List<Module> roots = moduleService.listRoot();
            for (Module root : roots) {
                StringBuilder rootURL = new StringBuilder(root.getUrl());
                // 拼装路径
                List<String> paths = moduleService.listURLByParent(rootURL,root.getId());
                urls.addAll(paths);
            }
            logger.info("[拼装维护路径成功] {}条路径", urls.size());
            return urls;
        }catch (Exception e){
            logger.error("[拼装维护路径失败]", e);
            return CollectionUtil.newArrayList();
        }
    }

    /**
     * 递归获取路径 应用树的深度遍历算法
     * @param parentURL 父路径
     * @param pid
     * @return
     */
    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<String> listURLByParent(StringBuilder parentURL, int pid) {
        try {
            ModuleService moduleService = SpringContextUtils.getBean(ModuleService.class);
            List<String> urls = new ArrayList<>();
            List<Module> childs = moduleService.listByParent(pid);
            for (Module child : childs) {
                // 获取子模块路径
                String childURL = child.getUrl();
                parentURL.append(childURL);
                boolean isParent = hasChild(child);
                // 父节点，递归获取子节点
                if (isParent) {
                    urls.addAll(moduleService.listURLByParent(parentURL, child.getId()));
                }
                // 加入子节点
                else {
                    urls.add(parentURL.toString());
                }
                // 返回上一场
                int last = parentURL.lastIndexOf("/");
                parentURL.delete(last, parentURL.length());
            }
            return urls;
        }catch (Exception e){
            return CollectionUtil.newArrayList();
        }
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public String getChildURL(String url) {
        List<String> urls = URLHelper.getUrls();
        for (String trueUrl: urls) {
            if(trueUrl.contains(url) && trueUrl.endsWith(url)) {
                return trueUrl;
            }
        }
        return null;
    }
}
