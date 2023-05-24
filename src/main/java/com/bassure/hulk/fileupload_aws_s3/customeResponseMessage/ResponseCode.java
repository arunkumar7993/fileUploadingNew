
package com.bassure.hulk.fileupload_aws_s3.customeResponseMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@NoArgsConstructor
@Setter
@Configuration
@ConfigurationProperties(prefix = "fileupload.code")
public class ResponseCode {
    
    private String sucessCode;
    private String filePathNotFoundInServer;
    private String fieldValidation;
    private String alreadyExeception;
    private String fileNotFoundException;
    private String filePathNotFoundException;
    private String nullPointException;
    
}
