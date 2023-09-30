package com.ali.smart.config;

import com.ali.smart.UserDetailsServiceImpl;

//import com.ali.smart.UserDetailsServiceImpl;
import com.ali.smart.dao.CloudinaryImageService;
import com.ali.smart.dao.CloudinaryImageServiceImpl;
import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.HttpSecurityDsl;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class MyConfig  {


@Bean
    public SecurityFilterChain FilterChain(HttpSecurity httpSecurity) throws Exception {


     httpSecurity.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/admin/**")
                     .hasRole("ADMIN")

                     .requestMatchers("/user/**")
                     .hasRole("USER")
                    // .authenticated()
                     .requestMatchers("/**").permitAll()


    )
             .logout(formLogout ->
                     formLogout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

             )
            .formLogin(formLogin -> formLogin
                    .loginPage("/signin")
                            .loginProcessingUrl("/dologin")
                            .defaultSuccessUrl("/user/index")

//                            .failureUrl("/login-fail")
//                    .permitAll()
            )


     ;



return httpSecurity.build();

 }

    @Bean
    public UserDetailsService getUserDetailService(){

        return new UserDetailsServiceImpl();
    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());

        return daoAuthenticationProvider;
    }




}

