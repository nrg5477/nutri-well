package com.example.nutri_well.upload;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class S3UploaderTest {

    @Autowired
    private S3Uploader s3Uploader;

    @Test
    public void testUpload() {
        try{
            String filePath ="D:\\wbs.png";
            String uploadName = s3Uploader.upload(filePath);
            System.out.println("uploadName = " + uploadName);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testRemove() {
        try {
            s3Uploader.removeS3File("wbs.png");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}