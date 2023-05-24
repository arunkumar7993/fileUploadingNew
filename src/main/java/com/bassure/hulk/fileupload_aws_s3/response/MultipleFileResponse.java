package com.bassure.hulk.fileupload_aws_s3.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleFileResponse {

    private String bucketName;
    private String fileName;
    private String filePath;
    private String error;

}
