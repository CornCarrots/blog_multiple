package com.lh.blog.dao;

import com.lh.blog.bean.Start;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface StartDAO extends JpaRepository<Start,Integer> {
    public Start findAllByAidAndUid(int aid,int uid);
    public List<Start> findAllByUid(int uid, Sort sort);
    public void deleteAllByAid(int aid);

}
