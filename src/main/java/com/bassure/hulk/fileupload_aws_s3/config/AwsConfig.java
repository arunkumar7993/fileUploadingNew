package com.bassure.hulk.fileupload_aws_s3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;

    @Value("${cloud.aws.region.static}")
    private String region;

    private String selectedRegion;
    private String[] regions = {"us-east-2", "us-east-1", "us-west-1", "us-west-2", "af-south-1", "ap-east-1", "ap-south-2", "ap-southeast-3", "ap-southeast-4", "ap-south-1", "ap-northeast-3", "ap-northeast-2", "ap-southeast-1", "ap-southeast-2", "ap-northeast-1", "ca-central-1", "cn-north-1", "cn-northwest-1", "eu-central-1", "eu-west-1", "eu-west-2", "eu-south-1", "eu-west-3", "eu-north-1", "eu-south-2", "eu-central-2", "sa-east-1", "me-south-1", "me-central-1"};
    private String[] regionsName = {"US East (Ohio)", "US East (N. Virginia)", "US West (N. California)", "US West (Oregon)", "Africa (Cape Town)", "Asia Pacific (Hong Kong)", "Asia Pacific (Hyderabad)", "Asia Pacific (Jakarta)", "Asia Pacific (Melbourne)", "Asia Pacific (Mumbai)", "Asia Pacific (Osaka)", "Asia Pacific (Seoul)", "Asia Pacific (Singapore)", "Asia Pacific (Sydney)", "Asia Pacific (Tokyo)", "Canada (Central)", "China (Beijing)", "China (Ningxia)", "Europe (Frankfurt)", "Europe (Ireland)", "Europe (London)", "Europe (Milan)", "Europe (Paris)", "Europe (Stockholm)", "Europe (Spain)", "Europe (Zurich)", "South America (São Paulo)", "Middle East (Bahrain)", "Middle East (UAE)"};

    @Bean
    public AmazonS3 s3Client() {
//        region = regionsWithName().get(selectedRegion);
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    public List<String> regions() {
        List<String> regions = Arrays.asList(this.regionsName);
        return regions;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public Map<String, String> regionsWithName() {
        Map<String, String> regionsNameAndCode = new HashMap<String, String>();
        if (regionsName.length == regions.length) {
            for (int i = 0; i < regions.length; i++) {
                regionsNameAndCode.put(regionsName[i], regions[i]);
            }
        }
        return regionsNameAndCode;
    }

}
