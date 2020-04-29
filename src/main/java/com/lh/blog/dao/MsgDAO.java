package com.lh.blog.dao;

import com.lh.blog.bean.Msg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface MsgDAO extends JpaRepository<Msg,Integer> {
    public List<Msg> findAllByRidAndStatusIn(int rid, List<Integer> status, Sort sort);
    public Page<Msg> findAllByRidAndStatusIn(int rid, List<Integer> status, Pageable pageable);

}
