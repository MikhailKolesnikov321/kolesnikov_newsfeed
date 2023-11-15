package com.example.kolesnikov_advancedServer.services.impl;

import com.example.kolesnikov_advancedServer.services.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    @Value("${server.fileUrl}")
    private String fileUrl;

    @Value("${file.upload.path}")
    private String root;

    public static Path uploading;

    @Override
    public String uploadFile(final MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
        uploading = Path.of(fileUrl + fileName);
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        return (fileUrl + fileName);
    }

    @Override
    public UrlResource getFile(final String fileName) throws IOException {
        Path file = Paths.get(root).resolve(fileName);
        UrlResource resource = new UrlResource(file.toUri());
        return resource;
    }
}
