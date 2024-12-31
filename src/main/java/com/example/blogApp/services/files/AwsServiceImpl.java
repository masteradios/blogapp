package com.example.blogApp.services.files;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AwsServiceImpl implements AwsService {

    Logger log= LoggerFactory.getLogger(AwsServiceImpl.class);

    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // Method to upload a file to an S3 bucket
    @Override
    public String uploadFile(

            final String keyName,
            final Long contentLength,
            final String contentType,
            final InputStream value
    ) throws AmazonClientException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType(contentType);

        Date expiration = new Date();
        long expTimeMillis = System.currentTimeMillis() + 3600 * 1000; // 1 hour in milliseconds
        expiration.setTime(expTimeMillis);


        s3Client.putObject(bucketName, keyName, value, metadata);
        log.info("File uploaded to bucket({}): {}", bucketName, keyName);

        //GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, keyName);
        //presignedUrlRequest.setExpiration(expiration);

//        URL presignedUrl = s3Client.generatePresignedUrl(presignedUrlRequest);
//        return presignedUrl.toString();

        String fileUrl = s3Client.getUrl(bucketName, keyName).toString();
        log.info("File URL: {}", fileUrl);
        return fileUrl;
    }

    // Method to download a file from an S3 bucket
    @Override
    public ByteArrayOutputStream downloadFile(
            final String keyName
    ) throws IOException, AmazonClientException {
        S3Object s3Object = s3Client.getObject(bucketName, keyName);
        InputStream inputStream = s3Object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int len;
        byte[] buffer = new byte[4096];
        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        log.info("File downloaded from bucket({}): {}", bucketName, keyName);
        return outputStream;
    }

    // Method to list files in an S3 bucket
    @Override
    public List<String> listFiles() throws AmazonClientException {
        List<String> keys = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(bucketName);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            if (objectSummaries.isEmpty()) {
                break;
            }

            objectSummaries.stream()
                    .filter(item -> !item.getKey().endsWith("/"))
                    .map(S3ObjectSummary::getKey)
                    .forEach(keys::add);

            objectListing = s3Client.listNextBatchOfObjects(objectListing);
        }

        log.info("Files found in bucket({}): {}", bucketName, keys);
        return keys;
    }

    // Method to delete a file from an S3 bucket
    @Override
    public void deleteFile(

            final String keyName
    ) throws AmazonClientException {
        s3Client.deleteObject(bucketName, keyName);
        log.info("File deleted from bucket({}): {}", bucketName, keyName);
    }
}