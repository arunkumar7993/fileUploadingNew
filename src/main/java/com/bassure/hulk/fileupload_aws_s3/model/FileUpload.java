package com.bassure.hulk.fileupload_aws_s3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "fileupload")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne()
    @JoinColumn(name = "bucket_id")
    @JsonIgnore
    private AwsBucket bucketId;

    @Column(name = "uploaded_time")
    private String uploadedTime;
    
    @Column(name="is_delete")
    private boolean deleted = Boolean.FALSE;
    
    @Column(name="deleted_date")
    private String deleteTime;
    
    @Column(name = "update_time")
    private String updatedTime;
    
    @Column(name = "file_path")
    private String filePath;
    
}
