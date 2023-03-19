package com.kakao.blog.common;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class ServerErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        int status = response.status();
        if (status >= 500) {
            return new RetryableException(
              response.status(), //status
              exception.getMessage(), //msg
              response.request().httpMethod(), //method
              exception, //cause
              null, //retryAfter,
              response.request());
        }
        return exception;
    }
}
