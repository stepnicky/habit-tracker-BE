//package com.habittracker.backend.config;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.habittracker.backend.config.properties.S3Properties;
//
//@Configuration
//@ConditionalOnProperty(prefix = "filesystem", name = "type", havingValue = "S3")
//@RequiredArgsConstructor
//public class S3Config {
//
//    private final S3Properties s3Properties;
//
//    @Bean
//    public AmazonS3 s3client() {
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());
//        return AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .withRegion(Regions.valueOf(s3Properties.getRegion()))
//                .build();
//    }
//}
