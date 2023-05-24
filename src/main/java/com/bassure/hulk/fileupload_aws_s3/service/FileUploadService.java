package com.bassure.hulk.fileupload_aws_s3.service;

import com.bassure.hulk.fileupload_aws_s3.response.ResponseInfo;
import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileUploadService {

    public ResponseInfo uploadFile(MultipartFile file);

    public ByteArrayResource downloadFile(String givenFilePath);

    public ResponseInfo deleteFile(String givenFilePath);

    public ResponseInfo updateFile(String givenFilePath, MultipartFile file);
    
    public  ResponseInfo permanentDeleteFile(String givenFilePath);

    public ResponseInfo uploadMultipleFiles(MultipartFile[] files);
    
    public ResponseInfo uploadMultipleFilesWithName(MultipartFile[] files,String[] fileNames);
    
}
