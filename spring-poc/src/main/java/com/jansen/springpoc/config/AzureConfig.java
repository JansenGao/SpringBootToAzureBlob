package com.jansen.springpoc.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Configuration
public class AzureConfig {
    @Value("${azure.storage.ConnectionString}")
    private String connectionString;

    @Value("${azure.storage.image.name}")
    private String containerName;

    @Autowired
    CloudStorageAccount cloudStorageAccount;

    @Bean
    CloudStorageAccount getCloudStorageAccount() throws URISyntaxException, InvalidKeyException {
        return CloudStorageAccount.parse(connectionString);
    }

    @Bean
    CloudBlobContainer getIeventContainer() throws URISyntaxException, StorageException {
        CloudBlobClient client = cloudStorageAccount.createCloudBlobClient();
        return client.getContainerReference(containerName);
    }
}
