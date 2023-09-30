package com.ali.smart.dao;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryImageService {
    public Map upload(MultipartFile file);
}
