package com.lh.blog.dao;

import com.lh.blog.bean.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionDAO extends JpaRepository<Option,Integer> {
    public static final String WEB_NAME = "web_name";
    public static final String WEB_DESC = "web_desc";
    public static final String WEB_RIGHT = "web_right";
    public static final String WEB_KEY = "web_key";
    public static final String TAG_COUNT = "tag_count";
    public static final String TAG_MAX = "tag_max";
    public static final String CATE_MAX = "cate_max";
    public static final String SWITCH_TAG = "switch_tag";
    public static final String SWITCH_CATE = "switch_cate";
    public static final String TAG_SCORE = "tag_score";
    public static final String TAG_WEIGHT = "tag_weight";
    public static final String INTERESTTAG_OVER = "interesttag_over";
    public Option findAllByKey(String key);
}
