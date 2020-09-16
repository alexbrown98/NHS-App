package com.example.demo.config;

import com.example.demo.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


//Spring security configuration, for more details : https://www.baeldung.com/securing-a-restful-web-service-with-spring-security
//this link redirect to another tutorial now, here is the one I used : http://web.archive.org/web/20190731160351/https://www.baeldung.com/securing-a-restful-web-service-with-spring-security
@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler successHandler;

    @Autowired
    private MyUserDetailsService userDetailsService;

    private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //we might want to delete this later
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .and()
            .authorizeRequests().antMatchers("/").permitAll()
            .and()
            .authorizeRequests().antMatchers("/console/**").permitAll()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .successHandler(successHandler)
            .failureHandler(failureHandler)
            .and()
            .headers().frameOptions().disable().and()
            .logout();
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}