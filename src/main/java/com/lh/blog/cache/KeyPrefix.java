package com.lh.blog.cache;

/**
 * @author linhao
 */
public interface KeyPrefix {

    /**
     * key的过期时间
      */
    int getExpireSeconds();

    /**
     *  key的前缀
     */
    String getPrefix();
}
