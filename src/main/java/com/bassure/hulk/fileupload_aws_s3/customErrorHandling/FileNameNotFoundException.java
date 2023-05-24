package com.bassure.hulk.fileupload_aws_s3.customErrorHandling;

public class FileNameNotFoundException extends RuntimeException{

    public FileNameNotFoundException(String message) {
        super(message);
    }
}
