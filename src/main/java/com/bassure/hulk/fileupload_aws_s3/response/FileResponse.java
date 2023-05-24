package com.bassure.hulk.fileupload_aws_s3.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileResponse {
    
    private String bucketName;
    private String fileName;
    private String filePath;
    
}
