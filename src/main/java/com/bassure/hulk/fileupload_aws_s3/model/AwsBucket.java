package com.bassure.hulk.fileupload_aws_s3.model;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Entity
@Table(name = "aws_bucket")
@Getter
@Setter
@NoArgsConstructor
public class AwsBucket {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int awsBucketId;
    
    @Column(name = "bucket_name")
    private String bucketName;
    
    @Column(name = "created_at")
    private String createdAt;
    
    @Column(name = "modified_at")
    private String modifiedAt;
    
    @Column(name = "is_Active")
    private boolean is_Active;
    
    @OneToMany(mappedBy = "bucketId")
    private List<FileUpload> fileUpload;
}
