package com.bassure.hulk.fileupload_aws_s3.response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfo {
    private String code;
    private Object value;
    private Map<String,String> error;

    
}
