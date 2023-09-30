package com.ali.smart.controller;

import com.ali.smart.dao.UserRepository;
import com.ali.smart.entities.Contact;
import com.ali.smart.entities.User;
import com.ali.smart.helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
//@RequestMapping("/")
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/")
    public String home(Model model) {

        model.addAttribute("title", "Home - Smart Contact Manager");

        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {

        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("title", "Register - Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

//    this handler for register user

    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                               Model model, HttpSession session) {

if(bindingResult.hasErrors()){

    System.out.println("ERROR" + bindingResult.toString());
    model.addAttribute("user",user);
    return "signup";
}

        try {
            if(!agreement)
            {
                System.out.println("You do not agreed the terms and conditions");
                throw new Exception("You do not agreed the terms and conditions");
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("Default.png");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            User result = userRepository.save(user);
            System.out.println("Agreement : "+agreement);
            System.out.println("USER : "+user);
            model.addAttribute("user",new User());
            session.setAttribute("message",new Message("Successfully Registered !!","alert-success"));
            return "signup";
        } catch (Exception e) {
          //handle error
            e.printStackTrace();
            model.addAttribute("user",user);
            session.setAttribute("message",new Message("Something went wrong !!"+e.getMessage(),"alert-danger"));
            return "signup";
        }

    }

    @GetMapping("/signin")
    public String customLogin(Model model){

        model.addAttribute("title","Login Page");
        return "login";
    }


}
