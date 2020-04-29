package com.lh.blog.dao;

import com.lh.blog.bean.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface LikeDAO extends JpaRepository<Like,Integer> {
    public String type_article = "type_article";
    public String type_comment = "type_comment";
    public Like findAllByAcidAndUidAndType(int aid,int uid,String type);
}
