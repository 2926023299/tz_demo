package com.example.tz_demo.filter;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.tz_demo.common.AppJwtUtil;
import com.example.tz_demo.entity.common.R;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author:  唐哲
 * date:    2022/12/22 14:23
 * description: 权限过滤器
 */
@Component
@Slf4j
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class AuthorizeFilter  implements Filter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        log.info("拦截到:{}", req.getRequestURI());

        String[] url = {
                "/admin/login",
                "/admin/register",
                "/swagger-ui.html",
                "/swagger-ui.html/**",
                "/swagger-resources/**",
                "/favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/v2/api-docs",
                "/v2/**"
        };

        if (check(url, req.getRequestURI())) {
            log.info("请求{}不需要处理", req.getRequestURI());

            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 获取token
        String token = req.getHeader("token");

        //4.判断token是否存在
        if (StringUtils.isBlank(token)) {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.getWriter().write(JSON.toJSONString(R.error("token不存在")));
            return;
        }

        //4-2、判断登录状态，如果已登录，则直接放行
        try {
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            //是否是过期
            int result = AppJwtUtil.verifyToken(claimsBody);
            if (result == 1 || result == 2) {
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                // 如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
                servletResponse.getWriter().write(JSON.toJSONString(R.error("NOT LOGIN")));
                return;
            }

            //获取用户信息存入head
            assert claimsBody != null;
            String userId = claimsBody.get("id").toString();
            log.info("用户id:{}", userId);

            //判断是否在redis中
            String redisToken = redisTemplate.opsForValue().get(userId);
            if(StringUtils.isBlank(redisToken) || !redisToken.equals(token)){
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                resp.getWriter().write(JSON.toJSONString(R.error("token已过期")));
            }

            ((HttpServletRequest) servletRequest).getSession().setAttribute("userId", userId);
            resp.setHeader("userId", userId);

            //放行
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    public boolean check(String[] urls, String url) {
        for (String u : urls) {
            if (PATH_MATCHER.match(u, url)) {
                return true;
            }
        }
        return false;
    }
}
