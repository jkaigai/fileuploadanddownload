package com.fileconsumer.utility;

import com.fileconsumer.model.File;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUploadUtil {

    public List<File> saveFiles(String reference, MultipartFile[] files)
            throws IOException {
        List<File> filesDb=new ArrayList<>();
        File fileDb;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {

                    //add the file to the local disk
                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                    Path uploadPath = Path.of("Files_Upload");
                    String fileType=file.getContentType();
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }


                    String fileCode = RandomStringUtils.randomAlphanumeric(8);

                    try (InputStream inputStream = file.getInputStream()) {
                        Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ioe) {
                        throw new IOException("Could not save file: " + fileName, ioe);
                    }

                    //create the file and add to the database
                    fileDb=new File();

                    fileDb.setFileName(fileName);
                    fileDb.setFileType(fileType);
                    fileDb.setDownloadUri("/download_file"+"/"+fileCode);
                    fileDb.setRef(reference);


                    filesDb.add(fileDb);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }}
        return filesDb;
    }}


