package com.lh.blog.dao;

import com.lh.blog.bean.Manager;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerDAO extends JpaRepository<Manager,Integer> {

    public List<Manager> findAllByNameLike(String key, Sort sort);

    public List<Manager> findAllByName(String name, Sort sort);

    public Manager findByName(String name);

    public Manager findByEmail(String email);

}
