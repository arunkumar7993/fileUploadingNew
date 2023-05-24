package com.bassure.hulk.fileupload_aws_s3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "modified_files")
@Getter
@Setter
@NoArgsConstructor
public class ModifiedFiles {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int modifiedFileId;
    
    @ManyToOne()
    @JoinColumn(name = "original_file_id")
    @JsonIgnore
    private FileUpload originalFile;
    
    @Column(name = "modified_file_name")
    private String modifiedFileName;
    
    @Column(name = "modified_date")
    private String modifiedAt;
}
