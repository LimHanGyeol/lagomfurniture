package com.example.lagomfurniture.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Getter
@Setter
public class ImageUpload {
    private MultipartFile[] fileDatas;  // 다중 파일 업로드를 위해 배열로 설정

    public ImageUpload() {
    }

    @Override
    public String toString() {
        return "ImageUpload{" +
                "fileDatas=" + Arrays.toString(fileDatas) +
                '}';
    }
}
