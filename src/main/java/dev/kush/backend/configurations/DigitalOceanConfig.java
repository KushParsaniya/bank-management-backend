package dev.kush.backend.configurations;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.*;
import com.amazonaws.regions.*;
import com.amazonaws.services.s3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import static com.amazonaws.client.builder.AwsClientBuilder.*;

@Configuration
public class DigitalOceanConfig {

    @Value("${aws.s3.access-key}")
    private String accessKey;

    @Value("${aws.s3.secret-key}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.url}")
    private String url;


    @Bean
    AmazonS3 s3Client() {
//        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
        AWSCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
//        return AmazonS3ClientBuilder
//                .standard()
//                .withEndpointConfiguration(new EndpointConfiguration(url, region))
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .build();

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new EndpointConfiguration(url, region))
                .withCredentials(credentials)
                .build();
    }

}