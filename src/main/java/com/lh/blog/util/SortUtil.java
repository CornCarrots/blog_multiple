package com.lh.blog.util;

import com.lh.blog.bean.Article;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtil {
    public static List<Article> sort(List<Article> articles, String order) {
        Comparator<Article> all = new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        };
        Comparator<Article> view = new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getView() - o2.getView();
            }
        };
        Comparator<Article> date = new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getCreateDate().compareTo(o2.getCreateDate());
            }
        };
        Comparator<Article> star = new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getStart() - o2.getStart();
            }
        };
        Comparator<Article> like = new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getLike() - o2.getLike();

            }
        };
        Comparator<Article> comment = new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getComment() - o2.getComment();

            }
        };
        Comparator<Article> comparator = null;
        switch (order) {
            case "all":
                comparator = all;
                break;
            case "view":
                comparator = view;
                break;
            case "date":
                comparator = date;
                break;
            case "star":
                comparator = star;
                break;
            case "like":
                comparator = like;
                break;
            case "comment":
                comparator = comment;
                break;
        }
        Collections.sort(articles, comparator);
        return articles;
    }
}
