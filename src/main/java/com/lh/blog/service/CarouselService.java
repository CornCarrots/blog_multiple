package com.lh.blog.service;

import com.lh.blog.bean.Carousel;
import com.lh.blog.dao.CarouselDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@CacheConfig(cacheNames = "carousels")
public class CarouselService {
    @Autowired
    CarouselDAO carouselDAO;

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Carousel> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return carouselDAO.findAll(sort);
    }

//    @CacheEvict(allEntries = true)
    public void add(Carousel carousel) {
        carouselDAO.save(carousel);
    }

//    @CacheEvict(allEntries = true)
    public void update(Carousel carousel) {
            carouselDAO.save(carousel);
    }

//    @CacheEvict(allEntries = true)
    public void delete(int id){
        carouselDAO.delete(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Carousel get(int id)
    {
        return carouselDAO.findOne(id);
    }

}
