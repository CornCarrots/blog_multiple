package com.lh.blog.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

public class FastJsonRedisSerializer<T>  implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    static {
        ParserConfig.getGlobalInstance().addAccept("com.lh.blog.");
        ParserConfig.getGlobalInstance().addAccept("com.lh.blog.util");
//        ParserConfig.getGlobalInstance().addAccept("org.springframework.data.domain");
        ParserConfig.getGlobalInstance().addAccept("com.xxx.xxx.bo");
        ParserConfig.getGlobalInstance().addAccept("com.xxx.xxx.redis");
//        ParserConfig.getGlobalInstance().addAccept("com.xxx.xxx.redis");
    }

    private Class<T> clazz;

    private Class<T> cla;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }

        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        System.out.println(str);
        System.out.println(clazz);
        return (T) JSON.parseObject(str, clazz);
    }
}
