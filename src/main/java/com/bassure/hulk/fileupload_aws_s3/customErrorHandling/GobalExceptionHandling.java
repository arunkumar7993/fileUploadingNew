package com.bassure.hulk.fileupload_aws_s3.customErrorHandling;

import com.bassure.hulk.fileupload_aws_s3.response.ResponseInfo;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GobalExceptionHandling {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseInfo fieldValidation(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseInfo("1310", null, errors);
    }
    
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseInfo alreadyExistException(AlreadyExistException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return new ResponseInfo("1320", null, errors);
    }
    
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseInfo fileNotFoundException (FileNotFoundException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return new ResponseInfo("1330",null,errors);
    }
    
   @ExceptionHandler(FilePathNotFoundException.class)
   public ResponseInfo filePathNotFoundException (FilePathNotFoundException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return new ResponseInfo("1340", null, errors);
   }
   
   @ExceptionHandler(FileNameNotFoundException.class)
   public ResponseInfo fileNameNotFoundException(FileNameNotFoundException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return new ResponseInfo("1350", null, errors);
   }
   
   @ExceptionHandler(NullPointerException.class)
   public ResponseInfo nullPointException(NullPointerException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return new ResponseInfo("1360", null, errors);  
   }
    
}
//InvalidDefinitionException