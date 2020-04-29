package com.lh.blog.controller.back;

import com.lh.blog.bean.Role;
import com.lh.blog.bean.RolePermission;
import com.lh.blog.service.RolePermissionService;
import com.lh.blog.service.RoleService;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    RolePermissionService rolePermissionService;

    @GetMapping(value = "/admin/roles")
    public PageUtil<Role> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception
    {
        start = start < 0 ? 0 : start;
        PageUtil<Role> page = roleService.list(start, size, 5);
        List<Role> roles = page.getContent();
        roleService.fill(roles);
        page.setContent(roles);
        return page;
    }

    @PostMapping(value = "/admin/roles")
    public void add(@RequestBody Role role) throws Exception
    {
        roleService.add(role);
    }

    /**
     * 删除角色
     **/
    @DeleteMapping(value = "/admin/roles/{id}")
    public String delete(@PathVariable("id") int id) throws Exception
    {
        // 获取所有权利并删除
        List<RolePermission> rolePermissionList = rolePermissionService.listByRole(id);
        if (rolePermissionList.size() > 0) {
            rolePermissionService.deleteByRole(id);
        }
        // 最后删除角色
        roleService.delete(id);
        return null;
    }

    @PutMapping(value = "/admin/roles/{id}")
    public void update(@RequestBody Role role) throws Exception
    {
        roleService.update(role);
    }

    @GetMapping(value = "/admin/roles/{id}")
    public Role get(@PathVariable("id")int id) throws Exception
    {
        Role role =  roleService.get(id);
        roleService.fill(role);
        return role;
    }

    @PostMapping(value = "/admin/roles/search")
    public List<Role> searchScore(@RequestParam(value = "key") String key) throws Exception {
        List<Role> roles = roleService.listByKey(key);
        roleService.fill(roles);
        return roles;
    }
}
