package com.lh.blog.dao;

import com.lh.blog.bean.Power;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface PowerDAO extends JpaRepository<Power,Integer> {
    public Page<Power> findAllByMid(int mid, Pageable pageable);
    public List<Power> findAllByMidAndAndTitleLike(int mid, String title, Sort sort);
}
