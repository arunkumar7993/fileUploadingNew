package com.bassure.hulk.fileupload_aws_s3.repository;

import com.bassure.hulk.fileupload_aws_s3.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Integer> {

    @Query(value = "select * from fileupload where file_name = :filename and is_delete = false", nativeQuery = true)
    public FileUpload removeFile(@Param("filename") String fileName);

    @Query(value = "select file_name from fileupload where file_name = :filename and is_delete = true", nativeQuery = true)
    public String existsByFilename(@Param("filename") String fileName);

    @Query(value = "select * from fileupload where file_name = :filename", nativeQuery = true)
    public FileUpload getFileByFileName(@Param("filename") String fileName);

    @Query(value = "select * from fileupload where file_path = :filePath and is_delete = false", nativeQuery = true)
    public FileUpload getFileByFilePath(@Param("filePath") String filePath);

}
