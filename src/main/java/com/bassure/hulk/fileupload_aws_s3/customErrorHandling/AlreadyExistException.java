package com.bassure.hulk.fileupload_aws_s3.customErrorHandling;

public class AlreadyExistException extends RuntimeException{
    
    public AlreadyExistException(String message){
        super(message);
    }
}
