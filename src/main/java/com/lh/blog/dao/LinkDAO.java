package com.lh.blog.dao;

import com.lh.blog.bean.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface LinkDAO extends JpaRepository<Link,Integer> {
    public Page<Link> findAllByStatusNot(Pageable pageable,int status);
}
