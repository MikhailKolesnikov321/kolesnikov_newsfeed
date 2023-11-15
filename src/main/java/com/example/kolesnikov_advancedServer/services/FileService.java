package com.example.kolesnikov_advancedServer.services;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {
    String uploadFile(MultipartFile file) throws IOException;

    Resource getFile(String fileName) throws IOException;
}
