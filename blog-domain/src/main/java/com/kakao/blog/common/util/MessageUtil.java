package com.kakao.blog.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtil {

    public static String getSingleLineStackTrace(Exception e) {
        if (e == null) {
            return "";
        }
        return ExceptionUtils.getStackTrace(e).replace("\n", ";");
    }
}
