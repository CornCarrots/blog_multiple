package com.lh.blog.dao;

import com.lh.blog.bean.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//
//@Repository
public interface TagDAO extends JpaRepository<Tag,Integer> {
    public List<Tag> findAllByNameContaining(String name, Sort sort);
    public Page<Tag> findAllByNameContainingAndIdNotIn(String name, List<Integer> id, Pageable sort);
    public List<Tag> findTagByNameEquals(String name);
    public int countAllByUid(int uid);
    public List<Tag> findAllByCountGreaterThanEqual(int count, Sort sort);
    public List<Tag> findFirst15ByCountGreaterThanEqual(int count, Sort sort);
    public Page<Tag> findAllByCountGreaterThanEqual(int count, Pageable pageable);
    public Page<Tag> findAllByUid(int uid, Pageable pageable);

}
