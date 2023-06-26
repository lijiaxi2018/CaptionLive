package com.aguri.captionlive.common.util;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
public class S3Handler {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Autowired
    private S3Client s3Client;

    public String uploadFile(MultipartFile file) {
        try {
            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "-" + StringUtils.cleanPath(file.getOriginalFilename());

            // Convert MultipartFile to File
            File convertedFile = convertMultipartFileToFile(file);

            // Upload the file to S3
            s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(fileName).build(), convertedFile.toPath());

            // Delete the temporary file
            convertedFile.delete();

            // Return the S3 file URL
            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file.");
            return "Failed to upload the file.";
        }
    }

    //fileKey is the file name in S3 bucket.
    public void deleteFileFromS3(String fileKey) {
        try {

            // Delete the file from S3
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build();

            DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);
            System.out.println("File deleted successfully. Delete marker: " + deleteObjectResponse.deleteMarker());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //fileKey is the file name in S3 bucket.
    public void downloadFileFromS3(String fileKey, String destinationPath) {
        try {
            
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();
            // Download the file from S3
            ResponseInputStream<GetObjectResponse> getObjectResponse = s3Client.getObject(getObjectRequest);

            // Save the file to the local destination
            try (OutputStream outputStream = new FileOutputStream(destinationPath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = getObjectResponse.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("File downloaded successfully to: " + destinationPath);
            } catch (IOException e) {
                System.out.println("Error saving the downloaded file: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error downloading the file: " + e.getMessage());
        }

    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return convertedFile;
    }
}

