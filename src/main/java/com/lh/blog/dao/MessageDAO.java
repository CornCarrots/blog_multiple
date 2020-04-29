package com.lh.blog.dao;

import com.lh.blog.bean.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface MessageDAO extends JpaRepository<Message,Integer> {
    public static String type_complaint = "type_complaint";
    public static String type_suggestion = "type_suggestion";
    public List<Message> findAllByTextContaining(String name, Sort sort);
}
