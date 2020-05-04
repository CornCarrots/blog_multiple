package com.lh.blog.dao;

import com.lh.blog.bean.Operation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationDAO extends JpaRepository<Operation,Integer> {
    public List<Operation> findAllByNameLike(String key, Sort sort);
    public List<Operation> findAllByName(String name);
}
