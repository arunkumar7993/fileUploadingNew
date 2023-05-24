package com.bassure.hulk.fileupload_aws_s3.customErrorHandling;


public class FilePathNotFoundException extends RuntimeException{

    public FilePathNotFoundException(String message) {
        super(message);
    }
    
}
