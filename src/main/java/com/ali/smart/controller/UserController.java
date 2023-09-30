package com.ali.smart.controller;

import com.ali.smart.dao.ContactRepository;
import com.ali.smart.dao.UserRepository;
import com.ali.smart.dao.sessionHelper;
import com.ali.smart.entities.Contact;
import com.ali.smart.entities.User;
import com.ali.smart.helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    sessionHelper sh;

    @Autowired
   private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String userName = principal.getName();
        System.out.println("USER : " +userName);
        User user = userRepository.getUserByUserName(userName);
        System.out.println("USER : "+user);
        model.addAttribute("user",user);

    }

   @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){

        model.addAttribute("title","User Dashboard");


       return "normal/user_dashboard";
    }

    @GetMapping("/normal")
    public ResponseEntity<String> normalUser(){

        return ResponseEntity.ok("I am a normal user");
    }
    @GetMapping("/admin")
    public ResponseEntity<String> adminUser(){

        return ResponseEntity.ok("I am a admin user");
    }
    @GetMapping("/public")
    public ResponseEntity<String> publicUser(){

        return ResponseEntity.ok("I am a public user");
    }

    @GetMapping("/add-contact")
    public String openAddContactForm(Model model){
       model.addAttribute("title","Add Contact");
       model.addAttribute("contact",new Contact());

       return "normal/add_contact_form";
    }


    //process contact
    @PostMapping(value = "/process-contact")
    public  String processContact(@RequestParam("profileImg") MultipartFile file, @ModelAttribute Contact contact, Principal principal, HttpSession session){


        try {


            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);
            user.getContacts().add(contact);
            contact.setUser(user);
            contact.setProfileImage(file.getOriginalFilename());

            if(contact!=null){
                this.userRepository.save(user);
            }

            //processing and uploading file
            if(file.isEmpty()){
                System.out.println("File is empty" + "");

            }
                //upload the file in the folder and update the name
                System.out.println(file.getOriginalFilename());

                File saveFile = new ClassPathResource("static/img").getFile().getAbsoluteFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename() );

                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Image is uploaded");
                System.out.println("Contact Details : "+contact);

                //print message
                 session.setAttribute("message",new Message("Your contact is added !! add more...","success"));

        } catch (Exception e) {
            System.out.println("ERROR "+e.getMessage());
            e.printStackTrace();

            //print error in message
            session.setAttribute("message",new Message("Something went wrong !! Try again...","danger"));
        }

        return "normal/add_contact_form";
    }

    //show contacts handler
    //per page = 5[n]
    //current pageIndex = 0[page]
    @GetMapping("/show_contacts/{page}")
    public String showContact(@PathVariable("page") Integer page , Model m,Principal principal){

       m.addAttribute("title","Show User Contacts");
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        int userId = user.getId();

        //per page = 5[n]
        //current pageIndex = 0[page]
        Pageable pageable = PageRequest.of(page, 3);
        Page<Contact> contacts = contactRepository.findContactsByUserId(userId,pageable);

        m.addAttribute("contacts",contacts);
        m.addAttribute("currentPage",page);
        m.addAttribute("totalPages",contacts.getTotalPages());


        return "normal/show_contacts";

    }


}
