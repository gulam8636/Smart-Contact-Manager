package com.ali.smart.controller;


import com.ali.smart.dao.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class CloudinaryImageUploadController {

    @Autowired
    CloudinaryImageService cloudinaryImageService;

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadImage(@RequestParam("image") MultipartFile file){

        try {
            Map data = cloudinaryImageService.upload(file);

            System.out.println("sss  "+data);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
