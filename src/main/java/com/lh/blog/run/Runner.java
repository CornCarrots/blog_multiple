package com.lh.blog.run;/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: linhao
 * @Date: 2020/04/23/20:52
 * @Description:
 */

import com.lh.blog.bean.Tag;
import com.lh.blog.es.CiLin;
import com.lh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *@author linhao
 *@date 2020/4/23 20:52
 */
@Component
public class Runner implements ApplicationRunner {

//    @Autowired
//    TagService tagService;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        CiLin.init();
//        List<List<String>> lists = CiLin.sym_words;
//        boolean flag = false;
//        for (List<String> list: lists) {
//            String s = list.get(0);
//            System.out.print(s + " ");
//            Tag tag = new Tag();
//            tag.setName(s);
//            tagService.add(tag);
//            for (String s: list) {
//                List<String> list1 = CiLin.get_sym(s, CiLin.TYPE_SYM);
//                flag
//                if (!flag){
//                    for (String s1:list1) {
//                        if (tagService.getByName(s1) != null){
//                            flag = true;
//                            break;
//                        }
//                    }
//                }
//
//            }
//        }
    }
}
