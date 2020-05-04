package com.lh.blog.dao;

import com.lh.blog.bean.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface NoticeDAO extends JpaRepository<Notice,Integer> {
    public List<Notice> findAllByTitleLike(String title, Sort sort);

}
