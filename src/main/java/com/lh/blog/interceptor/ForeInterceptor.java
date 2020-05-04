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

        List<User> top_users = userService.listForShow();
        userService.fillMember(top_users);

        List<Tag> tags = tagService.list15();
        context.setAttribute("options", options);
        context.setAttribute("notice", notice);
        context.setAttribute("categories", categories);
        context.setAttribute("parentCategories", parentCategories);
        context.setAttribute("top_articles", top_articles);
        context.setAttribute("top_users", top_users);
        context.setAttribute("tags", tags);
        HttpSession session = httpServletRequest.getSession();
        int msg = 0;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Msg> msgs = msgService.listByUser(user.getId());
            msg = msgs.size();
            msg+=commentsService.countForShow(user.getId());
            userService.fillMember(user);
            session.setAttribute("user", user);
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
