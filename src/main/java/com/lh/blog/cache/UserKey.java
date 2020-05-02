package com.lh.blog.cache;

import java.io.Serializable;

public class UserKey extends BaseKeyPrefix  implements Serializable {

    // 缓存有效时间为30min
    public static final int TOKEN_EXPIRE = 30*60;

    public static final String TOKEN_PREFERFIX = "token";

    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE, TOKEN_PREFERFIX);

    public static UserKey getRandom = new UserKey("user_random");

}
