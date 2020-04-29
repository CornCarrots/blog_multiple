package com.lh.blog.controller.back;

import com.lh.blog.bean.Manager;
import com.lh.blog.service.ManagerService;
import com.lh.blog.util.EncodeUtil;
import com.lh.blog.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

@RestController
public class PersonController {
    @Autowired
    ManagerService managerService;

    @GetMapping(value = "/admin/persons")
    public Manager get(HttpSession session)throws Exception
    {
        Manager manager = (Manager) session.getAttribute("manager");
        managerService.fill(manager);
        manager.setPassword("");
        return manager;
    }

    @PostMapping(value = "/admin/persons/image")
    public void upload(Manager manager, MultipartFile image, HttpServletRequest request) throws Exception
    {
        int id = manager.getId();
        File imageFolder= new File(request.getServletContext().getRealPath("image/profile_manager"));
        File file = new File(imageFolder,id+".jpg");
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    @PutMapping(value = "/admin/persons/{id}")
    public void update(@RequestBody Manager manager)throws Exception
    {
        Map<String,Object> map = EncodeUtil.encode(manager.getPassword());
        manager.setSalt(map.get("salt").toString());
        manager.setPassword(map.get("pass").toString());
        managerService.update(manager);
    }

}
