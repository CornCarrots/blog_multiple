package com.lh.blog.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

public class GenericFastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public GenericFastJson2JsonRedisSerializer() {
        super();
    }
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        ParserConfig.getGlobalInstance().setAsmEnable(false);
        ParserConfig.getGlobalInstance().addAccept("com.lh.blog");

//        ParserConfig.getGlobalInstance().addAccept("com.lh.blog.");
//        ParserConfig.getGlobalInstance().addAccept("org.springframework.data.domain.Sort$Order");
//        ParserConfig.getGlobalInstance().addAccept("com.xxx.xxx.bo");
//        ParserConfig.getGlobalInstance().addAccept("com.xxx.xxx.redis");
    }
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }

        System.out.println("0"+t);
        FastJsonWraper<T> wraperSet =new FastJsonWraper<>(t);
        return JSON.toJSONString(wraperSet, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String deserializeStr = new String(bytes, DEFAULT_CHARSET);
        System.out.println("1:"+deserializeStr);
        FastJsonWraper<T> wraperGet=JSON.parseObject(deserializeStr,FastJsonWraper.class);
        System.out.println("2:"+wraperGet.getValue());
        return wraperGet.getValue();
    }

}
