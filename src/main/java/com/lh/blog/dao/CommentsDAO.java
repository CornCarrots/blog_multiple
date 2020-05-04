package com.lh.blog.dao;

import com.lh.blog.bean.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface CommentsDAO extends JpaRepository<Comments,Integer> {

    @Query(value = "select * from comments where (status = 0 or status = 2) and pid in " +
            "(select id from comments where (status = 0 or status = 2) and uid = :uid)" +
            "or aid in " +
            "(select id from article where uid = :uid)" +
            " order by ?#{#pageable}"
            ,nativeQuery=true)
    Page<Comments> listByUser(@Param("uid")int uid, Pageable pageable);

    @Query(value = "select count(1) from comments where (status = 0 or status = 2) and pid in " +
            "(select id from comments where (status = 0 or status = 2) and uid = :uid)" +
            "or aid in " +
            "(select id from article where uid = :uid)" +
            " order by id desc "
            ,nativeQuery=true)
    int countByUser(@Param("uid")int uid);

    public List<Comments> findAllByTextLike(String name, Sort sort);
    public List<Comments> findAllByStatus(int status,Sort sort);
    public List<Comments> findAllByPid(int pid,Sort sort);
    public List<Comments> findAllByStatusIn(List<Integer> status,Sort sort);
    public List<Comments> findAllByStatusAndAid(int status,int aid,Sort sort);
    public Page<Comments> findAllByStatusAndAid(int status, int aid, Pageable pageable);
    public Page<Comments> findAllByStatusAndAidAndPid(int status, int aid,int pid, Pageable pageable);
    public Page<Comments> findAllByStatusInAndAidAndPid(List<Integer> status, int aid,int pid, Pageable pageable);
    public List<Comments> findAllByStatusInAndAidAndUid(List<Integer> status, int aid,int uid, Sort sort);
    public Page<Comments> findAllByStatusAndUid(int status, int uid, Pageable pageable);
    public Page<Comments> findAllByStatusInAndUid(List<Integer> status, int uid, Pageable pageable);
    public List<Comments> findAllByStatusInAndUid(List<Integer> status, int uid, Sort sort);
    public List<Comments> findAllByStatusAndUid(int status, int uid, Sort sort);
    public List<Comments> findAllByStatusInAndPid(List<Integer> status,int pid,Sort sort);
    public List<Comments> findAllByStatusAndPid(int status,int pid,Sort sort);

}
