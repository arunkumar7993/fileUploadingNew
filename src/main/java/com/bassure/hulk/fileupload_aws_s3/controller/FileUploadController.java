package com.bassure.hulk.fileupload_aws_s3.controller;

import com.bassure.hulk.fileupload_aws_s3.config.AwsConfig;
import com.bassure.hulk.fileupload_aws_s3.response.ResponseInfo;
import com.bassure.hulk.fileupload_aws_s3.service.FileUploadService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileUploadService service;

    @Autowired
    private AwsConfig awsConfig;

    @GetMapping("/nearestRegion")
    public List<String> nearestRegion() {
        return awsConfig.regions();
    }

    @PostMapping("/selectedRegion/{region}")
    public void selectedRegion(@PathVariable String region) {
        awsConfig.setSelectedRegion(region);
    }

    @PostMapping("/upload")
    public ResponseInfo uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return service.uploadFile(file);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "filePath") String givenFilePath) {
          ByteArrayResource resource = service.downloadFile(givenFilePath);
        return ResponseEntity
                .ok()
                .contentLength(resource.contentLength())
                .header("Content-disposition", "attachment; filename=\"" + "download" + "\"")
                .body(resource);
    }

    @PutMapping("/update")
    public ResponseInfo updateFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "filePath") String givenFilePath) {
        return service.updateFile( givenFilePath, file);
    }

    @DeleteMapping("/delete")
    public ResponseInfo deleteFile(@RequestParam(value = "filePath") String givenFilePath) {
        return service.deleteFile(givenFilePath);
    }
    
    @DeleteMapping("/deleteOrginalFile")
    public ResponseInfo deleteOrginalFile(@RequestParam(value = "filePath") String  givenFilePath){
        return service.permanentDeleteFile(givenFilePath);
    }

    @PostMapping("/multipleUpload")
    public ResponseInfo uploadMultipleFile(@RequestParam(value = "files") MultipartFile[] files) {
        return service.uploadMultipleFiles(files);
    }
    
    @PostMapping("/multipleUploadWithName")
    public ResponseInfo uploadMultipleFileWithName(@RequestParam(value = "files") MultipartFile[] files,@RequestParam(value = "fileName") String[] fileName) {
        return service.uploadMultipleFilesWithName(files,fileName);
    }
}
