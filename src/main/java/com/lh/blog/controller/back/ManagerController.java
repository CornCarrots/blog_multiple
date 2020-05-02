package com.lh.blog.controller.back;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.bean.Manager;
import com.lh.blog.bean.ManagerRole;
import com.lh.blog.bean.Role;
import com.lh.blog.service.ManagerRoleService;
import com.lh.blog.service.ManagerService;
import com.lh.blog.service.RoleService;
import com.lh.blog.util.EncodeUtil;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @Autowired
    RoleService roleService;
    @Autowired
    ManagerRoleService managerRoleService;


    @GetMapping(value = "/admin/managers")
    public Map<String, Object> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Map<String, Object> map = new HashMap<>();
        PageUtil<Manager> page = managerService.list(start, size, 5);
        List<Manager> managers = page.getContent();
        managerService.fill(managers);
        map.put("page", page);
        List<Role> roles = roleService.list();
        map.put("roles", roles);
        return map;
    }

    @PostMapping(value = "/admin/managers")
    public void add(@RequestBody JSONObject object)throws Exception
    {
        String str = JSONObject.toJSONString(object.get("manager"));
        Manager manager = JSONObject.parseObject(str,Manager.class);
        manager.setCreateDate(new Date());
        Map<String,Object> map = EncodeUtil.encode(manager.getPassword());
        manager.setSalt(map.get("salt").toString());
        manager.setPassword(map.get("pass").toString());
        managerService.add(manager);
        Object[] arr = ((ArrayList)(object.get("rids"))).toArray();
        for (int i = 0; i < arr.length; i++) {
            ManagerRole managerRole = new ManagerRole();
            managerRole.setMid(manager.getId());
            managerRole.setRid(Integer.parseInt(arr[i].toString()));
            managerRoleService.add(managerRole);
        }
    }

    @GetMapping(value = "/admin/managers/{id}")
    public Manager get(@PathVariable("id")int id)throws Exception
    {
        Manager manager =  managerService.get(id);
        managerService.fill(manager);
        manager.setPassword("");
        return manager;
    }

    @PutMapping(value = "/admin/managers/{id}")
    public void update(@RequestBody JSONObject object,@PathVariable("id")int mid)throws Exception
    {
        String str = JSONObject.toJSONString(object.get("manager"));
        Manager manager = JSONObject.parseObject(str,Manager.class);
        Map<String,Object> map = EncodeUtil.encode(manager.getPassword());
        manager.setSalt(map.get("salt").toString());
        manager.setPassword(map.get("pass").toString());
        managerService.update(manager);
        managerService.fill(manager);
        Object[] arr = ((ArrayList)(object.get("rids"))).toArray();
        List<Role> roles = manager.getRoles();
        Iterator<Role> iterator = roles.iterator();
        for (int i = 0; i < arr.length; i++) {
            boolean found = false;
            int rid = Integer.parseInt(arr[i].toString());
            while (iterator.hasNext()) {
                int rid2 = iterator.next().getId();
                if ( rid == rid2) {
                    iterator.remove();
                    found = true;
                    break;
                }
            }
            if(!found) {
                ManagerRole managerRole = new ManagerRole();
                managerRole.setRid(rid);
                managerRole.setMid(mid);
                managerRoleService.add(managerRole);
            }
        }
        iterator = roles.iterator();
        while (iterator.hasNext())
        {
            int rid = iterator.next().getId();
            managerRoleService.delete(managerRoleService.getByRoleAndManager(rid,mid).getId());
        }
    }

    @DeleteMapping(value = "/admin/managers/{id}")
    public String delete(@PathVariable("id")int id)throws Exception
    {
        managerRoleService.deleteByManager(id);
        managerService.delete(id);
        return null;
    }


    @PostMapping(value = "/admin/managers/search")
    public List<Manager> search(@RequestParam(value = "key") String key) throws Exception
    {
        List<Manager> managers =  managerService.listByKey(key);
        managerService.fill(managers);
        return managers;
    }


}
