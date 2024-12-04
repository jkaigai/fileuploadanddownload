package com.fileconsumer.service;

import com.fileconsumer.model.File;
import com.fileconsumer.repository.FileConsumerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileConsumerServiceImp implements FileConsumerServiceI {

    private final FileConsumerRepository fileConsumerRepository;


    FileConsumerServiceImp(FileConsumerRepository fileConsumerRepository) {
        this.fileConsumerRepository = fileConsumerRepository;
    }
    public void saveAllFilesList(List<File> fileList) {
        // Save all the files into database
        fileConsumerRepository.saveAll(fileList);
    }

    @Override
    public List<File> getFilesByReference(String reference) {
         return fileConsumerRepository.findAllByReference(reference);
    }
    @Override
    public void deleteFilesByReference(String reference){
        fileConsumerRepository.deleteFilesByReference(reference);
    }
    @Override
    public void deleteFileByName(String fileName){
        fileConsumerRepository.deleteFileByName(fileName );
    }

}
