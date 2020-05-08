package com.lh.blog.dao;

import com.lh.blog.bean.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface LikeDAO extends JpaRepository<Like,Integer> {
    String TYPE_ARTICLE = "type_article";
    String TYPE_COMMENT = "type_comment";
    Like findAllByAcidAndUidAndType(int aid,int uid,String type);
    public void deleteAllByAcidAndType(int aid, String type);
}
