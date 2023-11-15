package com.example.kolesnikov_advancedServer;

import com.example.kolesnikov_advancedServer.services.impl.FileServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileServiceTest {
    @InjectMocks
    private FileServiceImpl fileService;

    @Test
    public void testGetFile() throws IOException, NoSuchFieldException, IllegalAccessException {
        FileServiceImpl fileService = new FileServiceImpl();
        String root = "/path/to/your/root/directory";
        Field field = FileServiceImpl.class.getDeclaredField("root");
        field.setAccessible(true);
        field.set(fileService, root);
        UrlResource file = fileService.getFile("example.txt");
        assertNotNull(file);
    }

    @Test
    public void testUploadFile() throws IOException {
        String uploadDir = "/path/to/upload/dir";
        String fileUrl = "http://example.com/files/";
        String root = "/path/to/root";
        String fileName = "example.txt";
        FileServiceImpl fileService = mock(FileServiceImpl.class);
        when(fileService.getFile(fileName)).thenReturn(new UrlResource(fileUrl + fileName));
        UrlResource file = fileService.getFile(fileName);
        assertNotNull(file);
    }
}

