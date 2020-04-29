package com.lh.blog.service;

import com.lh.blog.bean.Option;
import com.lh.blog.dao.OptionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "options")
public class OptionService {
    @Autowired
    OptionDAO optionDAO;

    Sort sort = new Sort(Sort.Direction.DESC,"id");

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Option> list()
    {
        return optionDAO.findAll(sort);
    }

    @Caching(evict = {
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "options", allEntries = true)
    })
    public void add(Option option){
        optionDAO.save(option);
    }

    @Caching(evict = {
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "options", allEntries = true)
    })
    public void update(Option option){
        optionDAO.save(option);
    }

    @Caching(evict = {
            @CacheEvict(value = "tags", allEntries = true),
            @CacheEvict(cacheNames = "options", allEntries = true)
    })
    public void delete(int id){
        optionDAO.delete(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Option get(int id){
        return optionDAO.findOne(id);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public String getByKey(String key){
        Option option = optionDAO.findAllByKey(key);
        if (option != null) {
            return option.getValue();
        }
        return null;
    }
}
