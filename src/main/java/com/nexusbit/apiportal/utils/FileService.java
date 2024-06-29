package com.nexusbit.apiportal.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.nexusbit.apiportal.utils.firebase.FirebaseService;
import com.nexusbit.apiportal.utils.firebase.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class FileService implements FirebaseService {

    public String base64ToUrl(String file){
        return file;
    }

    private  final Properties properties;
    private static final LoggerService logger = new LoggerService();

    @EventListener
    public void init(ApplicationReadyEvent event) {

        try {

            ClassPathResource serviceAccount = new ClassPathResource("firebase.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(properties.getBucketName())
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception ex) {

           logger.error("Error initializing firebase "+ ex.getMessage());

        }
    }

    @Override
    public String save(MultipartFile file) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(file.getOriginalFilename());

        bucket.create(name, file.getBytes(), file.getContentType());

        return name;
    }

    @Override
    public String save(String base64, String email, String folder) throws IOException {

        email = email.split("@")[0];

        byte[] bytes = java.util.Base64.getDecoder().decode(base64.split(",")[1]);
        ByteArrayInputStream image = new ByteArrayInputStream( Base64.getDecoder().decode(base64.split(",")[1]));
        Bucket bucket = StorageClient.getInstance().bucket();
        String name = UUID.randomUUID().toString().substring(0, 36)+"."+base64.split(",")[0].split(";")[0].split("/")[1];
        String path = email + "/" + folder + "/" + name;
        name =  email + "%2F" + folder + "%2F" + name;
        bucket.create(path, image,"image/"+ base64.split(",")[0].split(";")[0].split("/")[1]);
        return String.format(properties.getImageUrl(), name)+ "?alt=media&token=" + UUID.randomUUID().toString();
    }

    @Override
    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {

        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));
        Bucket bucket = StorageClient.getInstance().bucket();
        String name = generateFileName(originalFileName);
        bucket.create(name, bytes);

        return name;
    }

    @Override
    public void delete(String name) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isEmpty(name)) {
            logger.error("file name is empty");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
           logger.error("file not found");
        }

        blob.delete();
    }

}

