package com.lh.blog.dao;

import com.lh.blog.bean.Module;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleDAO extends JpaRepository<Module,Integer> {
    public List<Module> findAllByNameLike(String name, Sort sort);
    public List<Module> findAllByPid(int pid, Sort sort);
    public int countAllByPid(int pid);
    public List<Module> findAllByUrl(String url);
}
