package com.ali.smart.config;

import com.ali.smart.dao.CloudinaryImageService;
import com.ali.smart.dao.CloudinaryImageServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class projectConfig {

public SecurityFilterChain FilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.authorizeHttpRequests(

            authorize-> authorize


                    .requestMatchers("/cloudinary/upload/**").permitAll()
    );

    return httpSecurity.build();
}


    @Bean
    public Cloudinary getCloudinary(){

        Map<String,String> config = new HashMap<>();
        config.put("cloud_name","dc0qllxrz");
        config.put("api_key","822141842153846");
        config.put("api_secret","2y0RC5TNfeS6Spfrv0jMDl3DeJk");
        config.put("secure","true");
        return new Cloudinary(config);
    }


}
