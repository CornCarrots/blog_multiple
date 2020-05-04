package com.lh.blog.dao;

import com.lh.blog.bean.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface CategoryDAO extends JpaRepository<Category,Integer> {
    public List<Category> findAllByPid(int pid, Sort sort);
    public List<Category> findAllByUid(int uid, Sort sort);
    public int countAllByUid(int uid);
    public Page<Category> findAllByUid(int uid, Pageable pageable);
    public List<Category> findAllByPidAndUid(int pid, int uid, Sort sort);
    public List<Category> findAllByPidNot(int pid, Sort sort);
    public List<Category> findAllByPidNotAndUid(int pid, int uid, Sort sort);
    public List<Category> findAllByNameLike(String name, Sort sort);
}
