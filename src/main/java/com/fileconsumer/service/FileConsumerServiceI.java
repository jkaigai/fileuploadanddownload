package com.fileconsumer.service;

import com.fileconsumer.model.File;

import java.util.List;

public interface FileConsumerServiceI {
    List<File> getAllFiles();
    void saveAllFilesList(List<File> fileList);
    List<File> getFilesByReference(String reference);
    void deleteFilesByReference(String reference);
    void deleteFileByName(String fileName);
}
