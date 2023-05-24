package com.bassure.hulk.fileupload_aws_s3.serviceImplementation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.util.IOUtils;
import com.bassure.hulk.fileupload_aws_s3.customeResponseMessage.ResponseCode;
import com.bassure.hulk.fileupload_aws_s3.customeResponseMessage.ResponseMessage;
import com.bassure.hulk.fileupload_aws_s3.model.AwsBucket;
import com.bassure.hulk.fileupload_aws_s3.model.FileUpload;
import com.bassure.hulk.fileupload_aws_s3.model.ModifiedFiles;
import com.bassure.hulk.fileupload_aws_s3.repository.AwsBucketRepository;
import com.bassure.hulk.fileupload_aws_s3.repository.FileUploadRepository;
import com.bassure.hulk.fileupload_aws_s3.repository.ModifiedFilesRepository;
import com.bassure.hulk.fileupload_aws_s3.response.FileResponse;
import com.bassure.hulk.fileupload_aws_s3.response.MultipleFileResponse;
import com.bassure.hulk.fileupload_aws_s3.response.ResponseInfo;
import com.bassure.hulk.fileupload_aws_s3.service.FileUploadService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImplementation implements FileUploadService {

    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private AwsBucketRepository awsBucketRepository;
    @Autowired
    private FileUploadRepository fileUploadRepository;
    @Autowired
    private ModifiedFilesRepository modifiedFilesRepository;
    @Autowired
    ResponseMessage responseMessage;
    @Autowired
    ResponseCode responseCode;

    private String filePath = "D:\\arun\\uploaded-files-path\\";
//    private String filePath = "/home/bassure/Desktop/New Folder/uploaded-files/";

    @Override
    public ResponseInfo uploadFile(MultipartFile file) {
        FileResponse fileResponse = new FileResponse();
        ResponseInfo responseInfo = new ResponseInfo();
        Map<String, String> errors = new HashMap<>();

        String fileName = file.getOriginalFilename();

        if (Objects.isNull(fileName)) {
            errors.put(responseMessage.getMessage(), responseMessage.getFileNameNotNull());
            responseInfo.setCode(responseCode.getNullPointException());
            responseInfo.setError(errors);
            return responseInfo;
        }

        String checkFileName = fileUploadRepository.existsByFilename(fileName);

        if (Objects.nonNull(checkFileName)) {
            fileName = java.time.LocalDateTime.now() + "" + fileName;
        }

        if (Objects.nonNull(fileUploadRepository.removeFile(fileName))) {
            errors.put(responseMessage.getMessage(), fileName + responseMessage.getFileNameAlreadyExists());
            responseInfo.setCode(responseCode.getAlreadyExeception());
            responseInfo.setError(errors);
            return responseInfo;
        }

//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        try {
            file.transferTo(new File(filePath + fileName));
        } catch (IOException ex) {
            errors.put(responseMessage.getMessage(), responseMessage.getFilePathError());
            responseInfo.setCode(responseCode.getFilePathNotFoundInServer());
            responseInfo.setError(errors);
            return responseInfo;
        }

        AwsBucket awsBucket = awsBucketRepository.getBucketByBucketName(bucketName);

        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(fileName);
        fileUpload.setBucketId(awsBucket);
        fileUpload.setUploadedTime(java.time.LocalDateTime.now() + "");
        fileUpload.setFilePath(filePath + fileName);
        fileUploadRepository.save(fileUpload);

        fileResponse.setBucketName(bucketName);
        fileResponse.setFileName(fileName);
        fileResponse.setFilePath(filePath + fileName);

        responseInfo.setCode(responseCode.getSucessCode());
        responseInfo.setValue(fileResponse);
        responseInfo.setError(null);
        return responseInfo;

    }

    @Override
    public ByteArrayResource downloadFile(String givenFilePath) {
//        S3Object s3Object = s3Client.getObject(bucketName, fileName);
//        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        File fileDownload = new File(givenFilePath);
        InputStream inputStream = null;
        byte[] content = null;
        ByteArrayResource resource = null;

        try {
            inputStream = new FileInputStream(fileDownload);
            content = IOUtils.toByteArray(inputStream);
        } catch (Exception ex) {
            throw new NullPointerException(givenFilePath + responseMessage.getNotFound());
        }

        resource = new ByteArrayResource(content);
        return resource;
    }

    @Override
    public ResponseInfo updateFile(String givenFilePath, MultipartFile file) {
        ResponseInfo responseInfo = new ResponseInfo();
        FileUpload fileUpdate = fileUploadRepository.getFileByFilePath(givenFilePath);
        Map<String, String> errors = new HashMap<>();

        if (Objects.isNull(fileUpdate)) {
            errors.put(responseMessage.getMessage(), givenFilePath + responseMessage.getNotFound());
            responseInfo.setCode(responseCode.getFileNotFoundException());
            responseInfo.setError(errors);
            return responseInfo;
        }
        String fileNameToModify = java.time.LocalDateTime.now() + "" + fileUpdate.getFileName();

//        s3Client.deleteObject(bucketName, fileName);
//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        try {
            File fileToDelete = new File(givenFilePath);
            fileToDelete.renameTo(new File(filePath + fileNameToModify));
            file.transferTo(new File(givenFilePath));
        } catch (IOException ex) {
            errors.put(responseMessage.getMessage(), responseMessage.getFilePathError());
            responseInfo.setCode(responseCode.getFilePathNotFoundInServer());
            responseInfo.setError(errors);
            return responseInfo;
        }

        fileUpdate.setUpdatedTime(java.time.LocalDateTime.now() + "");
        FileUpload fileUploaded = fileUploadRepository.save(fileUpdate);

        ModifiedFiles modifiedFiles = new ModifiedFiles();
        modifiedFiles.setOriginalFile(fileUploaded);
        modifiedFiles.setModifiedAt(java.time.LocalDateTime.now() + "");
        modifiedFiles.setModifiedFileName(fileNameToModify);
        modifiedFilesRepository.save(modifiedFiles);

        responseInfo.setCode(responseCode.getSucessCode());
        responseInfo.setValue(givenFilePath + responseMessage.getUpdatedSuccesfully());
        responseInfo.setError(null);
        return responseInfo;
    }

    @Override
    public ResponseInfo deleteFile(String givenFilePath) {
        ResponseInfo responseInfo = new ResponseInfo();
        FileUpload fileupload = fileUploadRepository.getFileByFilePath(givenFilePath);
        Map<String, String> errors = new HashMap<>();

        if (Objects.isNull(fileupload)) {
            errors.put(responseMessage.getMessage(), givenFilePath + responseMessage.getNotFound());
            responseInfo.setCode(responseCode.getFileNotFoundException());
            responseInfo.setError(errors);
            return responseInfo;
        }
        fileupload.setDeleted(true);
        fileupload.setDeleteTime(java.time.LocalDateTime.now() + "");
        fileUploadRepository.save(fileupload);

        responseInfo.setCode(responseCode.getSucessCode());
        responseInfo.setValue(givenFilePath + responseMessage.getRemovedSuccessfully());
        responseInfo.setError(null);
        return responseInfo;
    }

    @Override
    public ResponseInfo permanentDeleteFile(String givenFilePath) {
        ResponseInfo responseInfo = new ResponseInfo();
        Map<String, String> errors = new HashMap<>();
        FileUpload fileupload = fileUploadRepository.getFileByFilePath(givenFilePath);

        if (Objects.isNull(fileupload)) {
            errors.put(responseMessage.getMessage(), givenFilePath + responseMessage.getNotFound());
            responseInfo.setCode(responseCode.getFileNotFoundException());
            responseInfo.setError(errors);
            return responseInfo;
        }

        File fileToDelete = new File(givenFilePath);
//        s3Client.deleteObject(bucketName, fileName);
        if (!fileToDelete.exists()) {
            errors.put(responseMessage.getMessage(), responseMessage.getFileNameNotFound());
            responseInfo.setCode(responseCode.getFileNotFoundException());
            responseInfo.setError(errors);
            return responseInfo;
        }
        fileToDelete.delete();
        fileUploadRepository.deleteById(fileupload.getFileId());

        responseInfo.setCode(responseCode.getSucessCode());
        responseInfo.setError(null);
        responseInfo.setValue(givenFilePath + responseMessage.getDeletedSuccesfully());
        return responseInfo;
    }

    @Override
    public ResponseInfo uploadMultipleFiles(MultipartFile[] files) {
        ResponseInfo responseInfo = new ResponseInfo();
        List<MultipleFileResponse> fileResponses = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();

        AwsBucket awsBucket = awsBucketRepository.getBucketByBucketName(bucketName);

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            MultipleFileResponse fileResponse = new MultipleFileResponse();

            if (Objects.isNull(fileName)) {
                fileResponse.setError(responseMessage.getFileNameNotNull());
                fileResponses.add(fileResponse);
                continue;
            }

            String checkFileName = fileUploadRepository.existsByFilename(fileName);

            if (Objects.nonNull(checkFileName)) {
                fileName = java.time.LocalDateTime.now() + "" + fileName;
            }

            if (Objects.nonNull(fileUploadRepository.removeFile(fileName))) {
                fileResponse.setError(fileName + " " + responseMessage.getFileNameAlreadyExists());
                fileResponses.add(fileResponse);
                continue;
            }

//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            try {
                file.transferTo(new File(filePath + fileName));
            } catch (IOException ex) {
                fileResponse.setError(fileName + " " + responseMessage.getFilePathError());
                fileResponses.add(fileResponse);
                continue;
            }

            FileUpload fileUpload = new FileUpload();
            fileUpload.setFileName(fileName);
            fileUpload.setBucketId(awsBucket);
            fileUpload.setUploadedTime(java.time.LocalDateTime.now() + "");
            fileUpload.setFilePath(filePath + fileName);
            fileUploadRepository.save(fileUpload);

            fileResponse.setBucketName(bucketName);
            fileResponse.setFileName(fileName);
            fileResponse.setFilePath(filePath + fileName);
            fileResponses.add(fileResponse);
        }
        responseInfo.setCode(responseCode.getSucessCode());
        responseInfo.setValue(fileResponses);
        responseInfo.setError(null);

        return responseInfo;
    }

    @Override
    public ResponseInfo uploadMultipleFilesWithName(MultipartFile[] files, String[] fileNames) {
        ResponseInfo responseInfo = new ResponseInfo();
        List<MultipleFileResponse> fileResponses = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();
        AwsBucket awsBucket = awsBucketRepository.getBucketByBucketName(bucketName);

        for (int i = 0; i < files.length; i++) {

            String fileName = fileNames[i];

            if (fileNames[i].isBlank()) {
                fileName = files[i].getOriginalFilename();
            }

            MultipleFileResponse fileResponse = new MultipleFileResponse();

            if (Objects.isNull(fileName)) {
                fileResponse.setError(responseMessage.getFileNameNotNull());
                fileResponses.add(fileResponse);
                continue;
            }

            String checkFileName = fileUploadRepository.existsByFilename(fileName);

            if (Objects.nonNull(checkFileName)) {
                fileName = java.time.LocalDateTime.now() + "" + fileName;
            }

            if (Objects.nonNull(fileUploadRepository.removeFile(fileName))) {
                fileResponse.setError(fileName + " " + responseMessage.getFileNameAlreadyExists());
                fileResponses.add(fileResponse);
                continue;
            }

//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            try {
                files[i].transferTo(new File(filePath + fileName));
            } catch (IOException ex) {
                fileResponse.setError(fileName + " " + responseMessage.getFilePathError());
                fileResponses.add(fileResponse);
                continue;
            }

            FileUpload fileUpload = new FileUpload();
            fileUpload.setFileName(fileName);
            fileUpload.setBucketId(awsBucket);
            fileUpload.setUploadedTime(java.time.LocalDateTime.now() + "");
            fileUpload.setFilePath(filePath + fileName);
            fileUploadRepository.save(fileUpload);

            fileResponse.setBucketName(bucketName);
            fileResponse.setFileName(fileName);
            fileResponse.setFilePath(filePath + fileName);
            fileResponses.add(fileResponse);
        }
        responseInfo.setCode(responseCode.getSucessCode());
        responseInfo.setValue(fileResponses);
        responseInfo.setError(null);

        return responseInfo;
    }
}
