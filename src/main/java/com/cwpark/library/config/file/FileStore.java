package com.cwpark.library.config.file;

import com.cwpark.library.config.exception.RuntimeIOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public String storeFile(String filePath, MultipartFile multipartFile) {
        try {
            if(!multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                File file = new File(getFullPath(filePath + "/" + fileName));

                if(!file.exists()) {
                    file.mkdirs();
                }

                multipartFile.transferTo(file);

                return filePath + "/" + fileName;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public void storeFiles(String filePath, List<MultipartFile> multipartFiles) {
        for(MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                storeFile(filePath, multipartFile);
            }
        }
    }
}
