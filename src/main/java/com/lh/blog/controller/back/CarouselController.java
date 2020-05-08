package com.lh.blog.controller.back;

import com.lh.blog.bean.Carousel;
import com.lh.blog.enums.PathEnum;
import com.lh.blog.service.CarouselService;
import com.lh.blog.util.FtpUtil;
import com.lh.blog.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@RestController
public class CarouselController {
    @Autowired
    CarouselService carouselService;

    @GetMapping(value = "/admin/carousels")
    public List<Carousel> list()throws Exception
    {
        return carouselService.list();
    }

    @GetMapping(value = "/admin/userProfiles")
    public int listUser(HttpServletRequest request)throws Exception
    {
        int num = ImageUtil.getUserImg();
        return num;
    }

    @GetMapping(value = "/admin/carousels/{id}")
    public Carousel get(@PathVariable("id")int id)throws Exception
    {
        return carouselService.get(id);
    }

    @DeleteMapping(value = "/admin/carousels/{id}")
    public String delete(@PathVariable("id")int id)throws Exception
    {
        carouselService.delete(id);
        ImageUtil.deleteImg(String.valueOf(id), -1, PathEnum.USER_PROFILE);
//        String name = id+".jpg";
//        FtpUtil ftpUtil = new FtpUtil();
//        ftpUtil.deleteFile("/home/ftpuser/blog/image/carousel",name);
        return null;
    }


    @DeleteMapping(value = "/admin/userProfiles/{id}")
    public String deleteUser(@PathVariable("id")int id)throws Exception
    {
        ImageUtil.deleteImg(String.valueOf(id), -1, PathEnum.USER_PROFILE);
        return null;
    }

    @PutMapping(value = "/admin/userProfiles/{id}")
    public void updateUser(@PathVariable("id")int id,MultipartFile image,HttpServletRequest request)throws Exception
    {
        if(image==null) {
            return;
        }
        ImageUtil.uploadImg(String.valueOf(id), -1, image, PathEnum.USER_PROFILE);
    }

    @PutMapping(value = "/admin/carousels/{id}")
    public void update(MultipartFile image,Carousel carousel,HttpServletRequest request)throws Exception
    {
        carouselService.update(carousel);
        if(image==null)
            return;
        int id = carousel.getId();
        ImageUtil.uploadImg(String.valueOf(id), -1, image, PathEnum.Carousel);
    }

    @PostMapping(value = "/admin/carousels")
    public void add(MultipartFile image, Carousel carousel, HttpServletRequest request)throws Exception{
        carouselService.add(carousel);
        int id = carousel.getId();
        if(image==null) {
            return;
        }
        ImageUtil.uploadImg(String.valueOf(id), -1, image, PathEnum.Carousel);
    }

    @PostMapping(value = "/admin/userProfiles")
    public void addUser(MultipartFile image, int num, HttpServletRequest request)throws Exception{
        int id = num+1;
        if(image==null) {
            return;
        }
        ImageUtil.uploadImg(String.valueOf(id), -1, image, PathEnum.USER_PROFILE);
    }




}
