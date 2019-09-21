package com.example.lagomfurniture.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FileUpload {

    List<File> uploadFiles;
    List<String> failedFiles;

    public FileUpload(List<File> uploadFiles, List<String> failedFiles) {
        this.uploadFiles = uploadFiles;
        this.failedFiles = failedFiles;
    }



}
