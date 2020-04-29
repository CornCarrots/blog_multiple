package com.lh.blog.dao;

import com.lh.blog.bean.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface HistoryDAO extends JpaRepository<History,Integer> {
    public Page<History> findAllByUid(int uid, Pageable pageable);

}
