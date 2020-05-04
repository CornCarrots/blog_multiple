package com.lh.blog.dao;

import com.lh.blog.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UserDAO extends JpaRepository<User,Integer> {
    public List<User> findAllByNameLike(String name, Sort sort);
    public List<User> findTop5ByOrderByScoreDesc();
    public User findByName(String name);
    public User findByEmail(String email);
    public Page<User> findAllByMidNot(int mid, Pageable pageable);
    public Page<User> findAllByIdIn(List<Integer> id, Pageable pageable);
    public List<User> findAllByIdIn(List<Integer> id, Sort sort);
    public List<User> findAllByNameLikeAndMidNot(String name,int mid, Sort sort);
}
