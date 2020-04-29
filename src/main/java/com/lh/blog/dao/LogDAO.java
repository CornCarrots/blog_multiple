package com.lh.blog.dao;

import com.lh.blog.bean.Log;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogDAO extends JpaRepository<Log,Integer> {
    public List<Log> findAllByMid(int mid, Sort sort);
}
