package com.bassure.hulk.fileupload_aws_s3.repository;

import com.bassure.hulk.fileupload_aws_s3.model.AwsBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AwsBucketRepository extends JpaRepository<AwsBucket, Integer>{
    
    @Query(value = "select * from aws_bucket where bucket_name = :bucketName", nativeQuery = true)
    public AwsBucket getBucketByBucketName(@Param("bucketName") String bucketName);
    
}
