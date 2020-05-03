package com.lh.blog.run;/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: linhao
 * @Date: 2020/04/23/20:52
 * @Description:
 */

import com.lh.blog.bean.Tag;
import com.lh.blog.es.CiLin;
import com.lh.blog.filter.URLHelper;
import com.lh.blog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author linhao
 * @date 2020/4/23 20:52
 */
@Component
public class Runner implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    @Override
    public void run(ApplicationArguments applicationArguments) throws IOException {
        try {
            CiLin.initWords();
            URLHelper.init();
        } catch (IOException e) {
            logger.error("init fail!");
            throw e;
        }
    }
}
