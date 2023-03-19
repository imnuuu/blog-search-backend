package com.kakao.blog.common;

import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignExponentialBackOffRetryer implements feign.Retryer {

    final int maxAttempts;
    final long period;
    int attempt;

    public FeignExponentialBackOffRetryer() {
        this(1000, 4);
    }

    public FeignExponentialBackOffRetryer(long period, int maxAttempts) {
        this.period = period;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (attempt >= maxAttempts) {
            log.info("Exceed maximum attempts, attempt: {}, maxAttempts: {}", attempt, maxAttempts);
            throw e;
        }

        double interval;
        if (e.retryAfter() != null) {
            interval = e.retryAfter().getTime() - (double) System.currentTimeMillis();
            if (interval < 0) {
                return;
            }
        } else {
            interval = period * Math.pow(2, attempt - 1d);
        }

        try {
            log.info("attempt: {}, sleep interval: {}", attempt, interval);
            Thread.sleep(Math.round(interval));
            attempt++;
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    @Override
    public FeignExponentialBackOffRetryer clone() {
        return new FeignExponentialBackOffRetryer(this.period, this.maxAttempts);
    }
}
