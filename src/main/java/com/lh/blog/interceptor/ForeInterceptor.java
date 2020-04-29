package com.lh.blog.interceptor;

import com.lh.blog.bean.*;
import com.lh.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class ForeInterceptor implements HandlerInterceptor {
    @Autowired
    NoticeService noticeService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    OptionService optionService;
    @Autowired
    UserService userService;
    @Autowired
    MsgService msgService;
    @Autowired
    MemberService memberService;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentsService commentsService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        ServletContext context = httpServletRequest.getServletContext();
        List<Option> options = optionService.list();
        List<Category> parentCategories = categoryService.listParent();
        categoryService.fillParent(parentCategories);
        List<Category> categories = categoryService.listChild();
        Notice notice = noticeService.list().get(0);
        List<Article> top_articles = articleService.listByComment();
        List<Comments> top_comments = commentsService.listForShow();
        commentsService.fillUser(top_comments);

        List<Tag> tags = tagService.list20();
        context.setAttribute("options", options);
        context.setAttribute("notice", notice);
        context.setAttribute("categories", categories);
        context.setAttribute("parentCategories", parentCategories);
        context.setAttribute("top_articles", top_articles.size()>5?top_articles.subList(0,5):top_articles);
        context.setAttribute("top_comments", top_comments.size()>5?top_comments.subList(0,5):top_comments);
        context.setAttribute("tags", tags);
        HttpSession session = httpServletRequest.getSession();
        int msg = 0;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Member> members = memberService.list();
            for (Member member:
                 members) {
                if(member.getMin()<=user.getScore()&&member.getMax()>user.getScore())
                {
                    user.setMid(member.getId());
                    userService.update(user);
                    break;
                }
            }
            List<Msg> msgs = msgService.listByUser(user.getId());
            msg = msgs.size();
            msg+=commentsService.countForShow(user.getId());
            if (user.getMember() == null) {
                userService.fillMember(user);
                session.setAttribute("user", user);
            }
        }
        session.setAttribute("msg", msg);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
