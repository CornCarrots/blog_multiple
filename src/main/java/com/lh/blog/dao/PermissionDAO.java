package com.lh.blog.dao;

import com.lh.blog.bean.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionDAO extends JpaRepository<Permission,Integer> {

    public Permission findByOidAndMid(int oid, int mid);
}
