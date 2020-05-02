package com.lh.blog.service;

import com.lh.blog.bean.*;
import com.lh.blog.dao.CommentsDAO;
import com.lh.blog.dao.CountComment;
import com.lh.blog.dao.OptionDAO;
import com.lh.blog.util.PageUtil;
import com.lh.blog.util.RestPageImpl;
import com.lh.blog.util.SpringContextUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@CacheConfig(cacheNames = "comments")
public class CommentsService {
    @Autowired
    CommentsDAO dao;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;
    @Autowired
    OptionService optionService;

    private static Logger logger = Logger.getLogger(CommentsService.class);

    Sort sort = new Sort(Sort.Direction.DESC,"id");

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public long sum(){
        return dao.count();
    }

    @CacheEvict(allEntries = true)
    public void add(Comments bean){
        dao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id){
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        List<Comments> comments = dao.findAllByPid(id,sort);
        if(comments.size()>0) {
            for (Comments c : comments) {
                commentsService.delete(c.getId());
            }
        }
        dao.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Comments bean){
        dao.save(bean);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Comments get(int id){
        return dao.findOne(id);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Comments> list(){
        return dao.findAll(sort);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Comments> listForShow(){
        List<Comments> list = new ArrayList<>();
        list =  dao.findAllByStatusIn(Arrays.asList(0,2),sort);
        return list;
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Comments> listByParent(int pid){
        return dao.findAllByStatusInAndPid(Arrays.asList(0,2),pid,sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Comments> listByStatusAndParent(int status,int pid){
        return dao.findAllByStatusAndPid(status,pid,sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Comments> list(int start, int size, int number)
    {
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Comments> page = dao.findAll(pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Comments> listByParent(int aid,int start,int size, int number)
    {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(0);
        integerList.add(2);
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Comments> page = dao.findAllByStatusInAndAidAndPid(integerList,aid,0,pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Comments> listByStatusAndUser(int uid,int start,int size, int number)
    {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(0);
        integerList.add(2);
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Comments> page = dao.findAllByStatusInAndUid(integerList,uid,pageable);
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<Comments> listByKey(String key)
    {
        return dao.findAllByTextContaining(key,sort);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public boolean checkUser(int uid,int aid)
    {
        return true;
//        boolean check = false;
//        List<Integer> integerList = new ArrayList<>();
//        integerList.add(0);
//        integerList.add(2);
//        String[] strings = optionService.getByKey(OptionDAO.WEB_KEY).split(",");
//        List<Comments> list = dao.findAllByStatusInAndAidAndUid(integerList,aid,uid,sort);
//        for (Comments comments:
//             list) {
//            for (int i = 0; i < strings.length; i++) {
//                if(comments.getText().contains(strings[i]))
//                {
//                    check = true;
//                    break;
//                }
//            }
//
//        }
//        return check;
    }

    public void fillArticle(Comments comments){
        int aid = comments.getAid();
        comments.setArticle(articleService.get(aid));
    }

    public void fillArticle(List<Comments> comments){
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        for (Comments message:
                comments) {
            commentsService.fillArticle(message);
        }
    }
    public void fillLike(Comments comments,int uid){
        Like like = likeService.getComment(comments.getId(),uid);
        comments.setHasLike(like!=null);
        if(comments.getChild()!=null&&comments.getChild().size()!=0)
            for (Comments comments1:
                 comments.getChild()) {
                Like like1 = likeService.getComment(comments1.getId(),uid);
                comments1.setHasLike(like1!=null);
            }
    }

    public void fillLike(List<Comments> comments,int uid){
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        for (Comments message: comments) {
            commentsService.fillLike(message, uid);
            List<Comments> child = message.getChild();
            if (child != null && !child.isEmpty()) {
                commentsService.fillLike(child, uid);
            }
        }
    }

    public void fillUser(Comments comments){
        int uid = comments.getUid();
        User user = userService.get(uid);
//        userService.fillMember(user);
        comments.setUser(user);
    }

    public void fillUser(List<Comments> comments){
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        for (Comments message:
                comments) {
            commentsService.fillUser(message);
        }
    }

    public void fillChild(Comments comments){
        List<Comments> child = null;
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        int pid = comments.getId();
        if(pid!=0)
        {
           child = commentsService.listByParent(pid);
           commentsService.fillUser(child);
        }
        comments.setChild(child);
    }

    public void fillChild(List<Comments> comments){
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        for (Comments message:
                comments) {
            commentsService.fillChild(message);
        }
    }

    public void fillParent(Comments comments){
        Comments parent = null;
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        int pid = comments.getPid();
        if(pid!=0)
        {
           parent = commentsService.get(pid);
           commentsService.fillUser(parent);
        }
        comments.setParent(parent);
    }

    public void fillParent(List<Comments> comments){
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        for (Comments message: comments) {
            commentsService.fillParent(message);
        }
    }

    public void fillComments(List<Comments> comments){
        for (Comments comment: comments) {
            fillComments(comment);
        }
    }

    public void fillComments(Comments comments){
        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
        commentsService.fillUser(comments);
        commentsService.fillArticle(comments);
    }

//    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public PageUtil<Comments> listByUser(int uid,int start,int size, int number){
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Comments> page = null;
        try {
            page = dao.listByUser(uid,pageable);
        }catch (IllegalArgumentException e){
            logger.info("分页插件异常",e);
        }
        page = new RestPageImpl(page.getContent(),pageable,page.getTotalElements());
        return new PageUtil<>(page,number);
    }

    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public Integer countForShow(int uid){
        return dao.countByUser(uid);
//        CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
//        List<CountComment> count =  dao.countByUser(uid);
//        int res = count.get(0).getCount();
//        return res;
//        List<Comments> show = new ArrayList<>();
//        for (Comments comments:
//             list) {
//            int id = comments.getId();
//            List<Comments> list1 = commentsService.listByStatusAndParent(2,id);
//            if(list1.size()>0)
//                show.addAll(list1);
//        }
//        return show;
    }
}
