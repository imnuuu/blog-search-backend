package com.kakao.blog.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.blog.common.util.ObjectMapperUtil;
import feign.RequestTemplate;
import feign.Util;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;
import java.util.Collections;


public class JacksonIgnoreNullEncoder implements Encoder {

    private final ObjectMapper mapper;

    public JacksonIgnoreNullEncoder() {
        this(Collections.emptyList());
    }

    public JacksonIgnoreNullEncoder(Iterable<Module> modules) {
        this(ObjectMapperUtil.getObjectMapper()
          .registerModules(modules));
    }

    public JacksonIgnoreNullEncoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructType(bodyType);
            template.body(mapper.writerFor(javaType).writeValueAsBytes(object), Util.UTF_8);
        } catch (JsonProcessingException e) {
            throw new EncodeException(e.getMessage(), e);
        }
    }
}
