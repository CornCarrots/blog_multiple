package com.lh.blog.dao;

import com.lh.blog.bean.Focus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository
public interface FocusDAO extends JpaRepository<Focus,Integer> {
    public static final int YES = 1;
    public static final int NO = 2;
    public static final int BOTH = 3;
    public long countAllByFrom(int uid);
    public long countAllByTo(int uid);
    public List<Focus> findAllByFromAndTo(int from, int to, Sort sort);
    public List<Focus> findAllByFrom(int from, Sort sort);
    public List<Focus> findAllByTo(int to, Sort sort);
}
