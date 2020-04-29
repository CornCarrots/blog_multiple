package com.lh.blog.controller.back;

import com.lh.blog.bean.Carousel;
import com.lh.blog.bean.Manager;
import com.lh.blog.dao.CarouselDAO;
import com.lh.blog.service.CarouselService;
import com.lh.blog.util.FtpUtil;
import com.lh.blog.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        File imageFolder= new File("/home/ftpuser/blog/image/profile_user");
        String files[] = imageFolder.list();
        int num = files.length-1;
        return num;
    }

    @GetMapping(value = "/admin/carousels/{id}")
    public Carousel get(@PathVariable("id")int id)throws Exception
    {
        Carousel carousel = carouselService.get(id);
        return carousel;
    }

    @DeleteMapping(value = "/admin/carousels/{id}")
    public String delete(@PathVariable("id")int id)throws Exception
    {
        carouselService.delete(id);
        String name = id+".jpg";
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.deleteFile("/home/ftpuser/blog/image/carousel",name);
        return null;
    }


    @DeleteMapping(value = "/admin/userProfiles/{id}")
    public String deleteUser(@PathVariable("id")int id,HttpServletRequest request)throws Exception
    {
        String name = id+".jpg";
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.deleteFile("/home/ftpuser/blog/image/profile_user",name);
        return null;
    }

    @PutMapping(value = "/admin/userProfiles/{id}")
    public void updateUser(@PathVariable("id")int id,MultipartFile image,HttpServletRequest request)throws Exception
    {
        if(image==null)
            return;
//        File imageFolder= new File(request.getServletContext().getRealPath("image/profile_user"));
//        File file = new File(imageFolder,id+".jpg");
//        if(!file.getParentFile().exists())
//            file.getParentFile().mkdirs();
//        image.transferTo(file);
//        BufferedImage img = ImageUtil.change2jpg(file);
//        ImageIO.write(img, "jpg", file);
        String name = id+".jpg";
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.uploadFile(name,image.getInputStream(),"/home/ftpuser/blog/image/profile_user");
    }

    @PutMapping(value = "/admin/carousels/{id}")
    public void update(MultipartFile image,Carousel carousel,HttpServletRequest request)throws Exception
    {
        carouselService.update(carousel);
        if(image==null)
            return;
        int id = carousel.getId();
        // 本地上传
        File imageFolder= new File(request.getServletContext().getRealPath("image/carousel"));
        File file = new File(imageFolder,id+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);

        // FTP上传
//        String name = id+".jpg";
//        FtpUtil ftpUtil = new FtpUtil();
//        ftpUtil.uploadFile(name,image.getInputStream(),"/home/ftpuser/blog/image/carousel");
    }

    @PostMapping(value = "/admin/carousels")
    public void add(MultipartFile image, Carousel carousel, HttpServletRequest request)throws Exception{
        carouselService.add(carousel);
        int id = carousel.getId();
        File imageFolder= new File(request.getServletContext().getRealPath("image/carousel"));
        File file = new File(imageFolder,id+".jpg");
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
//        String name = id+".jpg";
//        FtpUtil ftpUtil = new FtpUtil();
//        ftpUtil.uploadFile(name,image.getInputStream(),"/home/ftpuser/blog/image/carousel");
    }

    @PostMapping(value = "/admin/userProfiles")
    public void addUser(MultipartFile image, int num, HttpServletRequest request)throws Exception{
        int id = num+1;
//        File imageFolder= new File(request.getServletContext().getRealPath("image/profile_user"));
//        File file = new File(imageFolder,id+".jpg");
//        if(!file.getParentFile().exists())
//            file.getParentFile().mkdirs();
//        image.transferTo(file);
//        BufferedImage img = ImageUtil.change2jpg(file);
//        ImageIO.write(img, "jpg", file);
        String name = id+".jpg";
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.uploadFile(name,image.getInputStream(),"/home/ftpuser/blog/image/profile_user");
    }




}
