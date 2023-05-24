package com.bassure.hulk.fileupload_aws_s3;

import com.bassure.hulk.fileupload_aws_s3.customeResponseMessage.ResponseCode;
import com.bassure.hulk.fileupload_aws_s3.customeResponseMessage.ResponseMessage;
import com.bassure.hulk.fileupload_aws_s3.model.AwsBucket;
import com.bassure.hulk.fileupload_aws_s3.model.FileUpload;
import com.bassure.hulk.fileupload_aws_s3.repository.AwsBucketRepository;
import com.bassure.hulk.fileupload_aws_s3.repository.FileUploadRepository;
import com.bassure.hulk.fileupload_aws_s3.repository.ModifiedFilesRepository;
import com.bassure.hulk.fileupload_aws_s3.serviceImplementation.FileUploadServiceImplementation;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class FileTesting {

    @Mock
    private ResponseMessage responseMessage;

    @Autowired
    MockMvc mockMock;
    @Mock
    private FileUploadRepository fileUploadRepository;
    @Mock
    private AwsBucketRepository awsBucketRepository;

    @Mock
    private ModifiedFilesRepository modifiedFilesRepository;

    @Mock
    private ResponseCode responseCode;

    @InjectMocks
    private FileUploadServiceImplementation fileUploadService;

    static AwsBucket awsBucket = new AwsBucket();
    static String filePath = "D:\\arun\\uploaded-files-path";
    static MockMultipartFile file = new MockMultipartFile("file", "test-file.txt", "text/plain", "welcome".getBytes());
    static MockMultipartFile file1 = new MockMultipartFile("file", "test-file1.txt", "text/plain", "welcome".getBytes());
    static FileUpload fileUpload = new FileUpload();
    static String givenFilePath = filePath + "test-file.txt";

    @BeforeAll
    public static void setUpTest() {
        awsBucket.setAwsBucketId(2);
        awsBucket.setBucketName("bassure");
        awsBucket.setCreatedAt(java.time.LocalDateTime.now() + "");
        awsBucket.set_Active(true);

        fileUpload.setBucketId(awsBucket);
        fileUpload.setFileId(1);
        fileUpload.setFileName("test-file.txt");
        fileUpload.setUploadedTime(java.time.LocalDateTime.now() + "");
        fileUpload.setDeleted(false);
        fileUpload.setFilePath(filePath);
    }

    @Test
    public void shouldUploadSingleFile() {
        Mockito.when(fileUploadRepository.existsByFilename("test-file.txt")).thenReturn(null);
        Mockito.when(awsBucketRepository.getBucketByBucketName("bassure")).thenReturn(awsBucket);
        Mockito.when(fileUploadRepository.removeFile("test-file.txt")).thenReturn(null);
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");

        Assertions.assertEquals("1300", fileUploadService.uploadFile(file).getCode());
    }

    @Test
    public void uploadFileWithExistsFileNameTest() {
        Mockito.when(fileUploadRepository.removeFile("test-file.txt")).thenReturn(fileUpload);
        Mockito.when(responseMessage.getMessage()).thenReturn("message");
        Mockito.when(responseMessage.getFileNameAlreadyExists()).thenReturn("is already exists.");
        Mockito.when(responseCode.getAlreadyExeception()).thenReturn("1320");
        Assertions.assertEquals("1320", fileUploadService.uploadFile(file).getCode());
    }

    @Test
    public void updateFileTest() {
        Mockito.when(fileUploadRepository.getFileByFilePath(givenFilePath)).thenReturn(fileUpload);
        Mockito.when(fileUploadRepository.save(fileUpload)).thenReturn(fileUpload);
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
        Mockito.when(responseMessage.getUpdatedSuccesfully()).thenReturn("Updated Succesfully");
        Assertions.assertEquals("1300", fileUploadService.updateFile(givenFilePath, file).getCode());
    }

    @Test
    public void updateFileWithNotExistsFilePathTest() {
        Mockito.when(fileUploadRepository.getFileByFilePath(givenFilePath)).thenReturn(null);
        Mockito.when(responseMessage.getMessage()).thenReturn("message");
        Mockito.when(responseMessage.getNotFound()).thenReturn("Not found.");
        Mockito.when(responseCode.getFileNotFoundException()).thenReturn("1330");
        Assertions.assertEquals("1330", fileUploadService.updateFile(givenFilePath, file).getCode());
    }

    @Test
    public void deleteFileByFilePathTest() {
        Mockito.when(fileUploadRepository.getFileByFilePath(givenFilePath)).thenReturn(fileUpload);
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
        Mockito.when(responseMessage.getRemovedSuccessfully()).thenReturn("Removed Successfully.");
        Assertions.assertEquals("1300", fileUploadService.deleteFile(givenFilePath).getCode());
    }

    @Test
    public void deleteFileByNotExistsFilePathTest() {
        Mockito.when(fileUploadRepository.getFileByFilePath(givenFilePath)).thenReturn(null);
        Mockito.when(responseMessage.getMessage()).thenReturn("message");
        Mockito.when(responseMessage.getNotFound()).thenReturn("Not found.");
        Mockito.when(responseCode.getFileNotFoundException()).thenReturn("1330");
        Assertions.assertEquals("1330", fileUploadService.deleteFile(givenFilePath).getCode());
    }

    @Test
    public void permanentDeleteFileByFilePathTest() {
        Mockito.when(fileUploadRepository.getFileByFilePath(givenFilePath)).thenReturn(fileUpload);
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
        Mockito.when(responseMessage.getDeletedSuccesfully()).thenReturn("Deleted Succesfully.");
        Assertions.assertEquals("1300", fileUploadService.deleteFile(givenFilePath).getCode());
    }

    @Test
    public void permanentDeleteFileByNotExistsFilePathTest() {
        Mockito.when(fileUploadRepository.getFileByFilePath(givenFilePath)).thenReturn(null);
        Mockito.when(responseMessage.getMessage()).thenReturn("message");
        Mockito.when(responseMessage.getNotFound()).thenReturn("Not found.");
        Mockito.when(responseCode.getFileNotFoundException()).thenReturn("1330");
        Assertions.assertEquals("1330", fileUploadService.permanentDeleteFile(givenFilePath).getCode());
    }

    @Test
    public void permanentDeleteFileWithNotExistsFilePathInServerTest() {
        Mockito.when(fileUploadRepository.getFileByFilePath(givenFilePath)).thenReturn(fileUpload);
        Mockito.when(responseMessage.getMessage()).thenReturn("message");
        Mockito.when(responseMessage.getFileNameNotFound()).thenReturn("File Name not found in Server.");
        Mockito.when(responseCode.getFileNotFoundException()).thenReturn("1330");
        Assertions.assertEquals("1330", fileUploadService.permanentDeleteFile("D:\\arun\\uploaded-files-path\\notFound\\").getCode());
    }

    @Test
    public void uploadMultipleFilesTest() {
        MockMultipartFile files[] = {file, file1};
        Mockito.when(fileUploadRepository.existsByFilename("test-file.txt")).thenReturn(null);
        Mockito.when(fileUploadRepository.removeFile("test-file.txt")).thenReturn(null);
        Mockito.when(awsBucketRepository.getBucketByBucketName("bassure")).thenReturn(awsBucket);
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
        Assertions.assertEquals("1300", fileUploadService.uploadMultipleFiles(files).getCode());
    }

//    @Test
//    public void uploadMultipleFilesWithAlreadyDeletedFileTest() {
//        MockMultipartFile files[] = {file, file1};
//        Mockito.when(fileUploadRepository.existsByFilename("test-file.txt")).thenReturn("test-file.txt");
//        Mockito.when(fileUploadRepository.removeFile("test-file.txt")).thenReturn(null);
//        Mockito.when(awsBucketRepository.getBucketByBucketName("bassure")).thenReturn(awsBucket);
//        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
//        Assertions.assertEquals("1300", fileUploadService.uploadMultipleFiles(files).getCode());
//    }
    @Test
    public void uploadMultipleFilesWithName() {
        MockMultipartFile files[] = {file, file1};
        String filesName[] = {"file1.txt", "file2.txt"};

        Mockito.when(fileUploadRepository.existsByFilename("test-file.txt")).thenReturn(null);
        Mockito.when(fileUploadRepository.removeFile("test-file.txt")).thenReturn(null);
        Mockito.when(awsBucketRepository.getBucketByBucketName("bassure")).thenReturn(awsBucket);
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
        Assertions.assertEquals("1300", fileUploadService.uploadMultipleFilesWithName(files, filesName).getCode());
    }

    @Test
    public void uploadMultipleFilesWithBlankName() {
        MockMultipartFile files[] = {file, file1};
        String filesName[] = {"", ""};

        Mockito.when(fileUploadRepository.existsByFilename("test-file.txt")).thenReturn(null);
        Mockito.when(fileUploadRepository.removeFile("test-file.txt")).thenReturn(null);
        Mockito.when(awsBucketRepository.getBucketByBucketName("bassure")).thenReturn(awsBucket);
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
        Assertions.assertEquals("1300", fileUploadService.uploadMultipleFilesWithName(files, filesName).getCode());
    }

    @Test
    public void uploadMultipleFilesWithDeletedName() {
        MockMultipartFile files[] = {file, file1};
        String filesName[] = {"file1.txt", "file2.txt"};

        Mockito.when(fileUploadRepository.existsByFilename("test-file.txt")).thenReturn("test-file.txt");
        Mockito.when(fileUploadRepository.removeFile("test-file.txt")).thenReturn(null);
        Mockito.when(awsBucketRepository.getBucketByBucketName("bassure")).thenReturn(awsBucket);
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
        Assertions.assertEquals("1300", fileUploadService.uploadMultipleFilesWithName(files, filesName).getCode());
    }

    @Test
    public void uploadMultipleFilesWithExsistingName() {
        MockMultipartFile files[] = {file, file1};
        String filesName[] = {"file1.txt", "file2.txt"};

        Mockito.when(awsBucketRepository.getBucketByBucketName("bassure")).thenReturn(awsBucket);
        Mockito.when(fileUploadRepository.removeFile("test-file.txt")).thenReturn(fileUpload);
        Mockito.when(responseMessage.getFileNameAlreadyExists()).thenReturn("is already exists.");
        Mockito.when(responseCode.getSucessCode()).thenReturn("1300");
        Assertions.assertEquals("1300", fileUploadService.uploadMultipleFilesWithName(files, filesName).getCode());

    }

    @Test
    public void shouldDownloadFile() {
        try {
            ResultActions perform = this.mockMock.perform(MockMvcRequestBuilders.get("file/download").param("filePath", "file.txt"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception ex) {
            Logger.getLogger(FileTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//      
}
