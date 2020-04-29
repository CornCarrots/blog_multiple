package com.lh.blog.dao;

import com.lh.blog.bean.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface MemberDAO extends JpaRepository<Member,Integer> {
    public List<Member> findAllByNameContaining(String name, Sort sort);
}
