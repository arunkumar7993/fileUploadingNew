package com.bassure.hulk.fileupload_aws_s3.repository;

import com.bassure.hulk.fileupload_aws_s3.model.ModifiedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModifiedFilesRepository extends JpaRepository<ModifiedFiles, Integer>{
    
}
