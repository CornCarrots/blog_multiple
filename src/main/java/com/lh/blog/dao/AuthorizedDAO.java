package com.lh.blog.dao;

import com.lh.blog.bean.Authorized;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface AuthorizedDAO extends JpaRepository<Authorized,Integer> {
    public Page<Authorized> findAllByStatusNot(Pageable pageable,int status);
}
