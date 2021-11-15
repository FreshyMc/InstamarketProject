package com.example.instamarket.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static List<String> targetPaths = Arrays.asList("/login", "/register", "/");
    Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long requestTime = request.getSession().getCreationTime();

        LocalDateTime time;

        time = LocalDateTime.ofInstant(Instant.ofEpochMilli(requestTime), TimeZone.getDefault().toZoneId());

        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String url = request.getRequestURI();

        //logger.info(request.getMethod());

        if(targetPaths.contains(url)){
            logger.info(String.format("Time of request: %s%nRequested url: %s%n", formattedTime, url));
        }

        return true;
    }
}
