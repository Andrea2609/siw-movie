package it.uniroma3.siw.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.StorageRepository;


@Service
public class StorageService {


    @Autowired
    private StorageRepository storageRepository;

    private final String folderPath = "E:/DOCPERS/Andrea/UNIVERSITA'/SIW/SiwMovie2-LaVendetta/src/main/resources/static/images/";

   public Image uploadImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename().replace("(", "").replace(")", "").replace(" ", "_"));
        String filePath = folderPath + fileName;
        String imagePath = "images/" + fileName;

        Image fileData = storageRepository.save(Image.builder()
                .name(fileName)
                .type(file.getContentType())
                .imagePath(imagePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return fileData;
        }

        return null;
    }
}