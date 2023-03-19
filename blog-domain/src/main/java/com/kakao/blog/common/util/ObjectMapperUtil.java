package com.kakao.blog.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ObjectMapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_NULL)
      .configure(SerializationFeature.INDENT_OUTPUT, true)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .registerModule(new JavaTimeModule());

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static MultiValueMap<String, String> convertValueToMultiValueMap(Object fromValue) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> map = ObjectMapperUtil.getObjectMapper().convertValue(fromValue, new TypeReference<>() {});
        params.setAll(map);
        return params;
    }
}
