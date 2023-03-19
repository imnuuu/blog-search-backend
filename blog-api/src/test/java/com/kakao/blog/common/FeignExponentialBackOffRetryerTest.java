package com.kakao.blog.common;

import static org.junit.jupiter.api.Assertions.*;

import feign.Request;
import feign.Request.Body;
import feign.Request.HttpMethod;
import feign.RequestTemplate;
import feign.RetryableException;
import java.sql.Date;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class FeignExponentialBackOffRetryerTest {

    @Test
    void continueOrPropagate() {
        FeignExponentialBackOffRetryer retryer = new FeignExponentialBackOffRetryer();
        assertEquals(1, retryer.attempt);
        assertEquals(1000, retryer.period);
        assertEquals(4, retryer.maxAttempts);

        RetryableException retryableException = retryableException(null);
        try {
            retryer.NEVER_RETRY.continueOrPropagate(retryableException);
            fail();
        } catch (RetryableException exception) {
            assertEquals(500, exception.status());
        }

    }

    @Test
    void continueOrPropagateWithRetryAfter() {
        FeignExponentialBackOffRetryer retryer = new FeignExponentialBackOffRetryer(100, 10);
        assertEquals(1, retryer.attempt);
        assertEquals(100, retryer.period);
        assertEquals(10, retryer.maxAttempts);

        long timeMillis = System.currentTimeMillis() - 1000;
        Date retryAfterToShowDoNotRetry = new Date(timeMillis);
        RetryableException retryableExceptionWithRetryAfter = retryableException(retryAfterToShowDoNotRetry);

        try {
            retryer.continueOrPropagate(retryableExceptionWithRetryAfter);
        } catch (RetryableException exception) {
            assertEquals(500, exception.status());
        }
    }

    @Test
    void testClone() {
        FeignExponentialBackOffRetryer clone = new FeignExponentialBackOffRetryer(10, 100).clone();
        assertEquals(1, clone.attempt);
        assertEquals(10, clone.period);
        assertEquals(100, clone.maxAttempts);
    }

    private Request mockRequest() {
        return Request.create(HttpMethod.GET, "https://api.airtable.com", Collections.emptyMap(), Body.create("test".getBytes()),
          new RequestTemplate());
    }

    private RetryableException retryableException(Date retryAfter) {
        return new RetryableException(500, "ServerError", HttpMethod.POST, retryAfter, this.mockRequest());
    }

}