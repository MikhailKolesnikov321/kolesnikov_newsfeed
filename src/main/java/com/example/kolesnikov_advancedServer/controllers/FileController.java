package com.example.kolesnikov_advancedServer.controllers;

import com.example.kolesnikov_advancedServer.dtos.CustomSuccessResponse;
import com.example.kolesnikov_advancedServer.services.impl.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/file")
public class FileController {

    private final FileServiceImpl fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(CustomSuccessResponse.ok(fileService.uploadFile(file)), HttpStatus.OK);
    }

    @GetMapping("/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("fileName") String fileName) throws IOException {
        Resource file = fileService.getFile(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}