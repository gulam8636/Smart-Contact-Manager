package com.ali.smart.dao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class sessionHelper {


    public void removeMessageFromSession(){
    try{

        System.out.println("Removing attribute");
        HttpServletRequest request = (HttpServletRequest) (RequestContextHolder.getRequestAttributes());
       request.getSession(false).removeAttribute("message");
    }
    catch( Exception e){
         e.printStackTrace();
    }
    }
}
