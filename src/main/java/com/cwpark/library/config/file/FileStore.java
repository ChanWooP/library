package com.cwpark.library.config.file;

import com.cwpark.library.config.exception.RuntimeIOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public String storeFile(String filePath, MultipartFile multipartFile, String fileName) {
        try {
            if(multipartFile != null && !multipartFile.isEmpty()) {
                String ext = extractExt(multipartFile.getOriginalFilename());
                File file = new File(getFullPath(filePath + "/" + fileName + ext));

                if(!file.exists()) {
                    file.mkdirs();
                }

                multipartFile.transferTo(file);

                return filePath + "/" + fileName + ext;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public String storeFile2(String filePath, MultipartFile multipartFile) {
        try {
            if(multipartFile != null && !multipartFile.isEmpty()) {
                String ext = extractExt(multipartFile.getOriginalFilename());

                Date nowDate = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String saveFileName = simpleDateFormat.format(nowDate);

                File file = new File(getFullPath(filePath + saveFileName + ext));

                if(!file.exists()) {
                    file.mkdirs();
                }

                multipartFile.transferTo(file);

                return saveFileName + ext;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public void storeFiles(String filePath, List<MultipartFile> multipartFiles) {
        if(multipartFiles != null) {
            for(int i=0; i<multipartFiles.size(); i++) {
                if(multipartFiles.get(i) != null && !multipartFiles.get(i).isEmpty()) {
                    storeFile(filePath, multipartFiles.get(i), String.valueOf(i+1));
                }
            }
        }
    }

    // 파일 삭제
    public void deleteFile(String filePath) {
        File file = new File(getFullPath(filePath));

        if(file.exists()) {
            file.delete();
        }
    }

    // 하위 목록 포함 삭제
    public void deleteFiles(String filePath, Boolean recursion) {
        File directory = null;

        if(!recursion) {
            directory = new File(getFullPath(filePath));
        } else {
            directory = new File(filePath);
        }

        if(directory.exists()) {
            File[] list = directory.listFiles();

            for(int i=0; i<list.length; i++) {
                if(list[i].isFile()) {
                    list[i].delete();
                } else {
                    deleteFiles(list[i].getPath(), true);
                }
            }

            directory.delete();
        }
    }

    // 확장자 구하기
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return "." + originalFilename.substring(pos + 1);
    }
}
