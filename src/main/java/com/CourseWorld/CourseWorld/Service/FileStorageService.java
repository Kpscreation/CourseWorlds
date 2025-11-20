package com.CourseWorld.CourseWorld.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";  // Store inside static folder

    public FileStorageService() {
        createUploadDir();
    }

    private void createUploadDir() {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Upload directory created: " + UPLOAD_DIR);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            createUploadDir();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR).resolve(fileName);

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }
}