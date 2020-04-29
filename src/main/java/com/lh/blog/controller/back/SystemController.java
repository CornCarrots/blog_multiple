package com.lh.blog.controller.back;

import com.lh.blog.bean.Log;
import com.lh.blog.bean.Manager;
import com.lh.blog.bean.Option;
import com.lh.blog.bean.User;
import com.lh.blog.service.LogService;
import com.lh.blog.service.ManagerService;
import com.lh.blog.service.OptionService;
import com.lh.blog.util.ImageUtil;
import com.lh.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
public class SystemController {
    @Autowired
    OptionService optionService;
    @Autowired
    LogService logService;
    @Autowired
    ManagerService managerService;

    @GetMapping(value = "/admin/logs")
    public PageUtil<Log> listLog(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception{
        PageUtil<Log> page =  logService.list(start,size,5);
        List<Log> logs = page.getContent();
        if(logs.size()!=0)
            logService.fillLog(logs);
        page.setContent(logs);
        return page;
    }

    @GetMapping(value = "/admin/options")
    public List<Option> listOption()throws Exception
    {
        return optionService.list();
    }

    @PutMapping(value = "/admin/options/image")
    public void updateImage(MultipartFile image, HttpServletRequest request)throws Exception
    {
        String name = "option.jpg";
        if(image!=null)
        {
            File imageFolder= new File(request.getServletContext().getRealPath("image/option"));
            File file = new File(imageFolder,name);
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }

    }

    @PutMapping(value = "/admin/options/{oid}")
    public void updateOption(@RequestBody Option option)throws Exception
    {
        optionService.update(option);
    }
    @GetMapping(value = "/admin/logs/search")
    public List<Log> search(@RequestParam("key")String key)
    {
        Manager manager = managerService.getByName(key);
        if(manager==null)
            return null;
        else
            return logService.listByUser(manager.getId());
    }



}
