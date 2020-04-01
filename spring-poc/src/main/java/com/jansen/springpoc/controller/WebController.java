package com.jansen.springpoc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
