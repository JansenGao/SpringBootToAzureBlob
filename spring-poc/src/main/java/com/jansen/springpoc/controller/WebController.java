package com.jansen.springpoc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;


@RestController
public class WebController {
    @Autowired
    private CloudStorageAccount cloudStorageAccount;

    private static final String containerName = "ievent";

    private static final String blobName = "test.txt";

    @GetMapping(value="/")
    public String readBlob(String content){
        String result = "";
        try{
            // Create a blob client.
            final CloudBlobClient blobClient = cloudStorageAccount.createCloudBlobClient();
            // Get a reference to a container. (Name must be lower case.)
            final CloudBlobContainer container = blobClient.getContainerReference(containerName);
            // Get a blob reference for a text file.
            CloudBlockBlob blob = container.getBlockBlobReference(blobName);
            // Get blob text
            result = blob.downloadText();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    @PostMapping(value = "/")
    public String uploadTextBlob(String content){
        try
        {
            // Create a blob client.
            final CloudBlobClient blobClient = cloudStorageAccount.createCloudBlobClient();
            // Get a reference to a container. (Name must be lower case.)
            final CloudBlobContainer container = blobClient.getContainerReference(containerName);
            // Get a blob reference for a text file.
            CloudBlockBlob blob = container.getBlockBlobReference(blobName);
            // Upload some text into the blob.
            blob.uploadText(content);
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }

        return "File was updated.\n";
    }

    @PostMapping(value = "/upload/image")
    public String uploadImage(@RequestParam("file") MultipartFile file){
        String blobName = "test-image.jpg";
        try{
            // Create a blob client.
            final CloudBlobClient blobClient = cloudStorageAccount.createCloudBlobClient();
            // Get a reference to a container. (Name must be lower case.)
            final CloudBlobContainer container = blobClient.getContainerReference(containerName);
            // Get a blob reference for a text file.
            CloudBlockBlob blob = container.getBlockBlobReference(blobName);
            // Upload some text into the blob.
            blob.upload(file.getInputStream(), file.getSize());
        }catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
        return "Success";
    }
}
