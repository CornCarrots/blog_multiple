package com.lh.blog.dao;

import com.lh.blog.bean.TagArticle;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface TagArticleDAO extends JpaRepository<TagArticle,Integer> {
    public List<TagArticle> findAllByAid(int aid, Sort sort);
    public List<TagArticle> findAllByTid(int tid, Sort sort);
    public void deleteByTid(int tid);
    public void deleteByAid(int aid);
}
