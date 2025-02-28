package org.example.miniprojet.services;

import org.example.miniprojet.enums.FileType;
import org.example.miniprojet.enums.TypeActivity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Date;

public class FileManager {

    public static String savePhoto(MultipartFile file, String username) throws IOException {

        Path folderPath = Paths.get(System.getProperty("user.home"), "MiniProjet", FileType.PHOTO.toString());
        if(!Files.exists(folderPath))
            Files.createDirectories(folderPath);

        String originalFileName = file.getOriginalFilename();
        String fileName = username+originalFileName.substring(originalFileName.lastIndexOf("."));

        Path filePath = Paths.get(System.getProperty("user.home"), "MiniProjet", FileType.PHOTO.toString(),
                fileName);
        //file.transferTo(filePath);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    public static String saveFile(MultipartFile file, Date date, TypeActivity typeActivity) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"), "MiniProjet", FileType.DOCUMENT.toString());
        if(!Files.exists(folderPath))
            Files.createDirectories(folderPath);

        String originalFileName = file.getOriginalFilename();
        String fileName = date.toString().replaceAll("[<>:\"/\\|?*]", "_")+"_"+typeActivity.toString()+originalFileName.substring(originalFileName.lastIndexOf("."));

        Path filePath = Paths.get(System.getProperty("user.home"), "MiniProjet", FileType.DOCUMENT.toString(),
                fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    public static byte[] getFile(String pathFile) throws IOException {
        Path path = Path.of(pathFile);
        return Files.readAllBytes(path);
    }
}
