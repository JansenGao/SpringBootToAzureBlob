package com.jansen.springpoc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;


@RestController
public class WebController {

    @Autowired
    private CloudStorageAccount cloudStorageAccount;

    @Autowired
    private CloudBlobContainer container;

    // private static final String containerName = "ievent";

    private static final String blobName = "test.txt";

    /*@Bean
    public CloudStorageAccount getCloudStorageAccount() throws InvalidKeyException, URISyntaxException {
        return CloudStorageAccount.parse(environment.getProperty("azure.storage.ConnectionString"));
    }*/

    @PostMapping(value = "/upload/image")
    public String uploadImage(@RequestParam("file") MultipartFile file){
        String blobName = "test-image.jpg";

        try{
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
