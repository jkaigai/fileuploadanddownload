package com.fileconsumer.repository;

import com.fileconsumer.model.File;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileConsumerRepository extends JpaRepository<File,Long>{
    @Query(value = "SELECT * FROM file where ref=:reference", nativeQuery = true)
    List<File> findAllByReference(String reference);
    @Modifying
    @Transactional
    @Query(value="DELETE from file where name=:fileName",nativeQuery = true)
    void deleteFileByName(String fileName);
    @Modifying
    @Transactional
    @Query(value="DELETE from file where ref=:reference",nativeQuery = true)
    void deleteFilesByReference(String reference);
}
