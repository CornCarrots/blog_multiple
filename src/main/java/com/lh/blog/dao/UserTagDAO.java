package com.lh.blog.dao;

import com.lh.blog.bean.UserTag;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository
public interface UserTagDAO extends JpaRepository<UserTag,Integer> {
    public List<UserTag> findAllByUid(int uid, Sort sort);
    public int countAllByUid(int uid, Sort sort);
    public List<UserTag> findAllByTid(int tid, Sort sort);
    public List<UserTag> findAllByTidAndUid(int tid, int uid, Sort sort);
    public void deleteAllByTid(int tid);
}
