package org.weibo.hl.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.weibo.hl.security.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.security.config
 * @className: SecurityConfig
 * @description:
 * @author: hl
 * @createDate: 2023/5/10 21:28
 */

@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 通过 AuthenticationManagerBuilder 创建一个认证用户的信息
     * <p>
     * 用户名为 admin, 密码为 admin, USER 的角色
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    //     authenticationManagerBuilder
    //             .inMemoryAuthentication()
    //             .passwordEncoder(new BCryptPasswordEncoder())
    //             .withUser("admin")
    //             .password(new BCryptPasswordEncoder().encode("admin"))
    //             .roles("ADMIN", "USER");
    //     authenticationManagerBuilder
    //             .inMemoryAuthentication()
    //             .passwordEncoder(new BCryptPasswordEncoder())
    //             .withUser("test")
    //             .password(new BCryptPasswordEncoder().encode("test"))
    //             .roles("USER");
    // }

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    RedisTemplate<String, String> StringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    public void configureGlobalMysql(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置 HttpSecurity
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/blogs/**").hasRole("USER")
                .and()
                // .formLogin().loginPage("/login")
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        Authentication auth)
                            throws IOException {


                        Object principal = auth.getPrincipal();
                        // 设置正文类型
                        resp.setContentType("application/json;charset=utf-8");
                        Map<String, Object> map = new HashMap<>();
                        // 生成 token
                        try {
                            String token = generateToken(auth);
                            // 状态码
                            resp.setStatus(200);
                            Cookie cookie = new Cookie("token", token);
                            cookie.setPath("/");
                            resp.addCookie(cookie);
                            map.put("status", 200);
                            map.put("msg", principal);
                        } catch (Exception e) {
                            resp.setStatus(401);
                            map.put("status", 401);
                            map.put("msg", "登录发生异常");
                        }
                        // 用来输出字符串形式得正文
                        PrintWriter out = resp.getWriter();
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        AuthenticationException e)
                            throws IOException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        resp.setStatus(401);
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 401);
                        if (e instanceof LockedException) {
                            map.put("msg", "账户被锁定，登录失败!");
                        } else if (e instanceof BadCredentialsException) {
                            map.put("msg", "账户名或密码输入错误，登录失败!");
                        } else if (e instanceof DisabledException) {
                            map.put("msg", "账户被注销，登录失败!");
                        } else if (e instanceof AccountExpiredException) {
                            map.put("msg", "账户已过期，登录失败!");
                        } else if (e instanceof CredentialsExpiredException) {
                            map.put("msg", "密码已过期，登录失败!");
                        } else {
                            map.put("msg", "登录失败!");
                        }
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest req,
                                       HttpServletResponse resp,
                                       Authentication auth) {

                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req,
                                                HttpServletResponse resp,
                                                Authentication auth)
                            throws IOException {
                        //resp.sendRedirect("/loginAdmin");

                    }
                })
                .and()
                // 跨域配置
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        resp.setStatus(401);
                        PrintWriter out = resp.getWriter();
                        String msg = "请求失败";
                        if (authException instanceof InsufficientAuthenticationException) {
                            msg = "请求失败，请联系管理员!";
                        }
                        out.write(new ObjectMapper().writeValueAsString(msg));
                        out.flush();
                        out.close();
                    }
                });
        http.logout().logoutSuccessUrl("/");
    }

    private String generateToken(Authentication auth) {
        // 加锁
        Lock lock = redissonClient.getLock("weibo::security::tokenlock");
        lock.lock();
        String token = UUID.randomUUID().toString();
        try {
            User user = (User) auth.getPrincipal();
            // Boolean aBoolean = StringRedisTemplate.opsForHash().putIfAbsent("weibo::security::token", user.getId(), token);
            StringRedisTemplate.opsForValue().set("weibo::security::token::" + token, user.getId().toString(), 10 * 60 * 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("登录获取 token 异常!", e);
            throw e;
        } finally {
            lock.unlock();
        }
        return token;
    }
}
