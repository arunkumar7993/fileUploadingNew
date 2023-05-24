package com.bassure.hulk.fileupload_aws_s3.customeResponseMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "fileupload.message")
public class ResponseMessage {

    private String fileNameNotNull;
    private String fileNameAlreadyExists;
    private String filePathError;
    private String notFound;
    private String updatedSuccesfully;
    private String removedSuccessfully;
    private String fileNameNotFound ;
    private String deletedSuccesfully;
    private String message;
    private String bucketNotFound;

}
