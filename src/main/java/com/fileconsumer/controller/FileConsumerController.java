package com.fileconsumer.controller;

import com.fileconsumer.exception.FileNotFoundException;
import com.fileconsumer.model.File;
import com.fileconsumer.service.FileConsumerServiceImp;
import com.fileconsumer.utility.FileDownloadUtil;
import com.fileconsumer.utility.FileUploadUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@Controller
public class FileConsumerController {


    private final FileConsumerServiceImp fileConsumerServiceImp;

    public FileConsumerController(FileConsumerServiceImp fileConsumerServiceImp) {
        this.fileConsumerServiceImp = fileConsumerServiceImp;
    }

    @GetMapping("/home")
    public String getForm(){
        return "file_form";
    }
    @GetMapping("/getFiles/{reference}")
    public ResponseEntity<List<File>> getFile(@PathVariable("reference")  String reference)
    {
        List<File> filesByReference=fileConsumerServiceImp.getFilesByReference(reference);
        return ResponseEntity.ok().body(filesByReference);
    }
    @GetMapping("/download_file/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "inline; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
    @PostMapping("/upload_file")
    public ResponseEntity<String> uploadMultipartFile(
            @RequestParam("reference") String reference,
            @RequestParam("files") MultipartFile[] files) {


        List<File> filesDb;
        FileUploadUtil fileUploadUtil=new FileUploadUtil();
        try {
            filesDb = fileUploadUtil.saveFiles(reference,files);
            fileConsumerServiceImp.saveAllFilesList(filesDb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().body("file(s) saved in the database and the disk");
    }
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName) {
        try{
            fileConsumerServiceImp.deleteFileByName(fileName);
            return ResponseEntity.status(HttpStatus.OK).body("File with name '"+ fileName +"' deleted successfully");
        }catch (FileNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deletefiles/{reference}")
    public ResponseEntity<String> deleteAllFiles(@PathVariable("reference") String reference) {
        try{
            fileConsumerServiceImp.deleteFilesByReference(reference);
            return ResponseEntity.ok("Files with reference '" + reference + "' deleted successfully.");
        }catch (FileNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    }

}