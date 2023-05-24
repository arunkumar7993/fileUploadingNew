package com.bassure.hulk.fileupload_aws_s3.customErrorHandling;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
    
}
