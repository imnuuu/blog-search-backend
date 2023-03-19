package com.kakao.blog.configuration;

import com.kakao.blog.common.constant.Constants.BlogPath;
import com.kakao.blog.common.util.DefaultWebSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @see <a href="https://spring.io/guides/gs/securing-web/">Securing a Web Application</a>
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Value("${spring.security.allowed-origin}")
    private String allowedOrigin;
    @Value("${spring.security.csrf.enabled}")
    private boolean isCsrfEnabled;
    @Value("${spring.security.hsts.enabled}")
    private boolean isHstsEnabled;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(DefaultWebSecurityUtil.corsConfigurationSource(allowedOrigin));
        http.csrf(this::csrfConfigure);
        setHttpStrictTransportSecurity(http);
        http.authorizeRequests(this::authorizationConfigure);
        http.formLogin().disable();
        return http.build();
    }

    private void authorizationConfigure(
      ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorization) {
        authorization
          .antMatchers(BlogPath.APIS_PATH).permitAll()
          .antMatchers(BlogPath.SWAGGER_PATH).permitAll()
          .antMatchers(BlogPath.HEALTH_PATH, BlogPath.METRICS_PATH, BlogPath.FAVICON_ICO_PATH).permitAll()
          .anyRequest().authenticated();
    }

    private void setHttpStrictTransportSecurity(HttpSecurity http) throws Exception {
        if (isHstsEnabled) {
            http.headers().httpStrictTransportSecurity().maxAgeInSeconds(31536000).includeSubDomains(true);
        } else {
            http.headers().httpStrictTransportSecurity().disable();
        }
    }

    private void csrfConfigure(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
        if (isCsrfEnabled) {
            httpSecurityCsrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        } else {
            httpSecurityCsrfConfigurer.disable();
        }
    }
}
