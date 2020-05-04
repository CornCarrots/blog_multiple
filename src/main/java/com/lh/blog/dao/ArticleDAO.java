package com.lh.blog.dao;

import com.lh.blog.bean.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface ArticleDAO extends JpaRepository<Article, Integer> {

    public static final String TYPE_PUBLISH = "type_publish";
    public static final String TYPE_DRAFT = "type_draft";

    public List<Article> findAllByStatus(int status, Sort sort);
    public List<Article> findTop5ByStatusAndUid(int status, int uid, Sort sort);
    public List<Article> findTop5ByOrderByCommentDesc();
    public List<Article> findTop5ByStatusAndTypeAndUid(int status, String type, int uid, Sort sort);
    public List<Article> findAllByStatusAndUid(int status, int uid, Sort sort);
    public List<Article> findAllByStatusAndTitleLike(int status,String title, Sort sort);
    public Page<Article> findAllByStatus(int status, Pageable pageable);
    public Page<Article> findAllByStatusAndType(int status,String type, Pageable pageable);
    public Page<Article> findAllByStatusAndUid(int status, int uid, Pageable pageable);
    public Page<Article> findAllByStatusAndTypeAndUid(int status, String type, int uid, Pageable pageable);
    public Page<Article> findAllByStatusAndUidAndTitleLike(int status, int uid, String title, Pageable pageable);
    public Page<Article> findAllByStatusAndTypeAndUidAndTitleLike(int status, String type, int uid, String title, Pageable pageable);
    public List<Article> findAllByStatusAndType(int status,String type,Sort sort);
    public List<Article> findAllByStatusAndCid(int status,int cid,Sort sort);
    public Page<Article> findAllByStatusAndCid(int status,int cid,Pageable pageable);
    public Page<Article> findAllByStatusAndUidAndCid(int status,int uid, int cid, Pageable pageable);
    public Page<Article> findAllByStatusAndTypeAndUidAndCid(int status, String type, int uid, int cid, Pageable pageable);
    public Page<Article> findAllByStatusAndUidAndCidAndTitleLike(int status,int uid, int cid, String title, Pageable pageable);
    public Page<Article> findAllByStatusAndTypeAndUidAndCidAndTitleLike(int status, String type, int uid, int cid, String title, Pageable pageable);
    public Page<Article> findAllByStatusAndTypeAndCidIn(int status, String type, List<Integer> cid,Pageable pageable);
    public Page<Article> findAllByStatusAndTypeAndIdIn(int status, String type, List<Integer> id,Pageable pageable);
    public List<Article> findAllByIdIn(List<Integer> id);
    public Page<Article> findAllByStatusAndTitleLike(int status,String title,Pageable pageable);

}
